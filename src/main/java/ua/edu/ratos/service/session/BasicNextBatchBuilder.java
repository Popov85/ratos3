package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import java.util.List;

/**
 * Use this implementation for non-dynamic sessions.
 */
@Slf4j
@Service
public class BasicNextBatchBuilder extends FirstBatchBuilder {
    /**
     * Build next batch in session.
     * The same method like previous overloaded method, but included the possibility to include results of previous batch
     * @param sessionData SessionData object associated with the current http(s)-session
     * @param batchEvaluated only needed when we provide right answers in between batches
     * @return next BatchOutDto
     */
    public BatchOutDto build(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        List<QuestionSessionOutDto> batchQuestions = getBatchQuestions(sessionData);
        return new BatchOutDto.Builder()
                .withQuestions(batchQuestions)
                .inMode(sessionData.getSchemeDomain().getModeDomain())
                .withTimeLeft(getTimeLeft(sessionData))
                .withQuestionsLeft(sessionData.getCurrentBatch().get().getQuestionsLeft() - batchQuestions.size())
                .withBatchesLeft(sessionData.getCurrentBatch().get().getBatchesLeft() - 1)
                .withBatchTimeLimit(getBatchTimeLimit(sessionData, batchQuestions.size()))
                .withPreviousBatchResult(getPreviousBatchResult(sessionData, batchEvaluated).orElse(null))
                .build();
    }
}
