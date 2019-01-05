package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.response.*;

public interface Evaluator {
    double evaluate(ResponseMCQ response);
    double evaluate(ResponseFBSQ response);
    // Partial response!
    double evaluate(ResponseFBMQ response);
    // Partial response!
    double evaluate(ResponseMQ response);
    double evaluate(ResponseSQ response);
}
