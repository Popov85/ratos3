package ua.zp.zsmu.ratos.learning_session.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

/**
 * Factory provides a proper QuestionProvider
 * Created by Andrey on 13.04.2017.
 */
public class QuestionProviderFactory {

        @Autowired
        private RandomQuestionProvider randomQuestionProvider;

        @Autowired
        private SequenceQuestionProvider sequenceQuestionProvider;

        @Autowired
        private SortedRandomQuestionProvider sortedRandomQuestionProvider;

        private QuestionProviderFactory() {}

        public static IQuestionProvider getQuestionProvider(Scheme scheme) {

                int mode = scheme.getMode();
                switch (mode) {
                        case 0:
                                return new RandomQuestionProvider();
                        case 1:
                                return new SequenceQuestionProvider();
                        case 2:
                                // Additionally to RandomQuestionProvider sorts the list of Questions by level
                                // Happy session
                                return new SortedRandomQuestionProvider();
                        default: throw new UnsupportedOperationException();
                }

        }
}
