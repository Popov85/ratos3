package ua.zp.zsmu.ratos.learning_session.service;

import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

/**
 * Created by Andrey on 10.04.2017.
 */
public class SessionFactory {
        public static ISession getSession(Scheme scheme) {
                // Create a different session depending on the type
                return new RandomSession();
        }
}
