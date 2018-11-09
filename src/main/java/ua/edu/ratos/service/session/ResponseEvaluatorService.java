package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.dto.response.Response;

@Service
public class ResponseEvaluatorService {

    public double evaluate(Response r, Question q) {
        return r.evaluateWith(new EvaluatorImpl(q));
    }
}
