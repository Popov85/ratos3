package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import ua.zp.zsmu.ratos.learning_session.model.*;
import java.util.*;

/**
 * Created by Andrey on 4/8/2017.
 */
public class LearningSession implements ISession {

        private static final long serialVersionUID = 1L;

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LearningSession.class);

        /**
         * ID of the stored backup (in a form of byte[]) of this class in a database
         */
        private Long sid;
        private Student student;
        private Scheme scheme;
        private List<Question> questionSequence = new ArrayList<>();
        private Map<Question, List<Answer>> studentAnswers = new HashMap<>();

        private int currentQuestionNumber = 0;

        private boolean isInterruptedByUser;
        private boolean isInterruptedByTimeout;

        public LearningSession(Long sid, Student student, Scheme scheme, List<Question> questionSequence) {
                this.sid = sid;
                this.student = student;
                this.scheme = scheme;
                this.questionSequence = questionSequence;
        }

        @Override
        public Long getStoredSessionID() {
                return this.sid;
        }

        @Override
        public void startSession() {
                if (questionSequence.isEmpty())
                        throw new IllegalStateException("Question list cannot be empty!");
                provideNextQuestion();
        }

        @Override
        public void interruptSession() {
                this.isInterruptedByUser=true;
        }

        @Override
        public Question provideNextQuestion() {
                if (currentQuestionNumber<=questionSequence.size()-1) {
                        Question nextQuestion = questionSequence.get(currentQuestionNumber);
                        currentQuestionNumber++;
                        return nextQuestion;

                } else {
                        finishSession();
                        return null;
                }
        }

        @Override
        public void obtainStudentAnswer(Question question, Answer answer) {
                List<Answer> answers = new ArrayList<>();
                answers.add(answer);
                studentAnswers.put(question, answers);
                // return right answer if needed
        }

        @Override
        public Result finishSession() {
                return calculateResult();
        }

        @Override
        public Result calculateResult() {
                StringBuilder sb = new StringBuilder();
                return new Result("Your result is:");
        }
}
