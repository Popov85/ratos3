package ua.edu.ratos.service;

import ua.edu.ratos.service.dto.response.*;

public interface Evaluator {
    int evaluate(ResponseMultipleChoice response);
    int evaluate(ResponseFillBlankSingle response);
    int evaluate(ResponseFillBlankMultiple response);
    int evaluate(ResponseMatcher response);
    int evaluate(ResponseSequence response);
}
