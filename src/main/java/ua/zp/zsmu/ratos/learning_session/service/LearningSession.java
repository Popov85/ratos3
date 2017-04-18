package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import com.sun.istack.internal.NotNull;
import org.slf4j.LoggerFactory;
import ua.zp.zsmu.ratos.learning_session.model.*;
import java.util.*;

/**
 * Created by Andrey on 4/8/2017.
 */
public class LearningSession implements ISession {

        private static final long serialVersionUID = 8762633453287052372L;

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LearningSession.class);

        /**
         * ID of the stored backup (in a form of byte[]) of this class in a database
         */
        private Long sid;
        private Student student;
        private Scheme scheme;
        // Key - a Theme, Value - List of question on this theme
        private Map<Theme, List<Question>> questionSequences = new HashMap<>();
        //private List<Question> questionSequence = new ArrayList<>();

        // Key - a Theme, Value - list of results (answers)
        private Map<Theme, List<QuestionResult>> resultSequences = new HashMap<>();

        //private Map<Question, List<Answer>> studentAnswers = new HashMap<>();

        private Theme currentTheme;

        private int currentQuestionNumber = 0;

        private double currentResult = 0d;

        private boolean isInterruptedByUser;
        private boolean isInterruptedByTimeout;

        private boolean isFinished;

        public LearningSession(@NotNull Long sid, @NotNull Student student, @NotNull Scheme scheme,
                               @NotNull Map<Theme, List<Question>> questionSequences) {
                if (questionSequences.isEmpty())
                        throw new RuntimeException("Question list cannot be empty!");
                this.sid = sid;
                this.student = student;
                this.scheme = scheme;
                //this.questionSequence = questionSequence;
        }

        @Override
        public Long getStoredSessionID() {
                return this.sid;
        }

        @Override
        public void startSession() {
                provideNextQuestion();
        }

        @Override
        public void interruptSession() {
                this.isInterruptedByUser=true;
        }

        @Override
        public Question provideNextQuestion() {
                /*if (currentQuestionNumber<=questionSequence.size()-1) {
                        Question nextQuestion = questionSequence.get(currentQuestionNumber);
                        currentQuestionNumber++;
                        return nextQuestion;

                } else {
                        finishSession();
                        return null;
                }*/
                return new Question();
        }

        @Override
        public void obtainStudentAnswer(Question question, Answer answer) {
                List<Answer> answers = new ArrayList<>();
                answers.add(answer);
                //studentAnswers.put(question, answers);
                // return right answer if needed
        }

        private Result finishSession() {
                this.isFinished = true;
                return calculateResult();
        }

        private Result calculateResult() {
                return new Result("Your result is:");
        }

        public void provideResult(int number) {

        }
}
