package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.cache.QuestionContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 13.04.2017.
 */
@Service
public class CachedRandomQuestionProvider {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CachedRandomQuestionProvider.class);

        @Autowired
        private QuestionContainer questionContainer;

        // TODO
        public List<Question> produceQuestionSequenceFromCache(Scheme scheme) {
                // Get List<Question> from in-memory cache: QuestionContainer
                LOGGER.info("Look up this Scheme in the cache." +
                        "1) if present - return List<Question>" +
                        "2) if not - request DB and put List<Question> to cache, then return it");
                if (questionContainer.containsScheme(scheme.getId())) return questionContainer.getQuestions(scheme.getId());
                return new ArrayList<>();
        }
}
