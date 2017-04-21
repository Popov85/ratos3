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

        private List<Question> questionSequence = new ArrayList<>();

        private Report report;

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
                this.questionSequence = createQuestionSequence(questionSequences);
                this.report = new Report(questionSequences);
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
                List<QuestionResult> results = report.resultSequences.get(theme);
                if (results==null) results = new ArrayList<>();
                results.add(questionResult);
                report.resultSequences.put(theme, results);

                // Check if there is such question in statistics
                // Calculate the time before the right answer is provided

                if (!report.statistics.containsKey(question)) {
                        QuestionStatistics questionStatistics = new QuestionStatistics(123l);
                        report.statistics.put(question, questionStatistics);
                } else {
                        // update time and everything
                }

        }

        @Override
        public Question skipQuestion() {
                if (!scheme.isSkippingEnabled())
                        throw new UnsupportedOperationException("This scheme does not allow skipping!");


                return null;
        }

        @Override
        public String provideHelp() {
                if (!scheme.isHelpAllowed())
                        throw new UnsupportedOperationException("This scheme does not allow help!");

                return null;
        }

        @Override
        public String provideHint() {
                if (scheme.isHintAfterAnswerEnabled())
                        throw new UnsupportedOperationException("This scheme does not allow hints!");

                return null;
        }

        @Override
        public SessionResult getSessionReport() {
                return new SessionResult(currentResult, student, scheme, report.getThemeReport());
        }

        private Report finishSession() {
                this.isFinished = true;
                return null;
        }

        public QuestionResult getQuestionResult(Long qid) {
                if (!report.questionResult.containsKey(qid))
                        throw new IllegalStateException("Report on this question is not yet available!");
                return report.questionResult.get(qid);
        }


}
