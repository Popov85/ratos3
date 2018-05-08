package ua.zp.zsmu.ratos.learning_session.service.parser;

import ua.zp.zsmu.ratos.learning_session.model.Question;

/**
 * Created by Andrey on 1/24/2018.
 */
public interface LineReader {

    void readLine(String line);

    ParsingResult<Question, Issue> getParsingResult();

}
