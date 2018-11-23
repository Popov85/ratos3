package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.domain.batch.BatchOut;
import ua.edu.ratos.service.session.dto.ComplaintInDto;
import ua.edu.ratos.service.session.domain.Help;

/**
 * Educational session provides more possibility for students.
 * These operations only available for training sort of sessions, not for knowledge control
 * @author Andrey P.
 */
@Service
public interface EducationalSessionService {

    /**
     * Stops time (for max 12 hours) while SessionData is still alive in memory
     * @param sessionData
     */
    void pause(SessionData sessionData);

    /**
     * Resumes paused learning session within authentication session
     * @param sessionData
     * @return
     */
    BatchOut resume(SessionData sessionData);

    /**
     * Serialize SessionData state to database
     * @param sessionData
     */
    void preserve(SessionData sessionData);

    /**
     * Retrieves preserved dto
     * @param key id in database
     * @return a newly generated key for in-memory storage
     */
    String retrieve(String key);


    /*--------------Ajax-------------*/
    /*---------All without timing control---------*/

    /**
     * Provides helpAvailable for the given question
     * @param questionId
     * @param sessionData
     * @return
     */
    Help help(Long questionId, SessionData sessionData);

    /**
     * Skips the question, puts it to the end of the list to appear in the following batches
     * @param questionId
     * @param sessionData
     */
    void skip(Long questionId, SessionData sessionData);


    /**
     * Star a question with 1 to 5 stars for future review by user himself
     * @param star
     * @param questionId
     * @param userId
     * @param sessionData
     */
    void star(byte star, Long questionId, Long userId, SessionData sessionData);

    /**
     * Complain about a question
     * @param complaint
     * @param questionId
     * @param sessionData
     */
    void complain(ComplaintInDto complaint, Long questionId, SessionData sessionData);
}
