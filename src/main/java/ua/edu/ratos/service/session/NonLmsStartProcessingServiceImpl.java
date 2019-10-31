package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;

import java.util.List;

/**
 * Use this implementation for non-LMS sessions.
 */
@Slf4j
@Service
@AllArgsConstructor
public class NonLmsStartProcessingServiceImpl implements StartProcessingService {

    static final String NOT_AVAILABLE_OUTSIDE_LMS = "Requested scheme is not available outside LMS";
    static final String NOT_AVAILABLE_FOR_USER = "Requested scheme is not available for this user";

    private final AppProperties appProperties;

    private final AvailabilityService availabilityService;

    private final SchemeService schemeService;

    private final IndividualSequenceProducer individualSequenceProducer;

    private final SchemeDomainTransformer schemeDomainTransformer;

    private final FirstBatchProducer firstBatchBuilder;

    private final SessionDataService sessionDataService;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public SessionData start(@NonNull final Long schemeId) {
        // Load the requested Scheme and build SessionData object
        final Scheme scheme = schemeService.findByIdForSession(schemeId);
        if (scheme==null || !scheme.isActive())
            throw new IllegalStateException(NOT_AVAILABLE);
        if (scheme.isLmsOnly())
            throw new IllegalStateException(NOT_AVAILABLE_OUTSIDE_LMS);
        Long userId = securityUtils.getAuthUserId();
        if (appProperties.getSession().isIncludeGroups()) {
            if (!availabilityService.isSchemeAvailable(scheme, userId)) {
                throw new IllegalStateException(NOT_AVAILABLE_FOR_USER);
            }
        }
        log.debug("Found available schemeId = {}, containing themes = {}", scheme.getSchemeId(), scheme.getThemes().size());
        // Build individual sequence
        List<QuestionDomain> sequence = individualSequenceProducer.getIndividualSequence(scheme);
        SchemeDomain schemeDomain = schemeDomainTransformer.toDomain(scheme);
        // Build SessionData
        final SessionData sessionData = SessionData.createNoLMS(userId, schemeDomain, sequence);
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = firstBatchBuilder.produce(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("SessionData is built = {}", sessionData);
        log.debug("First batch = {}", batchOutDto);
        return sessionData;
    }

    @Override
    public String name() {
        return "non-lms";
    }
}
