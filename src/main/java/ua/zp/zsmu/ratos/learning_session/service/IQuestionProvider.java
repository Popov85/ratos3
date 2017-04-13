package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.util.List;

/**
 * Created by Andrey on 13.04.2017.
 */
public interface IQuestionProvider {
        /**
         * Produces a sequence of Questions on the given Scheme
         * @param scheme - Scheme that contains a number of Themes that in turn contain a number of Questions
         * @param isFromCache - specifies that the questions should be taken from cache: QuestionContainer
         * @return
         */
        List<Question> produceQuestionSequence(Scheme scheme, boolean isFromCache);

}
