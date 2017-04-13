package ua.zp.zsmu.ratos.learning_session.service;

import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import java.util.List;

/**
 * Created by Andrey on 10.04.2017.
 */
@Component
public class LearningSessionFactory {

        private LearningSessionFactory() {}

        public static ISession getSession(Long sid, Student student, Scheme scheme, List<Question> questions) {
                // Create a different session depending on the type
                return new LearningSession(sid, student, scheme, questions);
        }
}
