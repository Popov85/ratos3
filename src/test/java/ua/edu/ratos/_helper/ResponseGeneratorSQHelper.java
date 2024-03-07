package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.answer.AnswerSQDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionSQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseSQ;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides methods for generating responses maps for SQ questions
 */
public class ResponseGeneratorSQHelper {

    public Map<Long, Response> getCorrectResponseSQMap(Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        List<Long> correctAnswers;
        // Look up this question in map
        if (isCorrect) {
            QuestionSQDomain questionDomain = (QuestionSQDomain) questionsMap.get(questionId);
            List<AnswerSQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
            correctAnswers = collect.stream().map(a -> a.getAnswerId()).collect(Collectors.toList());
        } else {
            // incorrect
            correctAnswers = Arrays.asList(996L, 997L, 998L, 999L);
        }
        ResponseSQ responseSQ = new ResponseSQ(questionId, correctAnswers);
        Map<Long, Response> response = new HashMap<>();
        response.put(questionId, responseSQ);
        return response;
    }

    // Multiple SQ in a batch, if containsIncorrect = true, one of responses per batch will be incorrect!
    public Map<Long, Response> getCorrectResponseSQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        // Only if enabled!
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionSQDomain questionDomain = (QuestionSQDomain) questionsMap.get(questionId);
            List<Long> correctAnswers;
            if (containsIncorrect) {
                if (questionId.equals(questions.get(randomIncorrect).getQuestionId())) {
                    // here comes incorrect response creating
                    correctAnswers = Arrays.asList(996L, 997L, 998L, 999L);
                    ResponseSQ responseSQ = new ResponseSQ(questionId, correctAnswers);
                    response.put(questionId, responseSQ);
                } else {
                    // here comes correct response creating
                    List<AnswerSQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
                    correctAnswers = collect.stream().map(a -> a.getAnswerId()).collect(Collectors.toList());
                    ResponseSQ responseSQ = new ResponseSQ(questionId, correctAnswers);
                    response.put(questionId, responseSQ);
                }
            } else {
                List<AnswerSQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
                correctAnswers = collect.stream().map(a -> a.getAnswerId()).collect(Collectors.toList());
                ResponseSQ responseSQ = new ResponseSQ(questionId, correctAnswers);
                response.put(questionId, responseSQ);
            }
        }
        return response;
    }
}
