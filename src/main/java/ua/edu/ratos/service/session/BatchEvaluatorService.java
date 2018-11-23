package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.domain.batch.BatchIn;
import ua.edu.ratos.service.session.domain.response.Response;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
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
     * Evaluates the whole non-empty BatchIn coming from user, skipped questions are ignored (since deleted from BatchOut);
     * Levels processing, response may be evaluated with a score higher than 100
     *   In case the result is 100% correct and the corresponding coefficient is more than 1
     *   Even if the result is not 100% correct but the corresponding coefficient is rather big, a score may appear to be higher than 100
     * @param batchIn
     * @param sessionData
     * @return result of evaluation
     */
    public Map<Long, ResponseEvaluated> doEvaluate(@NonNull final BatchIn batchIn, @NonNull final SessionData sessionData) {

        Map<Long, ResponseEvaluated> responsesEvaluated = new HashMap<>();

        final Map<Long, Question> questionsMap = sessionData.getQuestionsMap();

        List<Long> toEvaluate = sessionData.getCurrentBatch().getBatch().stream().map(dto->dto.getQuestionId()).collect(Collectors.toList());

        // Try to look up responses for each question in BatchOut and evaluate them
        for (Long questionId : toEvaluate) {
            // look up question in sessionData
            final Question question = questionsMap.get(questionId);
            // look up response in BatchIn
            final Response response = batchIn.getResponses().get(questionId);
            if (response != null) {// if found, evaluate
                double score = (response.isNullable() ? 0 : responseEvaluatorService.evaluate(response, question));
                // Levels processing
                final byte level = question.getLevel();
                if (level>1 && score>0) score =
                        levelsEvaluatorService.evaluateLevels(score, level, sessionData.getScheme().getSettings());
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
