package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;
import ua.zp.zsmu.ratos.learning_session.model.*;
import ua.zp.zsmu.ratos.learning_session.service.dto.AnswerDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.ResultDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.DetailedReportDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;
import ua.zp.zsmu.ratos.learning_session.service.util.DateUtil;
import ua.zp.zsmu.ratos.learning_session.service.util.Evaluator;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 4/8/2017.
 */
public class LearningSession implements ISession {

        private static final long serialVersionUID = -8131846952436733376L;
        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LearningSession.class);
        /**
         * ID of the stored backup (in a form of byte[]) of this class in a database
         */
        private final Long sid;

        private final Student student;
        private final Scheme scheme;

        private final Date startTime;

        private List<Question> questionSequence;

        private ReportBuilder reportBuilder;

        private int currentQuestionIndex = 0;
        // In % (e.g. 14.55645645454%)
        private double currentResult = 0d;
        // in milliseconds
        private long timeLeft;
        private int questionsLeft;

        private boolean isInterruptedByUser;
        private boolean isInterruptedByTimeout;
        private boolean isFinished;

        public LearningSession(@NotNull final Long sid, @NotNull final Date startTime, @NotNull final Student student, @NotNull final Scheme scheme,
                               @NotNull final Map<Theme, List<Question>> questionSequences) {
                this.sid = sid;
                this.startTime = startTime;
                this.student = student;
                this.scheme = scheme;
                // Converts time from min -> ms
                this.timeLeft = scheme.getDuration()*60*1000;
                List<Question> resultingQuestionSequence = new ArrayList<>();
                questionSequences.values().forEach(resultingQuestionSequence::addAll);
                this.questionSequence = resultingQuestionSequence;
                this.questionsLeft = this.questionSequence.size();
                this.reportBuilder = new ReportBuilder(questionSequences);
        }

        @Override
        public Long getStoredSessionID() {
                return this.sid;
        }

        @Override
        public ResultDTO interruptSessionByStudent() {
                this.isInterruptedByUser=true;
                return new ResultDTO(sid, student, scheme.getTitle(), currentResult,
                        Evaluator.calculateMark(scheme, currentResult));
        }

        // Call it from Controller/Service layer when TimeIsOverException is caught
        @Override
        public ResultDTO interruptSessionByTimeout() {
                this.isInterruptedByTimeout=true;
                return new ResultDTO(sid, student, scheme.getTitle(), currentResult,
                        Evaluator.calculateMark(scheme, currentResult));
        }

        @Override
        public ResultDTO finishSession() {
                this.isFinished = true;
                return  new ResultDTO(sid, student, scheme.getTitle(), currentResult,
                        Evaluator.calculateMark(scheme, currentResult));
        }

        @Override
        public QuestionDTO provideNextQuestion() throws TimeIsOverException {
                if (isTimeOver()) throw new TimeIsOverException("Time is over!");
                if (isQuestionsRunOut()) throw new IllegalStateException("No more questions!");
                LOGGER.info("Current state: "+this);
                // update timeLeft
                updateTimeLeft();
                // update current index
                currentQuestionIndex++;
                // update questions left
                questionsLeft--;
                Question q = questionSequence.get(currentQuestionIndex);
                return new QuestionDTO(q.getId(), q.getTitle(), createAnswerDTOs(q),
                        timeLeft, questionsLeft, currentResult);
        }

        // Check if the current date is more than startDate + timeForTest
        // Duration is min-based time
        private boolean isTimeOver() {
                Date expectedStopTime = DateUtils.addMinutes(startTime, scheme.getDuration());
                Date currentTime = new Date();
                if (currentTime.compareTo(expectedStopTime)>=0) return true;
                return false;
        }

        private void updateTimeLeft() {
                timeLeft = DateUtil.getDateDiff(new Date(),
                        DateUtils.addMinutes(startTime, scheme.getDuration()), TimeUnit.MILLISECONDS);
        }

        // TODO: to check
        private long calculateQuestionTookTime() {
                return timeLeft - DateUtil.getDateDiff(new Date(),
                        DateUtils.addMinutes(startTime, scheme.getDuration()), TimeUnit.MILLISECONDS);
        }

        private boolean isQuestionsRunOut() {
                return questionsLeft<=0;
        }

        private List<AnswerDTO> createAnswerDTOs(Question q) {
                List<AnswerDTO> answers = new ArrayList<>();
                for (Answer answer : q.getAnswers()) {
                        AnswerDTO a = new AnswerDTO(answer.getId(), answer.getTitle());
                        answers.add(a);
                }
                return answers;
        }

        @Override
        public void processStudentAnswer(List<Long> answers) {
                Question question = questionSequence.get(currentQuestionIndex);
                QuestionResult questionResult = new QuestionResult(question, answers);
                questionResult.calculateResult();
                // calculateCurrentResult();
                // increase counter and divide by the quantity
                Theme theme = question.getTheme();

                // Let ReportBuilder do its job
                reportBuilder.addResult(theme, questionResult);

                // Calculate the time before an answer is provided by student
                reportBuilder.addStatTime(question, calculateQuestionTookTime());
        }

        @Override
        public Question provideAnswers() {
                if (!scheme.isRightAnswerDisplayed())
                        throw new UnsupportedOperationException("This scheme does not provide right answers!");
                return questionSequence.get(currentQuestionIndex);
        }

        @Override
        public QuestionDTO skipQuestion() throws TimeIsOverException {
                if (!scheme.isSkippingEnabled())
                        throw new UnsupportedOperationException("This scheme does not allow skipping!");
                if (isTimeOver()) throw new TimeIsOverException("Time is over!");
                // Update statistics
                Question question = questionSequence.get(currentQuestionIndex);
                reportBuilder.addStatSkip(question, calculateQuestionTookTime());
                // Swap the current question and the last one
                Collections.swap(questionSequence, currentQuestionIndex, questionSequence.size()-1);
                Question q = questionSequence.get(currentQuestionIndex);
                // update time left
                updateTimeLeft();
                return new QuestionDTO(q.getId(), q.getTitle(), createAnswerDTOs(q),
                        timeLeft, questionsLeft, currentResult);
        }

        @Override
        public String provideHelp() throws TimeIsOverException {
                if (!scheme.isHelpAllowed())
                        throw new UnsupportedOperationException("This scheme does not allow help!");
                if (isTimeOver()) throw new TimeIsOverException("Time is over!");
                Question question = questionSequence.get(currentQuestionIndex);
                reportBuilder.addStatHelp(question);
                return question.getHelpString();
        }

        @Override
        public String provideHint() throws TimeIsOverException {
                if (scheme.isHintAfterAnswerEnabled())
                        throw new UnsupportedOperationException("This scheme does not allow hints!");
                if (isTimeOver()) throw new TimeIsOverException("Time is over!");
                Question question = questionSequence.get(currentQuestionIndex);
                reportBuilder.addStatHint(question);
                return question.getHelpTitle();
        }

        @Override
        public DetailedReportDTO getSessionReport() throws IllegalAccessException {
                if (!isFinished&&!isInterruptedByUser&&!isInterruptedByTimeout)
                        throw new IllegalAccessException();
                if (!scheme.isViewLogAllowed()) throw new IllegalAccessException();
                return new DetailedReportDTO(sid, currentResult, student, scheme,
                        Evaluator.calculateMark(scheme, currentResult), reportBuilder.getThemeReport());
        }


        @Override
        public QuestionResult getQuestionResult(Long qid) throws IllegalAccessException {
                if (!reportBuilder.questionResult.containsKey(qid))
                        throw new IllegalStateException("ReportBuilder on this question is not yet available!");
                if (!isFinished&&!isInterruptedByUser&&!isInterruptedByTimeout)
                        throw new IllegalAccessException();
                if (!scheme.isViewLogAllowed()) throw new IllegalAccessException();
                return reportBuilder.questionResult.get(qid);
        }

        @Override
        public String toString() {
                return "LearningSession{" +
                        "sid=" + sid +
                        ", student=" + student +
                        ", startTime=" + startTime +
                        ", currentQuestionIndex=" + currentQuestionIndex +
                        ", currentResult=" + currentResult +
                        ", timeLeft=" + timeLeft +
                        ", questionsLeft=" + questionsLeft +
                        ", isInterruptedByUser=" + isInterruptedByUser +
                        ", isInterruptedByTimeout=" + isInterruptedByTimeout +
                        ", isFinished=" + isFinished +
                        '}';
        }
}
