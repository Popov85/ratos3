package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SessionPreserved;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.SessionPreservedRepository;
import ua.edu.ratos.service.session.domain.Mode;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
import ua.edu.ratos.service.session.dto.ComplaintInDto;
import ua.edu.ratos.service.session.domain.Help;
import ua.edu.ratos.service.session.dto.question.QuestionOutDto;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EducationalSessionServiceImpl implements EducationalSessionService {

    private static final String NO_SUCH_QUESTION = "No such questionId found in the current batch";
    private static final String OPERATION_NOT_ALLOWED = "This operation is not allowed by scheme settings";
    private static final String NO_HELP_PRESENT = "No help is available for this question";
    private static final String STAR_OUT_OF_BOUND = "Unsupported star value, only 1 to 5 stars are allowed";

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private UserQuestionStarredService userQuestionStarredService;

    @Autowired
    private SessionDataSerializerService serializer;

    @Autowired
    private SessionPreservedRepository sessionPreservedRepository;

    @Autowired
    private EntityManager em;

    @Override
    public void pause(@NonNull final SessionData sessionData) {
        // TODO
    }

    @Override
    public BatchOutDto resume(@NonNull final SessionData sessionData) {
        // TODO
        return null;
    }



    @Override
    @Transactional
    public String preserve(@NonNull final SessionData sessionData) {
        Mode mode = sessionData.getScheme().getMode();
        if (!mode.isPreservable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        String data = serializer.serialize(sessionData);
        SessionPreserved sessionPreserved = new SessionPreserved();
        sessionPreserved.setKey(sessionData.getKey());
        sessionPreserved.setData(data);
        sessionPreserved.setScheme(em.getReference(Scheme.class, sessionData.getScheme().getSchemeId()));
        sessionPreserved.setUser(em.getReference(User.class, sessionData.getUserId()));
        sessionPreserved.setWhenPreserved(LocalDateTime.now());
        sessionPreservedRepository.save(sessionPreserved);
        // save to DB and return key
        return sessionPreserved.getKey();
    }

    @Override
    @Transactional
    public SessionData retrieve(@NonNull final String key) {
        SessionPreserved sessionPreserved = sessionPreservedRepository.findById(key)
                .orElseThrow(()->new IllegalStateException(key));
        sessionPreservedRepository.deleteById(key);
        return serializer.deserialize(sessionPreserved.getData());
    }

    /*---------------------AJAX---------------------*/

    /**
     * Technically, many Helps can be associated with a question, but for the sake of simplicity
     * we only allow a single Help for now (this can be modified if requirements change)
     * Front-end must ensure that Help is present and is allowed by settings before calling this method
     * @param questionId
     * @param sessionData
     * @return Help DTO (domain actually) or nullable optional object if no Help is associated with this question
     */
    @Override
    public Help help(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        QuestionOutDto question = sessionData.getCurrentBatch().getBatchMap().get(questionId);
        if (question==null) throw new IllegalStateException(NO_SUCH_QUESTION);
        Mode mode = sessionData.getScheme().getMode();
        if (!mode.isHelpable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        Optional<Help> help = sessionData.getQuestionsMap().get(questionId).getHelp();
        if (help.isPresent()) {
            // MetaData processing
            metaDataService.createOrUpdateHelp(sessionData, questionId);
            return help.get();
        }
        throw new IllegalStateException(NO_HELP_PRESENT);
    }

    /**
     * Front-end script must ensure not to call this method twice (hide the skipped question from UI);
     * Front-end must ensure calling next() programmatically if this is the single question in the batchOut
     * Algorithm:
     * 1) Check if Skip is allowed by settings;
     * 2) If so, remove this question from current batchOut, we are not gonna evaluateAndUpdateState it since it is skipped
     * 3) Do shifting to the end;
     * 4) MetaData processing
     * @param questionId
     * @param sessionData
     */
    @Override
    public void skip(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        QuestionOutDto question = sessionData.getCurrentBatch().getBatchMap().get(questionId);
        if (question==null) throw new IllegalStateException(NO_SUCH_QUESTION);
        Mode mode = sessionData.getScheme().getMode();
        if (!mode.isSkipable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        QuestionOutDto questionToRemove = sessionData.getCurrentBatch().getBatchMap().get(questionId);
        sessionData.getCurrentBatch().getBatch().remove(questionToRemove);
        shiftService.doShift(questionId, sessionData);
        // MetaData processing
        metaDataService.createOrUpdateSkip(sessionData, questionId);
    }

    /**
     * User can star questions with 1 up to 5 stars for future review via his own account
     * @param star
     * @param questionId
     * @param userId
     * @param sessionData
     */
    @Override
    @Transactional
    public void star(final byte star, @NonNull final Long questionId, @NonNull final Long userId, @NonNull final SessionData sessionData) {
        QuestionOutDto question = sessionData.getCurrentBatch().getBatchMap().get(questionId);
        if (question==null) throw new IllegalStateException(NO_SUCH_QUESTION);
        Mode mode = sessionData.getScheme().getMode();
        if (!mode.isStarrable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        if (star<1 || star>5) throw new UnsupportedOperationException(STAR_OUT_OF_BOUND);
        // 1. addAnswer to Metadata
        metaDataService.createOrUpdateStar(sessionData, questionId, star);
        // 2. Save to DB
        userQuestionStarredService.save(userId, questionId, star);
    }

    @Override
    @Transactional
    public void complain(@NonNull final ComplaintInDto complaint, @NonNull final Long questionId, @NonNull final SessionData sessionData) {

    }
}
