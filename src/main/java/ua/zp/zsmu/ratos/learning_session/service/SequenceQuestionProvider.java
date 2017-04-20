package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 13.04.2017.
 */
public class SequenceQuestionProvider implements IQuestionProvider {

        // TODO
        @Override
        public Map<Theme, List<Question>> produceQuestionSequence(Scheme scheme, boolean isFromCache) {
                return null;
        }
}
