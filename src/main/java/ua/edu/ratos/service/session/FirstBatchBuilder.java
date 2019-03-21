package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.PreviousBatchResult;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirstBatchBuilder {

    private AppProperties appProperties;

    private TimingService timingService;

    private ProgressDataService progressDataService;

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setTimingService(TimingService timingService) {
        this.timingService = timingService;
    }

    @Autowired
    public void setProgressDataService(ProgressDataService progressDataService) {
        this.progressDataService = progressDataService;
    }

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    /**
     * Build first batch in session.
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return next BatchOutDto
     */
    public BatchOutDto build(@NonNull final SessionData sessionData) {
        List<QuestionSessionOutDto> batchQuestions = getBatchQuestions(sessionData);
        return new BatchOutDto.Builder()
                .withQuestions(batchQuestions)
                .inMode(sessionData.getSchemeDomain().getModeDomain())
                .withTimeLeft(getTimeLeft(sessionData))
                .withQuestionsLeft(getQuestionsLeft(sessionData, batchQuestions.size()))
                .withBatchesLeft(getBatchesLeft(sessionData, batchQuestions.size()))
                .withBatchTimeLimit(getBatchTimeLimit(sessionData, batchQuestions.size()))
                .build();
    }

    /**
     * Before calling this method make sure that there are some questions left, that is
     * sessionData.hasMoreQuestions() returns true, otherwise it will throw RuntimeException
     * with message "Wrong API usage: no more questions!"
     * @param sessionData session data
     * @return list of next batch questions
     */
    protected List<QuestionSessionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
        if (!sessionData.hasMoreQuestions()) throw new IllegalStateException("Wrong API usage: no more questions");

        final List<QuestionDomain> questionDomains = sessionData.getQuestionDomains();
        final int size = questionDomains.size();
        final int currentIndex = sessionData.getCurrentIndex();
        if (currentIndex >= size) throw new RuntimeException("Ops, no more questions!");
        final int questionsPerBatch = sessionData.getQuestionsPerBatch();
        List<QuestionDomain> result;
        if (currentIndex + questionsPerBatch<= size) {// normal
            result = new ArrayList<>(questionDomains.subList(currentIndex, currentIndex+questionsPerBatch));
        } else {// to the end
            result = new ArrayList<>(questionDomains.subList(currentIndex, size));
        }
        List<QuestionSessionOutDto> collect = result.stream().map(q -> q.toDto()).collect(Collectors.toList());

        // shuffle answers if enabled and where appropriate
        if (appProperties.getSession()!=null && appProperties.getSession().isShuffleEnabled())
            collect.forEach(q->{ if (q.isShufflingSupported()) q.shuffle(collectionShuffler);});

        return collect;
    }

    protected Optional<PreviousBatchResult> getPreviousBatchResult(@NonNull final SessionData sessionData, BatchEvaluated batchEvaluated) {
        if (sessionData.getSchemeDomain().getModeDomain().isRightAnswer()) {
            final double currentScore = progressDataService.getCurrentScore(sessionData);
            final double effectiveScore = progressDataService.getEffectiveScore(sessionData);
            return Optional.of(new PreviousBatchResult(currentScore, effectiveScore, batchEvaluated));
        }
        return Optional.empty();
    }


    protected long getTimeLeft(@NonNull final SessionData sessionData) {
        long timeLeft = -1;// in sec
        final LocalDateTime sessionTimeout = sessionData.getSessionTimeout();
        if (timingService.isLimited(sessionTimeout)) {
            timeLeft = LocalDateTime.now().until(sessionTimeout, ChronoUnit.SECONDS);
        }
        return timeLeft;
    }

    protected long getBatchTimeLimit(@NonNull final SessionData sessionData, int questionsPerBatch) {
        // Return -1 if time is not limited
        long batchTimeLimit = -1; // in sec
        final long perQuestionTimeLimit = sessionData.getPerQuestionTimeLimit();
        if (timingService.isLimited(perQuestionTimeLimit)) {
            batchTimeLimit = questionsPerBatch * perQuestionTimeLimit;
        }
        return batchTimeLimit;
    }

    protected int getQuestionsLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex();
        final int totalQuestionsNumber = sessionData.getQuestionDomains().size();
        int questionsLeft = totalQuestionsNumber-(currentIndex+questionsPerBatch);
        return questionsLeft;
    }

    protected int getBatchesLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex()+questionsPerBatch;
        final int totalQuestionsNumber = sessionData.getQuestionDomains().size();
        if (currentIndex>=totalQuestionsNumber-1) return 0;
        int quantity = (totalQuestionsNumber - currentIndex)/questionsPerBatch;
        if ((totalQuestionsNumber - currentIndex)%questionsPerBatch==0) {
            return quantity;
        } else {
            return ++quantity;
        }
    }
}
