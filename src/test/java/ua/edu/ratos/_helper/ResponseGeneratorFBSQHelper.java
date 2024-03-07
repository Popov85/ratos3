package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionFBSQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseFBSQ;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides methods for generating responses maps for FBSQ questions
 */
public class ResponseGeneratorFBSQHelper {


    public Map<Long, Response> getCorrectResponseFBSQMap(Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        String correctAnswer;
        // Look up this question in map
        if (isCorrect) {
            QuestionFBSQDomain questionDomain = (QuestionFBSQDomain) questionsMap.get(questionId);
            List<PhraseDomain> collect = questionDomain.getAnswer().getAcceptedPhraseDomains().stream().collect(Collectors.toList());
            correctAnswer = collect.get(0).getPhrase();
        } else {
            // incorrect
            correctAnswer = "xxx";
        }
        ResponseFBSQ responseFBSQ = new ResponseFBSQ(questionId, correctAnswer);
        Map<Long, Response> response = new HashMap<>();
        response.put(questionId, responseFBSQ);
        return response;
    }


    // Multiple FBSQ in a batch, if containsIncorrect = true, one of responses per batch will be incorrect!
    public Map<Long, Response> getCorrectResponseFBSQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        // Only if enabled!
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionFBSQDomain questionDomain = (QuestionFBSQDomain) questionsMap.get(questionId);
            Set<Long> correctAnswers;
            if (containsIncorrect) {
                if (questionId.equals(questions.get(randomIncorrect).getQuestionId())) {
                    // here comes incorrect response creating
                    ResponseFBSQ responseMCQ = new ResponseFBSQ(questionId, "xxx");
                    response.put(questionId, responseMCQ);
                } else {
                    // here comes correct response creating
                    List<PhraseDomain> collect = questionDomain.getAnswer().getAcceptedPhraseDomains().stream().collect(Collectors.toList());
                    ResponseFBSQ responseFBSQ = new ResponseFBSQ(questionId, collect.get(0).getPhrase());
                    response.put(questionId, responseFBSQ);
                }
            } else {
                List<PhraseDomain> collect = questionDomain.getAnswer().getAcceptedPhraseDomains().stream().collect(Collectors.toList());
                ResponseFBSQ responseFBSQ = new ResponseFBSQ(questionId, collect.get(0).getPhrase());
                response.put(questionId, responseFBSQ);
            }
        }
        return response;
    }
}
