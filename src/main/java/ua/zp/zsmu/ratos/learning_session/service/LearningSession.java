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

        private static final long serialVersionUID = -2956423932590303815L;

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LearningSession.class);

        /**
         * ID of the stored backup (in a form of byte[]) of this class in a database
         */
        private final Long sid;

        private final Student student;
        private final Scheme scheme;

        private final Map<Theme, List<Question>> questionSequences;

        private List<Question> questionSequence = new ArrayList<>();

        private Map<Theme, List<QuestionResult>> resultSequences = new HashMap<>();

        private Map<Question, QuestionStatistics> statistics = new HashMap<>();

        private int currentQuestionIndex = 0;

        private double currentResult = 0d;

        private boolean isInterruptedByUser;
        private boolean isInterruptedByTimeout;

        private boolean isFinished;

        public LearningSession(@NotNull final Long sid, @NotNull final Student student, @NotNull final Scheme scheme,
                               @NotNull final Map<Theme, List<Question>> questionSequences) {
                if (questionSequences.isEmpty())
                        throw new RuntimeException("Question list cannot be empty!");
                this.sid = sid;
                this.student = student;
                this.scheme = scheme;
                this.questionSequences = questionSequences;
                this.questionSequence = createQuestionSequence(questionSequences);
        }

        // TODO: consider to move to a question provider
        private List<Question> createQuestionSequence(Map<Theme, List<Question>> questionSequences) {
                List<Question> resultingQuestionSequence = new ArrayList<>();
                questionSequences.values().forEach(resultingQuestionSequence::addAll);
                return resultingQuestionSequence;
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
        public void obtainStudentAnswer(Question question, List<Long> answers) {
                QuestionResult questionResult = new QuestionResult(question, answers);
                questionResult.calculateResult();
                //calculateCurrentResult(); increase counter and divide by the quantity
                Theme theme = question.getTheme();
                List<QuestionResult> results = resultSequences.get(theme);
                if (results==null) results = new ArrayList<>();
                results.add(questionResult);
                resultSequences.put(theme, results);

                // Check if there is such question in statistics
                // Calculate the time before the right answer is provided

                if (!statistics.containsKey(question)) {
                        QuestionStatistics questionStatistics = new QuestionStatistics(123l);
                        statistics.put(question, questionStatistics);
                } else {
                        // update time and everything
                }

        }

        @Override
        public Question skipQuestion() {
                return null;
        }

        @Override
        public String provideHelp() {
                return null;
        }

        @Override
        public String provideHint() {
                return null;
        }

        @Override
        public SessionResult provideReport() {
                List<ThemeResult> themeResults = new ArrayList<>();
                for (Map.Entry<Theme, List<QuestionResult>> themes : resultSequences.entrySet()) {
                        Theme theme = themes.getKey();
                        List<QuestionResult> results = themes.getValue();
                        int questionsInTheme = questionSequences.get(theme).size();
                        ThemeResult themeResult = new ThemeResult(theme, questionsInTheme, results);
                        themeResult.calculateResult();
                        themeResults.add(themeResult);
                }
                SessionResult sessionResult = new SessionResult(currentResult, student, scheme, themeResults);
                return sessionResult;
        }

        private Result finishSession() {
                this.isFinished = true;
                return calculateResult();
        }

        private Result calculateResult() {
                return new Result("Your result is:");
        }
}
