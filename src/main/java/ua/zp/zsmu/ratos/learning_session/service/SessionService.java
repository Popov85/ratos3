package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Session;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 4/9/2017.
 */
@Service
public class SessionService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SessionService.class);

        @Autowired
        private SessionDAO sessionDAO;

        @Autowired
        private RandomQuestionProvider randomQuestionProvider;

        @Transactional
        public ISession start(Student student, Scheme scheme) throws RuntimeException {
                // Create Session object
                Session session = create(scheme);
                LOGGER.info("Serializable Session created: "+session);
                // Produce questions
                Map<Theme, List<Question>> questions = randomQuestionProvider.produceQuestionSequence(scheme, false);
                LOGGER.info("Questions generated: "+questions);
                // Create corresponding ISession object
                ISession iSession = LearningSessionFactory.getSession(session.getSid(), session.getBeginTime(), student, scheme, questions);
                LOGGER.info("Learning Session created: "+iSession);
                // Update Session
                update(iSession);
                LOGGER.info("Session updated!");
                return iSession;
        }

        private Session create(Scheme scheme) {
                Session session = new Session();
                Date date = new Date();
                session.setBeginTime(date);
                session.setScheme(scheme);
                return sessionDAO.save(session);
        }

        public void update(ISession iSession) {
                LOGGER.info("iSession to be serialized is: "+iSession);
                byte[] backup = SerializationUtils.serialize(iSession);
                sessionDAO.updateSessionInfoById(backup, new Date(), iSession.getStoredSessionID());
        }

        public ISession restore(Long sid) {
                Session session = sessionDAO.findOne(sid);
                ISession iSession = (ISession) SerializationUtils.deserialize(session.getSession());
                return iSession;
        }

        public Session findOne(Long sid) {
                return sessionDAO.findOne(sid);
        }

        public QuestionDTO provideNextQuestion(ISession iSession) throws TimeIsOverException {
                QuestionDTO question  = iSession.provideNextQuestion();
                // TODO: Check if the question has any resources?
                return question;

        }
}
