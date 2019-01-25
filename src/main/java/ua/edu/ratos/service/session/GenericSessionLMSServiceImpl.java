package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.SchemeService;

import static ua.edu.ratos.service.session.GenericSessionServiceImpl.NOT_AVAILABLE;

@Slf4j
@Service
public class GenericSessionLMSServiceImpl implements GenericSessionService {

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SessionDataBuilder sessionDataBuilder;

    @Autowired
    private GenericSessionServiceImpl genericSessionService;

    @Autowired
    private BatchBuilder batchBuilder;

    @Autowired
    private SessionDataService sessionDataService;


    @Override
    public SessionData start(@NonNull final StartData startData) {
        // Load the requested Scheme and build SessionData object
        final Scheme scheme = schemeService.findByIdForSession(startData.getSchemeId());
        if (scheme==null || !scheme.isActive())
            throw new IllegalStateException(NOT_AVAILABLE);
        log.debug("Found available scheme ID = {}", scheme.getSchemeId());
        final SessionData sessionData = sessionDataBuilder.build(startData.getKey(), startData.getUserId(), scheme, startData.getLmsId());
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = batchBuilder.build(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("SessionData is built = {}", sessionData);
        return sessionData;
    }

    @Override
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        return genericSessionService.next(batchInDto, sessionData);
    }

    @Override
    public ResultOutDto finish(@NonNull final SessionData sessionData) {
        return genericSessionService.finish(sessionData);
    }

    @Override
    public ResultOutDto cancel(@NonNull final SessionData sessionData) {
        return genericSessionService.cancel(sessionData);
    }
}
