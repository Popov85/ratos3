package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionMCQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseMCQ;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides methods for generating responses maps for MCQ questions
 */
public class ResponseGeneratorMCQHelper {

    public Map<Long, Response> getCorrectResponseMCQMap(Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        Set<Long> correctAnswers;
        // Look up this question in map
        if (isCorrect) {
            QuestionMCQDomain questionDomain = (QuestionMCQDomain) questionsMap.get(questionId);
            correctAnswers = questionDomain.getAnswers().stream().filter(a -> a.getPercent() != 0).map(a -> a.getAnswerId()).collect(Collectors.toSet());
        } else {
            correctAnswers = new HashSet<>(Arrays.asList(999L));
        }
        ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
        Map<Long, Response> response = new HashMap<>();
        response.put(questionId, responseMCQ);
        return response;
    }

    // Multiple MCQ in a batch, if isCorrect = false, only of response in this batch will be incorrect!
    public Map<Long, Response> getCorrectResponseMCQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, Long incorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionMCQDomain questionDomain = (QuestionMCQDomain) questionsMap.get(questionId);
            Set<Long> correctAnswers;
            if (questionId.equals(incorrect)) {
                // here comes incorrect response creating
                correctAnswers = new HashSet<>(Arrays.asList(999L));
                ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
                response.put(questionId, responseMCQ);
            } else {
                // here comes correct response creating
                correctAnswers = questionDomain.getAnswers().stream().filter(a -> a.getPercent() != 0).map(a -> a.getAnswerId()).collect(Collectors.toSet());
                ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
                response.put(questionId, responseMCQ);
            }
        }
        return response;
    }

    // Multiple MCQ in a batch, if containsIncorrect = true, one of responses per batch will be incorrect!
    public Map<Long, Response> getCorrectResponseMCQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        // Only if enabled!
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionMCQDomain questionDomain = (QuestionMCQDomain) questionsMap.get(questionId);
            Set<Long> correctAnswers;
            if (containsIncorrect) {
                if (questionId.equals(questions.get(randomIncorrect).getQuestionId())) {
                    // here comes incorrect response creating
                    correctAnswers = new HashSet<>(Arrays.asList(999L));
                    ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
                    response.put(questionId, responseMCQ);
                } else {
                    // here comes correct response creating
                    correctAnswers = questionDomain.getAnswers().stream().filter(a -> a.getPercent() != 0).map(a -> a.getAnswerId()).collect(Collectors.toSet());
                    ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
                    response.put(questionId, responseMCQ);
                }
            } else {
                correctAnswers = questionDomain.getAnswers().stream().filter(a -> a.getPercent() != 0).map(a -> a.getAnswerId()).collect(Collectors.toSet());
                ResponseMCQ responseMCQ = new ResponseMCQ(questionId, correctAnswers);
                response.put(questionId, responseMCQ);
            }
        }
        return response;
    }
}
