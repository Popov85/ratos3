package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 13.04.2017.
 */
@Service
public class SortedRandomQuestionProvider implements IQuestionProvider {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SortedRandomQuestionProvider.class);

        @Autowired
        private CachedRandomQuestionProvider cachedRandomQuestionProvider;

        @Autowired
        private DBRandomQuestionProvider dbRandomQuestionProvider;

        @Override
        public List<Question> produceQuestionSequence(Scheme scheme, boolean isFromCache) {
                List<Question> questions = new ArrayList<>();
                if (isFromCache) {
                        questions =  cachedRandomQuestionProvider.produceQuestionSequenceFromCache(scheme);
                } else {
                        questions = dbRandomQuestionProvider.produceQuestionSequenceFromDB(scheme);
                }
                // TODO sort questions by level: from simpler (0) -> to harder(2)
                return questions;
        }
}
