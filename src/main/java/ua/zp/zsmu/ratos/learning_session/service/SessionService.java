package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.controller.SessionController;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Session;

import java.util.Date;

/**
 * Created by Andrey on 4/9/2017.
 */
@Transactional
public class SessionService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SessionService.class);

        @Autowired
        private SessionDAO sessionDAO;

        @Autowired
        private ISession sessionFactory;

        public ISession start(Student student, Scheme scheme) {
                // Create Session object
                Session session = create(scheme);
                LOGGER.info("Serializable Session created: "+session);
                // Create corresponding ISession object
                ISession iSession = sessionFactory; //SessionFactory.getSession(scheme);
                LOGGER.info("Learning Session created: "+iSession);
                // At ISession populate questions
                iSession.populatePersonalQuestionSequence();
                LOGGER.info("Questions populated!");
                // Update Session
                update(session.getSid(), iSession);
                LOGGER.info("Session updated!");
                return iSession;
        }

        public Session create(Scheme scheme) {
                Session session = new Session();
                Date date = new Date();
                session.setBeginTime(date);
                session.setScheme(scheme);
                return sessionDAO.save(session);
        }

        public void update(Long sid, ISession iSession) {
                byte[] backup = SerializationUtils.serialize(iSession);
                sessionDAO.updateSessionInfoById(backup, new Date(), sid);
        }

        public ISession restore(Long sid) {
                Session session = sessionDAO.findOne(sid);
                ISession iSession = (ISession) SerializationUtils.deserialize(session.getSession());
                return iSession;
        }


        public Session findOne(Long sid) {
                return sessionDAO.findOne(sid);
        }

}
