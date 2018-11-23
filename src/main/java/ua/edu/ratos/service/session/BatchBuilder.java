package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.domain.batch.BatchOut;
import ua.edu.ratos.service.session.domain.PreviousBatchResult;
import ua.edu.ratos.service.session.dto.question.QuestionOutDto;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchBuilder {

    /**
     * If true, randomizes answers of questions;
     * True by default
     */
    private static final boolean MIXABLE = true;

    @Autowired
    private TimingService timingService;

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private CollectionShuffler collectionShuffler;


    public BatchOut build(@NonNull final SessionData sessionData) {
        final BatchOut batchOut = buildRegular(sessionData);
        log.debug("BatchOut is built :: {}", batchOut);
        return batchOut;
    }

    public BatchOut build(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        final boolean includeRightAnswer = sessionData.getScheme().getMode().isRightAnswer();
        final BatchOut batchOut = buildRegular(sessionData);
        if (includeRightAnswer) {
            final double currentScore = progressDataService.getCurrentScore(sessionData);
            final double effectiveScore = progressDataService.getEffectiveScore(sessionData);
            PreviousBatchResult previousBatchResult =
                    new PreviousBatchResult(currentScore, effectiveScore, batchEvaluated);
            batchOut.setPreviousBatchResult(previousBatchResult);
            log.debug("Extended BatchOut is built :: {}", batchOut);
            return batchOut;
        }
        log.debug("Regular BatchOut is built :: {}", batchOut);
        return batchOut;
    }




    private BatchOut buildRegular(@NonNull final SessionData sessionData) {
        final List<QuestionOutDto> batchQuestionsDto = shuffleBatchQuestions(sessionData);
        final int currentBatchSize = batchQuestionsDto.size();
        // Timing resolution
        long timeLeft = getTimeLeft(sessionData);
        long batchTimeLimit = getBatchTimeLimit(sessionData, currentBatchSize);
        // Questions left
        final int questionsLeft = getQuestionsLeft(sessionData, currentBatchSize);
        // Batches left
        int batchesLeft = getBatchesLeft(sessionData, currentBatchSize);
        return new BatchOut.Builder()
                .withQuestions(batchQuestionsDto)
                .inMode(sessionData.getScheme().getMode())
                .withTimeLeft(timeLeft)
                .withQuestionsLeft(questionsLeft)
                .withBatchTimeLimit(batchTimeLimit)
                .withBatchesLeft(batchesLeft).build();
    }

    private List<QuestionOutDto> shuffleBatchQuestions(@NonNull final SessionData sessionData) {
        List<QuestionOutDto> batchQuestionsDto = getBatchQuestions(sessionData);
        batchQuestionsDto.forEach(q->{
            if (MIXABLE && q.isShufflingSupported()) q.shuffle(collectionShuffler);
        });
        return batchQuestionsDto;
    }

    private List<QuestionOutDto> getBatchQuestions(@NonNull final SessionData sessionData) {
        final List<Question> questions = sessionData.getQuestions();
        final int currentIndex = sessionData.getCurrentIndex();
        final int questionsPerBatch = sessionData.getQuestionsPerBatch();
        final int size = questions.size();

        if (currentIndex >= size - 1)
            throw new IllegalStateException("oops, no more questions");

        List<Question> result;
        if (currentIndex + questionsPerBatch<= size) {
            // normal
            result = new ArrayList<>(questions.subList(currentIndex, currentIndex+questionsPerBatch));
        } else {
            // to the end
            result =  new ArrayList<>(questions.subList(currentIndex, size));
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
        final int totalQuestionsNumber = sessionData.getQuestions().size();
        int questionsLeft = totalQuestionsNumber-(currentIndex+questionsPerBatch);
        return questionsLeft;
    }

    private int getBatchesLeft(@NonNull final SessionData sessionData, int questionsPerBatch) {
        final int currentIndex = sessionData.getCurrentIndex();
        final int totalQuestionsNumber = sessionData.getQuestions().size();
        if (currentIndex>=totalQuestionsNumber-1) return 0;
        int quantity = (totalQuestionsNumber - currentIndex)/questionsPerBatch;
        if ((totalQuestionsNumber - currentIndex)%questionsPerBatch==0) {
            return quantity;
        } else {
            return ++quantity;
        }
    }
}
