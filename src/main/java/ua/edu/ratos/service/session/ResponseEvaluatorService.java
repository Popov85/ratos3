package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;

import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.domain.response.Response;

@Service
public class ResponseEvaluatorService {

    public double evaluate(Response r, Question q) {
        return r.evaluateWith(new EvaluatorImpl(q));
    }
}
