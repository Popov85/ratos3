package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.domain.PreviousBatchResult;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchBuilder {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private TimingService timingService;

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private CollectionShuffler collectionShuffler;


    /**
     * Build a BatchOutDto at the session start (first batch)
     * Before calling this method make sure that there are some questions left, that is
     * sessionData.hasMoreQuestions() returns true, otherwise it will throw IllegalStateException
     * with message "Wrong API usage: no more questions!"
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return next BatchOutDto
     */
    public BatchOutDto build(@NonNull final SessionData sessionData) {
        if (!sessionData.hasMoreTime()) throw new IllegalStateException("Wrong API usage: no more questions");
        final BatchOutDto batchOutDto = buildRegular(sessionData);
        log.debug("BatchOutDto is built = {}", batchOutDto);
        return batchOutDto;
    }

    /**
     * Build a BatchOutDto in the middle of the Session (second+ batch with some responses from user)
     * The same method like previous overloaded method, but included the possibility to include results of previous batch
     * Before calling this method make sure that there are some questions left, that is
     * sessionData.hasMoreQuestions() returns true, otherwise it will throw RuntimeException
     * with message "Wrong API usage: no more questions!"
     * @param sessionData SessionData object associated with the current http(s)-session
     * @param batchEvaluated only needed when we provide right answers in between batches
     * @return next BatchOutDto
     */
    public BatchOutDto build(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        BatchOutDto batchOutDto = build(sessionData);
        if (sessionData.getSchemeDomain().getModeDomain().isRightAnswer()) {
            final double currentScore = progressDataService.getCurrentScore(sessionData);
            final double effectiveScore = progressDataService.getEffectiveScore(sessionData);
            PreviousBatchResult previousBatchResult =
                    new PreviousBatchResult(currentScore, effectiveScore, batchEvaluated);
            batchOutDto.setPreviousBatchResult(previousBatchResult);
            log.debug("PreviousBatchResult is added to BatchOutDto = {}", previousBatchResult);
            return batchOutDto;
        }
        return batchOutDto;
    }



    private BatchOutDto buildRegular(@NonNull final SessionData sessionData) {

        final List<QuestionSessionOutDto> batchQuestions = getBatchQuestions(sessionData);

        // shuffle answers if enabled and where appropriate
        if (appProperties.getSession()!=null && appProperties.getSession().isShuffle_enabled())
            batchQuestions.forEach(q->{ if (q.isShufflingSupported()) q.shuffle(collectionShuffler);});

        final int currentBatchSize = batchQuestions.size();
        // Timing resolution
        long timeLeft = getTimeLeft(sessionData);
        long batchTimeLimit = getBatchTimeLimit(sessionData, currentBatchSize);
        // Questions left
        final int questionsLeft = getQuestionsLeft(sessionData, currentBatchSize);
        // Batches left
        int batchesLeft = getBatchesLeft(sessionData, currentBatchSize);
        return new BatchOutDto.Builder()
                .withQuestions(batchQuestions)
                .inMode(sessionData.getSchemeDomain().getModeDomain())
                .withTimeLeft(timeLeft)
                .withQuestionsLeft(questionsLeft)
                .withBatchTimeLimit(batchTimeLimit)
                .withBatchesLeft(batchesLeft)
                .build();
    }

    private List<QuestionSessionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
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
        return result.stream().map(q->q.toDto()).collect(Collectors.toList());
    }

    private long getTimeLeft(@NonNull final SessionData sessionData) {
        long timeLeft = -1;// in sec
        final LocalDateTime sessionTimeout = sessionData.getSessionTimeout();
        if (timingService.isLimited(sessionTimeout)) {
            timeLeft = LocalDateTime.now().until(sessionTimeout, ChronoUnit.SECONDS);
        }
        return timeLeft;
    }

    private long getBatchTimeLimit(@NonNull final SessionData sessionData, int questionsPerBatch) {
        // Return -1 if time is not limited
        long batchTimeLimit = -1; // in sec
        final long perQuestionTimeLimit = sessionData.getPerQuestionTimeLimit();
        if (timingService.isLimited(perQuestionTimeLimit)) {
            batchTimeLimit = questionsPerBatch * perQuestionTimeLimit;
        }
        return batchTimeLimit;
    }

    private int getQuestionsLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex();
        final int totalQuestionsNumber = sessionData.getQuestionDomains().size();
        int questionsLeft = totalQuestionsNumber-(currentIndex+questionsPerBatch);
        return questionsLeft;
    }

    private int getBatchesLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
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
