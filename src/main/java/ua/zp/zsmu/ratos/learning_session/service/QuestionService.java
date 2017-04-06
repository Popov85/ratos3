package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.List;

/**
 * Created by Andrey on 23.03.2017.
 */
@Transactional
@Component
public class QuestionService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionService.class);

        @Autowired
        private QuestionDAO questionDAO;

        public List<Question> findAll() {
                return questionDAO.findAll();
        }

        public Question findOne(Long id) {
                LOGGER.info("findOne: "+questionDAO.findOne(id));
                return questionDAO.findOne(id);
        }

        public Question findOneWithAnswers(Long id) {
                LOGGER.info("findOneWithAnswers: "+questionDAO.findOneQuestionWithAnswers(id));
                return questionDAO.findOneQuestionWithAnswers(id);
        }

}
