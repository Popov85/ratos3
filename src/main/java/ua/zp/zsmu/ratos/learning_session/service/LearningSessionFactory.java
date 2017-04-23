package ua.zp.zsmu.ratos.learning_session.service;

import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 10.04.2017.
 */
@Component
public class LearningSessionFactory {

        private LearningSessionFactory() {}

        public static ISession getSession(Long sid, Date beginTime, Student student, Scheme scheme, Map<Theme, List<Question>> questions) {
                // Create a different session depending on the type
                return new LearningSession(sid, beginTime, student, scheme, questions);
        }
}
