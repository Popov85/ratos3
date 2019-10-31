package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionFBMQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseFBMQ;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides methods for generating responses maps for FBMQ questions
 */
public class ResponseGeneratorFBMQHelper {

    public Map<Long, Response> getCorrectResponseFBMQMap(Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        Set<ResponseFBMQ.Pair> correctAnswers;
        // Look up this question in map
        if (isCorrect) {
            QuestionFBMQDomain questionDomain = (QuestionFBMQDomain) questionsMap.get(questionId);
            correctAnswers = questionDomain.getAnswers().stream().map(a->{
                List<PhraseDomain> collect = a.getAcceptedPhraseDomains().stream().collect(Collectors.toList());
                return new ResponseFBMQ.Pair(a.getAnswerId(), collect.get(0).getPhrase());
            }).collect(Collectors.toSet());
        } else {
            // incorrect
            correctAnswers = Arrays.asList(new ResponseFBMQ.Pair(999L, "xxx")).stream().collect(Collectors.toSet());
        }
        ResponseFBMQ responseFBMQ = new ResponseFBMQ(questionId, correctAnswers);
        Map<Long, Response> response = new HashMap<>();
        response.put(questionId, responseFBMQ);
        return response;
    }


    // Multiple FBMQ in a batch, if containsIncorrect = true, one of responses per batch will be incorrect!
    public Map<Long, Response> getCorrectResponseFBMQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        // Only if enabled!
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionFBMQDomain questionDomain = (QuestionFBMQDomain) questionsMap.get(questionId);
            if (containsIncorrect) {
                if (questionId.equals(questions.get(randomIncorrect).getQuestionId())) {
                    // here comes incorrect response creating
                    Set<ResponseFBMQ.Pair> incorrect = Arrays.asList(new ResponseFBMQ.Pair(999L, "xxx"))
                            .stream().collect(Collectors.toSet());
                    ResponseFBMQ responseFBMQ = new ResponseFBMQ(questionId, incorrect);
                    response.put(questionId, responseFBMQ);
                } else {
                    // here comes correct response creating
                    Set<ResponseFBMQ.Pair> correct = questionDomain.getAnswers().stream().map(a -> {
                        List<PhraseDomain> collect = a.getAcceptedPhraseDomains().stream().collect(Collectors.toList());
                        return new ResponseFBMQ.Pair(a.getAnswerId(), collect.get(0).getPhrase());
                    }).collect(Collectors.toSet());
                    ResponseFBMQ responseFBMQ = new ResponseFBMQ(questionId, correct);
                    response.put(questionId, responseFBMQ);
                }
            } else {
                Set<ResponseFBMQ.Pair> correct = questionDomain.getAnswers().stream().map(a -> {
                    List<PhraseDomain> collect = a.getAcceptedPhraseDomains().stream().collect(Collectors.toList());
                    return new ResponseFBMQ.Pair(a.getAnswerId(), collect.get(0).getPhrase());
                }).collect(Collectors.toSet());
                ResponseFBMQ responseFBMQ = new ResponseFBMQ(questionId, correct);
                response.put(questionId, responseFBMQ);
            }
        }
        return response;
    }
}
