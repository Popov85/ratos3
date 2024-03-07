package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.List;

/**
 * Use this implementation for non-dynamic sessions.
 */
@Slf4j
@Service
@AllArgsConstructor
public class StaticNextBatchProducer {

    private final NextBatchQuestionsProducer nextBatchQuestionsProducer;

    private final BatchOptionalParamsSetter batchOptionalParamsProducer;

    /**
     * Create next batch for a static session;
     * @param sessionData SessionData
     * @return next BatchOutDto
     */
    public BatchOutDto produce(@NonNull final SessionData sessionData) {
        List<QuestionSessionOutDto> batchQuestions = nextBatchQuestionsProducer.getBatchQuestions(sessionData);
        int questionsLeft = sessionData.getCurrentBatch().get().getQuestionsLeft() - batchQuestions.size();
        int batchesLeft = sessionData.getCurrentBatch().get().getBatchesLeft() - 1;
        BatchOutDto batchOutDto = BatchOutDto.createRegular(batchQuestions, (questionsLeft==0 && batchesLeft==0));
        batchOptionalParamsProducer.setQuestionsLeft(sessionData, batchOutDto, questionsLeft);
        batchOptionalParamsProducer.setBatchesLeft(sessionData, batchOutDto, batchesLeft);
        batchOptionalParamsProducer.setOptionalParams(sessionData, batchOutDto);
        log.debug("Produced batchOutDto for static next request = {}", batchOutDto);
        return batchOutDto;
    }

}
