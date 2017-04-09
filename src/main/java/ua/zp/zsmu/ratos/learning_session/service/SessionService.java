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

        public Session create(Scheme scheme) {
                Session session = new Session();
                Date date = new Date();
                session.setBeginTime(date);
                session.setLastSerialisationTime(date);
                session.setScheme(scheme);
                //session.setSession(SerializationUtils.serialize(this));
                // YourObject yourObject = (YourObject) SerializationUtils.deserialize(byte[] data)
                //this.session = session;
                return sessionDAO.save(session);
        }
}
