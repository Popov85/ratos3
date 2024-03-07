package ua.edu.ratos.service.session;

import ua.edu.ratos.service.NamedService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

/**
 * See existing implementations:
 * @see ua.edu.ratos.service.session.StaticNextProcessingService
 * @see ua.edu.ratos.service.session.DynamicNextProcessingService
 */
public interface NextProcessingService extends NamedService<String> {

    /**
     * Provides the next batch of questions for end-user.
     * @param batchInDto batch in with user's provided answers
     * @param sessionData session data
     * @return next batch out
     */
    BatchOutDto next(BatchInDto batchInDto, SessionData sessionData);
}
