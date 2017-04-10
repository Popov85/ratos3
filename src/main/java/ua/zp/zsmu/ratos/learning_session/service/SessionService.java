package ua.zp.zsmu.ratos.learning_session.service;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Session;

import java.util.Date;

/**
 * Created by Andrey on 4/9/2017.
 */
@Transactional
public class SessionService {

        @Autowired
        private SessionDAO sessionDAO;

        public ISession start(Student student, Scheme scheme) {
                // Create Session object
                // Create corresponding ISession object
                ISession iSession = SessionFactory.getSession(scheme);
                // At ISession populate questions
                // Update Session
                // Return first question
                return new RandomSession();
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
