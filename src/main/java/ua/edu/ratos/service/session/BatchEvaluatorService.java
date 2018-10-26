package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.dto.response.Response;
import ua.edu.ratos.service.dto.session.*;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.*;

@Slf4j
@Service
public class BatchEvaluatorService {

    @Autowired
    private ResponseEvaluatorService evaluator;

    /**
     * Evaluates the whole BatchIn coming from user
     * Skipped questions are ignored
     * @param batchIn
     * @param sessionData
     * @return map object incapsulated the result of evaluation
     */
    public Map<Long, ResponseEvaluated> doEvaluate(@NonNull final BatchIn batchIn, @NonNull final List<Long> toEvaluate,
                                                   @NonNull final SessionData sessionData) {

        final Map<Long, Question> questionsMap = sessionData.getQuestionsMap();

        Map<Long, ResponseEvaluated> responsesEvaluated = new HashMap<>();

        // Try to look up responses for each question and evaluate them
        toEvaluate.forEach(questionId -> {
            // look up in BatchIn
            final Response response = batchIn.getResponses().get(questionId);
            final Question question = questionsMap.get(questionId);
            if (response != null) {// if found, evaluate
                final int score = (response.isNullable() ? 0 : evaluator.evaluate(response, question));
                responsesEvaluated.put(questionId, new ResponseEvaluated(question, response, score));
                log.debug("Evaluated response, ID :: {}, score :: {}", questionId, score);
            } else {// if not found, consider incorrect
                responsesEvaluated.put(questionId, ResponseEvaluated.buildEmpty(question));
                log.debug("Empty response, consider incorrect ID :: {}", questionId);
            }
        });
        return responsesEvaluated;
    }

}
