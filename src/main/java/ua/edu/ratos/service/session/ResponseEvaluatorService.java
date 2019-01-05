package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;

import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;

@Service
public class ResponseEvaluatorService {

    public double evaluate(Response r, QuestionDomain q) {
        return r.evaluateWith(new EvaluatorImpl(q));
    }
}
