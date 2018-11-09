package ua.edu.ratos.service.session;

import ua.edu.ratos.service.dto.response.*;

public interface Evaluator {
    double evaluate(ResponseMultipleChoice response);
    double evaluate(ResponseFillBlankSingle response);
    // Partial response!
    double evaluate(ResponseFillBlankMultiple response);
    // Partial response!
    double evaluate(ResponseMatcher response);
    double evaluate(ResponseSequence response);
}
