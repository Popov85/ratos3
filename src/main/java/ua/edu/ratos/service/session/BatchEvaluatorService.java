package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchEvaluatorService {

    @Autowired
    private ResponseEvaluatorService responseEvaluatorService;

    @Autowired
    private LevelsEvaluatorService levelsEvaluatorService;

    /**
     * Evaluates the whole non-empty BatchInDto coming from user, skipped questions are ignored (since deleted from BatchOutDto);
     * Levels processing, response may be evaluated with a score higher than 100
     *   In case the result is 100% correct and the corresponding coefficient is more than 1
     *   Even if the result is not 100% correct but the corresponding coefficient is rather big, a score may appear to be higher than 100
     * @param batchInDto
     * @param sessionData
     * @return result of evaluation
     */
    public Map<Long, ResponseEvaluated> doEvaluate(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {

        Map<Long, ResponseEvaluated> responsesEvaluated = new HashMap<>();

        final Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();

        List<Long> toEvaluate = sessionData.getCurrentBatch().getBatch().stream().map(dto->dto.getQuestionId()).collect(Collectors.toList());

        // Try to look up responses for each question in BatchOutDto and evaluate them
        for (Long questionId : toEvaluate) {
            // look up question in sessionData
            final QuestionDomain questionDomain = questionsMap.get(questionId);
            // look up response in BatchInDto
            final Response response = batchInDto.getResponses().get(questionId);
            if (response != null) {// if found, evaluate
                double score = (response.isNullable() ? 0 : responseEvaluatorService.evaluate(response, questionDomain));
                // Levels processing
                final byte level = questionDomain.getLevel();
                if (level>1 && score>0) score =
                        levelsEvaluatorService.evaluateLevels(score, level, sessionData.getSchemeDomain().getSettingsDomain());
                responsesEvaluated.put(questionId, new ResponseEvaluated(questionId, response, score));
                log.debug("Evaluated response, ID :: {}, score :: {}", questionId, score);
            } else {// if not found, consider incorrect
                log.debug("Empty response, incorrect ID :: {}", questionId);
                responsesEvaluated.put(questionId, ResponseEvaluated.buildEmpty(questionId));
            }
        }
        return responsesEvaluated;
    }


}
