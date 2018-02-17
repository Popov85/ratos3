package ua.zp.zsmu.ratos.learning_session.service.parser;

import ua.zp.zsmu.ratos.learning_session.model.Question;

/**
 * Created by Andrey on 1/20/2018.
 */
public interface Parser {
    ParsingResult<Question, Issue> readFile(LineReader lineReader);
}
