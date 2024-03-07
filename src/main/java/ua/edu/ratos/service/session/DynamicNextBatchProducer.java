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
 * Use this implementation for dynamic sessions.
 */
@Slf4j
@Service
@AllArgsConstructor
public class DynamicNextBatchProducer {

    private final NextBatchQuestionsProducer nextBatchQuestionsProducer;

    private final BatchCreatingDataProducer batchCreatingDataProducer;

    private final BatchOptionalParamsSetter batchOptionalParamsProducer;

    /**
     * Build next batch for a dynamic session with calculations questions left and batches left each time.
     * @param sessionData SessionData
     * @return next BatchOutDto
     */
    public BatchOutDto produce(@NonNull final SessionData sessionData) {
        List<QuestionSessionOutDto> batchQuestions = nextBatchQuestionsProducer.getBatchQuestions(sessionData);
        int questionsLeft = batchCreatingDataProducer.getQuestionsLeft(sessionData, batchQuestions.size());
        int batchesLeft = batchCreatingDataProducer.getBatchesLeft(sessionData, batchQuestions.size());
        BatchOutDto batchOutDto = BatchOutDto.createRegular(batchQuestions, (questionsLeft==0 && batchesLeft==0));
        batchOptionalParamsProducer.setQuestionsLeft(sessionData, batchOutDto, questionsLeft);
        batchOptionalParamsProducer.setBatchesLeft(sessionData, batchOutDto, batchesLeft);
        batchOptionalParamsProducer.setOptionalParams(sessionData, batchOutDto);
        log.debug("Produced batchOutDto for dynamic next request = {}", batchOutDto);
        return batchOutDto;
    }
}
