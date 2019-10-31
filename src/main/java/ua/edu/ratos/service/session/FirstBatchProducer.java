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
 * Related classes for the following batches:
 * @see StaticNextBatchProducer
 * @see DynamicNextBatchProducer
 */
@Slf4j
@Service
@AllArgsConstructor
public class FirstBatchProducer {

    private final FirstBatchQuestionsProducer firstBatchQuestionsProducer;

    private final BatchCreatingDataProducer batchCreatingDataProducer;

    private final BatchOptionalParamsSetter batchOptionalParamsProducer;

    /**
     * Create the first batch in any type of sessions
     * @param sessionData SessionData
     * @return next BatchOutDto
     */
    public BatchOutDto produce(@NonNull final SessionData sessionData) {
        List<QuestionSessionOutDto> batchQuestions = firstBatchQuestionsProducer.getBatchQuestions(sessionData);
        int questionsLeft = batchCreatingDataProducer.getQuestionsLeft(sessionData, batchQuestions.size());
        int batchesLeft = batchCreatingDataProducer.getBatchesLeft(sessionData, batchQuestions.size());
        BatchOutDto batchOutDto = BatchOutDto.createRegular(batchQuestions, (questionsLeft==0 && batchesLeft==0));
        batchOptionalParamsProducer.setQuestionsLeft(sessionData, batchOutDto, questionsLeft);
        batchOptionalParamsProducer.setBatchesLeft(sessionData, batchOutDto, batchesLeft);
        batchOptionalParamsProducer.setOptionalParams(sessionData, batchOutDto);
        log.debug("Produced batchOutDto = {}", batchOutDto);
        return batchOutDto;
    }
}
