package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.ComplaintService;
import ua.edu.ratos.service.SessionPreservedService;
import ua.edu.ratos.service.domain.HelpDomain;
import ua.edu.ratos.service.domain.ModeDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;
import ua.edu.ratos.service.dto.session.ComplaintInDto;
import ua.edu.ratos.service.dto.session.StarredInDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.transformer.domain_to_dto.HelpDomainDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * No methods throw RunOutOfTimeException cause it is not basically educational,
 * not controlling learning session with session timeout being less important!!!
 */
@Service
public class EducationalSessionServiceImpl implements EducationalSessionService {

    private static final String NO_SUCH_QUESTION = "No such questionId found in the current batch, questionId = ";
    private static final String OPERATION_NOT_ALLOWED = "This operation is not allowed by session settings";
    private static final String PRESERVED_SESSIONS_LIMIT = "You have reached the limit of preserved sessions";
    private static final String NO_HELP_AVAILABLE = "No help is available for this question";
    private static final String STARRED_QUESTIONS_LIMIT = "You have reached the limit of starred questions, remove some to proceed";

    @PersistenceContext
    private EntityManager em;

    private AppProperties appProperties;

    private ShiftService shiftService;

    private MetaDataService metaDataService;

    private UserQuestionStarredService userQuestionStarredService;

    private SessionPreservedService sessionPreservedService;

    private HelpDomainDtoTransformer helpDomainDtoTransformer;

    private ComplaintService complaintService;

    private SecurityUtils securityUtils;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setShiftService(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @Autowired
    public void setMetaDataService(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Autowired
    public void setUserQuestionStarredService(UserQuestionStarredService userQuestionStarredService) {
        this.userQuestionStarredService = userQuestionStarredService;
    }

    @Autowired
    public void setSessionPreservedService(SessionPreservedService sessionPreservedService) {
        this.sessionPreservedService = sessionPreservedService;
    }
    @Autowired
    public void setHelpDomainDtoTransformer(HelpDomainDtoTransformer helpDomainDtoTransformer) {
        this.helpDomainDtoTransformer = helpDomainDtoTransformer;
    }

    @Autowired
    public void setComplaintService(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //--------------------------------------------------PRESERVE--------------------------------------------------------

    @Override
    @Transactional
    public String preserve(@NonNull final SessionData sessionData) {
        ModeDomain modeDomain = sessionData.getSchemeDomain().getModeDomain();
        if (!modeDomain.isPreservable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        Long requestingUserId = securityUtils.getAuthUserId();
        long preservedExist = sessionPreservedService.countByUserId(requestingUserId);
        int preservedLimit = appProperties.getSession().getPreservedLimit();
        if (preservedExist >= preservedLimit) throw new UnsupportedOperationException(PRESERVED_SESSIONS_LIMIT);
        return sessionPreservedService.save(sessionData);
    }

    //-----------------------------------------------------AJAX---------------------------------------------------------

    @Override
    @Transactional
    public void star(@NonNull final StarredInDto starred, @NonNull final SessionData sessionData) {
        ModeDomain modeDomain = validateRequest(starred.getQuestionId(), sessionData);
        if (!modeDomain.isStarrable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        int starredLimit = appProperties.getSession().getStarredLimit();
        if (userQuestionStarredService.countByUserId()>=starredLimit)  throw new UnsupportedOperationException(STARRED_QUESTIONS_LIMIT);
        userQuestionStarredService.save(starred.getQuestionId(), starred.getStars());
    }

    @Override
    @Transactional
    public void reStar(@NonNull final StarredInDto starred, @NonNull final SessionData sessionData) {
        ModeDomain modeDomain = validateRequest(starred.getQuestionId(), sessionData);
        if (!modeDomain.isStarrable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        userQuestionStarredService.updateStars(starred.getQuestionId(), starred.getStars());
    }

    @Override
    @Transactional
    public void unStar(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        ModeDomain modeDomain = validateRequest(questionId, sessionData);
        if (!modeDomain.isStarrable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        userQuestionStarredService.deleteById(questionId);
    }

    @Override
    @Transactional
    public void complain(@NonNull final ComplaintInDto complaint, @NonNull final SessionData sessionData) {
        Long questionId = complaint.getQuestionId();
        ModeDomain modeDomain = validateRequest(questionId, sessionData);
        if (!modeDomain.isReportable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        Long schemeId = sessionData.getSchemeDomain().getSchemeId();
        Scheme scheme = em.find(Scheme.class, schemeId);
        Long depId = scheme.getDepartment().getDepId();
        complaintService.save(questionId, complaint.getComplaintTypeIds(), depId);
    }

    @Override
    public HelpMinOutDto help(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        ModeDomain modeDomain = validateRequest(questionId, sessionData);
        if (!modeDomain.isHelpable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        Optional<HelpDomain> help = sessionData.getQuestionsMap().get(questionId).getHelpDomain();
        if (!help.isPresent()) throw new IllegalStateException(NO_HELP_AVAILABLE);
        metaDataService.createOrUpdateHelp(sessionData, questionId);
        return helpDomainDtoTransformer.toDto(help.get());
    }

    @Override
    public void skip(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        ModeDomain modeDomain = validateRequest(questionId, sessionData);
        if (!modeDomain.isSkipable()) throw new UnsupportedOperationException(OPERATION_NOT_ALLOWED);
        QuestionSessionOutDto questionToRemove = sessionData.getCurrentBatch().get().getBatchMap().get(questionId);
        sessionData.getCurrentBatch().get().getBatch().remove(questionToRemove);
        shiftService.doShift(questionId, sessionData);
        metaDataService.createOrUpdateSkip(sessionData, questionId);
    }

    private ModeDomain validateRequest(@NonNull final Long questionId, @NonNull final SessionData sessionData) {
        QuestionSessionOutDto question = sessionData.getCurrentBatch().get().getBatchMap().get(questionId);
        if (question==null) throw new IllegalStateException(NO_SUCH_QUESTION+questionId);
        return sessionData.getSchemeDomain().getModeDomain();
    }

}
