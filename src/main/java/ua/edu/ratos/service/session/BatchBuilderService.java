package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.dto.session.BatchOut;
import ua.edu.ratos.service.dto.session.PreviousBatchResult;
import ua.edu.ratos.service.dto.session.QuestionOutDto;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchBuilderService {

    @Autowired
    private TimingService timingService;

    @Autowired
    private ProgressDataService progressDataService;

    /**
     * If true, randomizes answers of questions;
     * True by default
     */
    private static final boolean MIXABLE = true;


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
        final List<QuestionOutDto> batchQuestionsDto = getBatchDto(sessionData);
        final int currentBatchSize = batchQuestionsDto.size();
        // Timing resolution
        long timeLeft = getTimeLeft(sessionData);
        long batchTimeLimit = getBatchTimeLimit(sessionData, currentBatchSize);
        // Questions left
        final int questionsLeft = getQuestionsLeft(sessionData, currentBatchSize);
        // Batches left
        int batchesLeft = getBatchesLeft(sessionData, currentBatchSize);
        return new BatchOut(batchQuestionsDto, timeLeft, questionsLeft, batchTimeLimit, batchesLeft);
    }

    private List<QuestionOutDto> getBatchDto(@NonNull final SessionData sessionData) {
        List<Question> batchQuestions = getBatchQuestions(sessionData);
        final List<QuestionOutDto> batchQuestionsDto = batchQuestions
                .stream()
                .map(q -> q.toDto(MIXABLE))
                .collect(Collectors.toList());
        return batchQuestionsDto;
    }

    private List<Question> getBatchQuestions(@NonNull final SessionData sessionData) {
        final List<Question> questions = sessionData.getQuestions();
        final int currentIndex = sessionData.getCurrentIndex();
        final int questionsPerBatch = sessionData.getQuestionsPerBatch();
        final int size = questions.size();

        if (currentIndex >= size - 1)
            throw new IllegalStateException("oops, no more questions");

        if (currentIndex + questionsPerBatch<= size) {
            // normal
            return new ArrayList<>(questions.subList(currentIndex, currentIndex+questionsPerBatch));
        } else {
            // to the end
            return new ArrayList<>(questions.subList(currentIndex, size));
        }
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
