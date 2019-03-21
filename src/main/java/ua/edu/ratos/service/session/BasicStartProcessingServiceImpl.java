package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

/**
 * Use this implementation for non-LMS sessions.
 */
@Slf4j
@Service
public class BasicStartProcessingServiceImpl implements StartProcessingService {

    static final String NOT_AVAILABLE_OUTSIDE_LMS = "Requested scheme is not available outside LMS";
    static final String NOT_AVAILABLE_FOR_USER = "Requested scheme is not available for this user";

    private AppProperties appProperties;

    private AvailabilityService availabilityService;

    private SchemeService schemeService;

    private SessionDataBuilder sessionDataBuilder;

    private FirstBatchBuilder firstBatchBuilder;

    private SessionDataService sessionDataService;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setAvailabilityService(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

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

    @TrackTime
    @Override
    public SessionData start(@NonNull final StartData startData) {
        // Load the requested Scheme and build SessionData object
        final Scheme scheme = schemeService.findByIdForSession(startData.getSchemeId());
        if (scheme==null || !scheme.isActive())
            throw new IllegalStateException(NOT_AVAILABLE);
        if (scheme.isLmsOnly())
            throw new IllegalStateException(NOT_AVAILABLE_OUTSIDE_LMS);
        if (appProperties.getSession().isIncludeGroups()) {
            if (!availabilityService.isSchemeAvailable(scheme, startData.getUserId())) {
                throw new IllegalStateException(NOT_AVAILABLE_FOR_USER);
            }
        }
        log.debug("Found available scheme ID = {}, containing themes = {}", scheme.getSchemeId(), scheme.getThemes().size());
        final SessionData sessionData = sessionDataBuilder.build(startData.getKey(), startData.getUserId(), scheme);
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = firstBatchBuilder.build(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("SessionData is built = {}", sessionData);
        return sessionData;
    }

    @Override
    public String type() {
        return "basic";
    }
}
