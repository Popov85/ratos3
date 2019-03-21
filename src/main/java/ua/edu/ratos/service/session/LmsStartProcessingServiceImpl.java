package ua.edu.ratos.service.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

@Slf4j
@Service
public class LmsStartProcessingServiceImpl implements  StartProcessingService {

    private SchemeService schemeService;

    private SessionDataBuilder sessionDataBuilder;

    private FirstBatchBuilder firstBatchBuilder;

    private SessionDataService sessionDataService;

    @Autowired
    public void setSchemeService(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Autowired
    public void setSessionDataBuilder(SessionDataBuilder sessionDataBuilder) {
        this.sessionDataBuilder = sessionDataBuilder;
    }

    @Autowired
    public void setFirstBatchBuilder(FirstBatchBuilder firstBatchBuilder) {
        this.firstBatchBuilder = firstBatchBuilder;
    }

    @Autowired
    public void setSessionDataService(SessionDataService sessionDataService) {
        this.sessionDataService = sessionDataService;
    }

    @Override
    public SessionData start(StartData startData) {
        // Load the requested Scheme and create SessionData object
        final Scheme scheme = schemeService.findByIdForSession(startData.getSchemeId());
        if (scheme==null || !scheme.isActive())
            throw new IllegalStateException(NOT_AVAILABLE);
        log.debug("For LMS session found available scheme  ID = {}", scheme.getSchemeId());
        Long lmsId = startData.getLmsId().get();
        final SessionData sessionData = sessionDataBuilder.build(startData.getKey(), startData.getUserId(), scheme, lmsId);
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = firstBatchBuilder.build(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("For LMS session sessionData is built = {}", sessionData);
        return sessionData;
    }

    @Override
    public String type() {
        return "lms";
    }
}
