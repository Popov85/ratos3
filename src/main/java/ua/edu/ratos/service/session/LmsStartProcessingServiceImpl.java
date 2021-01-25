package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.transformer.SchemeMapper;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LmsStartProcessingServiceImpl implements StartProcessingService {

    private final SchemeService schemeService;

    private final IndividualSequenceProducer individualSequenceProducer;

    private final SchemeMapper schemeMapper;

    private final FirstBatchProducer firstBatchBuilder;

    private final SessionDataService sessionDataService;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public SessionData start(@NonNull final Long schemeId) {
        // Load the requested Scheme and create SessionData object
        final Scheme scheme = schemeService.findByIdForSession(schemeId);
        if (scheme==null || !scheme.isActive())
            throw new IllegalStateException(NOT_AVAILABLE);
        Long lmsId = securityUtils.getLmsId();
        Long userId = securityUtils.getLmsUserId();
        log.debug("For LMS session found available schemeId = {}, lmsId = {}, userId = {}", scheme.getSchemeId(), lmsId, userId);

        // Build individual sequence
        List<QuestionDomain> sequence = individualSequenceProducer.getIndividualSequence(scheme);
        SchemeDomain schemeDomain = schemeMapper.toDomain(scheme);
        // Build sessionData
        final SessionData sessionData = SessionData.createFromLMS(lmsId, userId, schemeDomain, sequence);
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = firstBatchBuilder.produce(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("For LMS session sessionData is built = {}", sessionData);
        return sessionData;
    }

    @Override
    public String name() {
        return "lms";
    }
}
