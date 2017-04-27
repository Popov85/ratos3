package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.ResourceDAO;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.*;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.UnsupportedQuestionTypeException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 4/9/2017.
 */
@Service
public class SessionService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SessionService.class);

        @Autowired
        private SessionDAO sessionDAO;

        @Autowired
        private ResourceDAO resourceDAO;

        @Autowired
        private RandomQuestionProvider randomQuestionProvider;

        @Transactional
        public ISession start(Student student, Scheme scheme) throws RuntimeException {
                // Create Session object
                Session session = create(scheme);
                LOGGER.info("Serializable Session created: "+session);
                // Produce questions
                Map<Theme, List<Question>> questions = randomQuestionProvider.produceQuestionSequence(scheme, false);
                LOGGER.info("Questions generated: "+questions);
                // Create corresponding ISession object
                ISession iSession = LearningSessionFactory.getSession(session.getSid(), session.getBeginTime(), student, scheme, questions);
                LOGGER.info("Learning Session created: "+iSession);
                // Update Session
                update(iSession);
                LOGGER.info("Session updated!");
                return iSession;
        }

        private Session create(Scheme scheme) {
                Session session = new Session();
                Date date = new Date();
                session.setBeginTime(date);
                session.setScheme(scheme);
                return sessionDAO.save(session);
        }

        public void update(ISession iSession) {
                LOGGER.info("iSession to be serialized is: "+iSession);
                byte[] backup = SerializationUtils.serialize(iSession);
                sessionDAO.updateSessionInfoById(backup, new Date(), iSession.getStoredSessionID());
        }

        public ISession restore(Long sid) {
                Session session = sessionDAO.findOne(sid);
                ISession iSession = (ISession) SerializationUtils.deserialize(session.getSession());
                return iSession;
        }

        public Session findOne(Long sid) {
                return sessionDAO.findOne(sid);
        }

        /**
         * It is used when you are certain that there is no resources associated with any question
         * @param iSession
         * @return
         * @throws TimeIsOverException
         */
        public QuestionDTO provideNextQuestionUnchecked(ISession iSession) throws TimeIsOverException {
                return iSession.provideNextQuestion();
        }

        /**
         * It is pretty much the same as provideNextQuestionUnchecked except we check at every nex question request
         * if there are any resources
         * present in DB associated with a question
         * @param iSession
         * @return QuestionDTO
         * @throws TimeIsOverException
         */
        public QuestionDTO provideNextQuestion(ISession iSession) throws TimeIsOverException, UnsupportedQuestionTypeException {
                QuestionDTO question  = iSession.provideNextQuestion();
                // Check if the question has any resources? Enrich the output if so
                // If so - enrich the question with specifications of how to represent this question on the web page
                // Determine what type of question it is:
                // Get List<Resource> for this qid.
                // if empty - go ahead with QuestionDTO
                List<Resource> resources = resourceDAO.findResource(question.getQid());
                if (resources.isEmpty()) return question;
                QuestionView questionView = determineViewType(resources);
                question.setResources(resources);

                return question;
        }

        /**
         * There can be questions of different types:
         * QuestionView.question - no resources (only question, answers and buttons (next, skip, help, hint, etc.))
         * QuestionView.questionImg - contains only one image
         * QuestionView.questionImgMany - contains many question none of which is pressable and the right answer
         * QuestionView.questionImgAnswer - contains many questions some of them (more often one!) is pressable and is the right answer
         *  0 - MediaPlayer (0)
         // 1 - img (767, blob is present)
         // 2 - iFrame (0)
         // 3 - Question (2128)
         // 4 - Answers section(2155)
         // 5 - Next button (2190)
         // 6 - Help button(7)
         // 7 - Hint button(1792)
         // 8 - (0)
         // 9 - Alt message (248)
         // 10 - Skip button (245)
         // 11 - Flash Player(11)
         * @param resources
         * @return
         * @throws UnsupportedQuestionTypeException
         */
        private QuestionView determineViewType(List<Resource> resources) throws UnsupportedQuestionTypeException {
                int countImg = 0;
                int countImgAns = 0;
                for (Resource resource : resources) {
                        int type = resource.getType();
                        if (type!=1) throw new UnsupportedQuestionTypeException("Faced a question of an unsupported type: "+type);
                        countImg++;
                        if (resource.getPercentage()!=0) countImgAns++;
                }
                if (countImg==1) return QuestionView.questionImg;
                if (countImg>1&&countImgAns==0) return QuestionView.questionImgMany;
                if (countImg>1&&countImgAns>0) return QuestionView.questionImgAnswer;
                throw new UnsupportedQuestionTypeException("Faced a question with an unsupported combination of resources");
        }

        public Question provideQuestionsAnswers(ISession iSession) throws TimeIsOverException {
                Question question  = iSession.provideAnswers();
                // TODO: Check if the question has any resources? And prepare the corresponding response
                // If so, prepare this right answers to display correctly
                return question;
        }

}
