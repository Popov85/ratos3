package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 11.04.2017.
 */
@Service
public class RandomQuestionProvider implements IQuestionProvider {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RandomQuestionProvider.class);

        @Autowired
        private CachedRandomQuestionProvider cachedRandomQuestionProvider;

        @Autowired
        private DBRandomQuestionProvider dbRandomQuestionProvider;

        @Override
        public Map<Theme, List<Question>> produceQuestionSequence(Scheme scheme, boolean isFromCache) {
                if (isFromCache) return cachedRandomQuestionProvider.produceQuestionSequenceFromCache(scheme);
                Map<Theme, List<Question>> questions = dbRandomQuestionProvider.produceQuestionSequenceFromDB(scheme);
                return questions;
        }
}
