package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.util.List;

/**
 * Created by Andrey on 11.04.2017.
 */
@Component
public class QuestionSequenceProducer {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionSequenceProducer.class);

        @Autowired
        private QuestionDAO questionDAO;

        public List<Question> producePersonalQuestionSequence(Scheme scheme) {
                LOGGER.info("questionDAO "+questionDAO);
                return questionDAO.findNRandomByTheme(123l,5);
        }
}
