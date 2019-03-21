package ua.edu.ratos.it.service.session.sessions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.answer.AnswerMQDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionMQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseMQ;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile(value = {"h2", "mysql"})
public class SessionTestMQSupport {


    public Map<Long, Response> getCorrectResponseMQMap(Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        Set<ResponseMQ.Triple> correctAnswers;
        // Look up this question in map
        if (isCorrect) {
            QuestionMQDomain questionDomain = (QuestionMQDomain) questionsMap.get(questionId);
            List<AnswerMQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
            correctAnswers = collect.stream().map(a -> new ResponseMQ.Triple(a.getAnswerId(), a.getLeftPhraseDomain().getPhraseId(), a.getRightPhraseDomain().getPhraseId())
            ).collect(Collectors.toSet());
        } else {
            // incorrect
            correctAnswers = Arrays.asList(new ResponseMQ.Triple(questionId, 998L, 999L)).stream().collect(Collectors.toSet());
        }
        ResponseMQ responseMQ = new ResponseMQ(questionId, correctAnswers);
        Map<Long, Response> response = new HashMap<>();
        response.put(questionId, responseMQ);
        return response;
    }

    // Multiple MQ in a batch, if containsIncorrect = true, one of responses per batch will be incorrect!
    public Map<Long, Response> getCorrectResponseMQMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getBatch();
        // Only if enabled!
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            QuestionMQDomain questionDomain = (QuestionMQDomain) questionsMap.get(questionId);
            Set<ResponseMQ.Triple> correctAnswers;
            if (containsIncorrect) {
                if (questionId.equals(questions.get(randomIncorrect).getQuestionId())) {
                    // here comes incorrect response creating
                    correctAnswers = Arrays.asList(new ResponseMQ.Triple(questionId, 998L, 999L)).stream().collect(Collectors.toSet());
                    ResponseMQ responseMQ = new ResponseMQ(questionId, correctAnswers);
                    response.put(questionId, responseMQ);
                } else {
                    // here comes correct response creating
                    List<AnswerMQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
                    correctAnswers = collect.stream().map(a -> new ResponseMQ.Triple(a.getAnswerId(), a.getLeftPhraseDomain().getPhraseId(), a.getRightPhraseDomain().getPhraseId())
                    ).collect(Collectors.toSet());
                    ResponseMQ responseMQ = new ResponseMQ(questionId, correctAnswers);
                    response.put(questionId, responseMQ);
                }
            } else {
                List<AnswerMQDomain> collect = questionDomain.getAnswers().stream().collect(Collectors.toList());
                correctAnswers = collect.stream().map(a -> new ResponseMQ.Triple(a.getAnswerId(), a.getLeftPhraseDomain().getPhraseId(), a.getRightPhraseDomain().getPhraseId())
                ).collect(Collectors.toSet());
                ResponseMQ responseMQ = new ResponseMQ(questionId, correctAnswers);
                response.put(questionId, responseMQ);
            }
        }
        return response;
    }
}
