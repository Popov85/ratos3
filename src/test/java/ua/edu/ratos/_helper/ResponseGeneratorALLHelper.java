package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResponseGeneratorALLHelper {

    public Map<Long, Response> getResponseMap(Long type, Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {

        if (type.equals(1L)) return new ResponseGeneratorMCQHelper().getCorrectResponseMCQMap(questionsMap, questionId, isCorrect);
        if (type.equals(2L)) return new ResponseGeneratorFBSQHelper().getCorrectResponseFBSQMap(questionsMap, questionId, isCorrect);
        if (type.equals(3L)) return new ResponseGeneratorFBMQHelper().getCorrectResponseFBMQMap(questionsMap, questionId, isCorrect);
        if (type.equals(4L)) return new ResponseGeneratorMQHelper().getCorrectResponseMQMap(questionsMap, questionId, isCorrect);
        if (type.equals(5L)) return new ResponseGeneratorSQHelper().getCorrectResponseSQMap(questionsMap, questionId, isCorrect);
        throw new UnsupportedOperationException("Unrecognized type");
    }

    public Map<Long, Response> getResponseMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getQuestions();
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            long type = question.getType();
            Map<Long, Response> resp;
            if(containsIncorrect && question.getQuestionId().equals(questions.get(randomIncorrect).getQuestionId())) {
                resp = getResponseMap(type, questionsMap, questionId, false);
            } else {
                resp = getResponseMap(type, questionsMap, questionId, true);
            }
            response.putAll(resp);
        }
        return response;
    }
}
