package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Andrey on 4/8/2017.
 */
/*@Data
@Getter
@Setter*/
@Component
@Transactional
public class RandomSession implements ISession {

        private static final long serialVersionUID = 1L;

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RandomSession.class);

        @Autowired
        private SessionDAO sessionDAO;

        @Autowired
        private QuestionDAO questionDAO;

        /**
         * Session object is created as the first request is obtained to begin a learning session
         * and is sent/updated into DB every time a response from student is obtained
         */
        private Long sid;
        private Student student;
        private Scheme scheme;
        private List<Question> questionSequence = new ArrayList<>();
        private Map<Question, Answer> studentAnswers = new HashMap<>();

        private int currentQuestionNumber = 0;

        public RandomSession() {
        }

        public RandomSession(Student student, Scheme scheme) {
                this.student = student;
                this.scheme = scheme;
        }


        @Override
        public Long getSID() {
                return this.sid;
        }

        @Override
        public void populatePersonalQuestionSequence(Scheme scheme) {
                questionSequence = questionDAO.findNRandomByTheme(123l,5);
                LOGGER.info("Sequence HashCode: "+questionSequence.hashCode());
        }

        @Override
        public void startSession() {
                if (questionSequence.isEmpty())
                        throw new IllegalStateException("Question list cannot be empty!");
                provideNextQuestion();
        }

        @Override
        public void interruptSession() {

        }

        @Override
        public Question provideNextQuestion() {
                currentQuestionNumber++;
                if (currentQuestionNumber>=questionSequence.size()) finishSession();
                return questionSequence.get(currentQuestionNumber);
        }

        @Override
        public void obtainStudentAnswer(Question question, Answer answer) {
                studentAnswers.put(question, answer);
        }

        @Override
        public void finishSession() {
                calculateResult();
        }

        @Override
        public Result calculateResult() {
                return new Result();
        }
}
