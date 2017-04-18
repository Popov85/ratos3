package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 13.04.2017.
 */
public interface IQuestionProvider {
        /**
         * Produces a sequence of Questions on the given Scheme
         * @param scheme - Scheme that contains a number of ThemeAndRatedQuestions that in turn contain a number of Questions
         * @param isFromCache - specifies that the questions should be taken from cache: QuestionContainer
         * @return
         */
        Map<Theme,List<Question>> produceQuestionSequence(Scheme scheme, boolean isFromCache);

}
