package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.domain.PreviousBatchResult;
import ua.edu.ratos.service.dto.session.question.QuestionOutDto;
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


    public BatchOutDto build(@NonNull final SessionData sessionData) {
        final BatchOutDto batchOutDto = buildRegular(sessionData);
        log.debug("BatchOutDto is built :: {}", batchOutDto);
        return batchOutDto;
    }

    public BatchOutDto build(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        final BatchOutDto batchOutDto = buildRegular(sessionData);
        if (sessionData.getSchemeDomain().getModeDomain().isRightAnswer()) {
            final double currentScore = progressDataService.getCurrentScore(sessionData);
            final double effectiveScore = progressDataService.getEffectiveScore(sessionData);
            PreviousBatchResult previousBatchResult =
                    new PreviousBatchResult(currentScore, effectiveScore, batchEvaluated);
            batchOutDto.setPreviousBatchResult(previousBatchResult);
            log.debug("Extended BatchOutDto is built :: {}", batchOutDto);
            return batchOutDto;
        }
        log.debug("Regular BatchOutDto is built :: {}", batchOutDto);
        return batchOutDto;
    }


    private BatchOutDto buildRegular(@NonNull final SessionData sessionData) {
        final List<QuestionOutDto> batchQuestions = getBatchQuestions(sessionData);

        if (appProperties.getSession().isShuffle_enabled())
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



    private List<QuestionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
        final List<QuestionDomain> questionDomains = sessionData.getQuestionDomains();
        final int size = questionDomains.size();
        final int currentIndex = sessionData.getCurrentIndex();
        if (currentIndex >= size) throw new RuntimeException("Ooops, no more questions!");
        final int questionsPerBatch = sessionData.getQuestionsPerBatch();
        List<QuestionDomain> result;
        if (currentIndex + questionsPerBatch<= size) {// normal
            result = new ArrayList<>(questionDomains.subList(currentIndex, currentIndex+questionsPerBatch));
        } else {// to the end
            result =  new ArrayList<>(questionDomains.subList(currentIndex, size));
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

    /**
     * -1 if is not limited
     * @param sessionData
     * @param questionsPerBatch
     * @return time limit in sec
     */
    private long getBatchTimeLimit(@NonNull final SessionData sessionData, int questionsPerBatch) {
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
        final int currentIndex = sessionData.getCurrentIndex();
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