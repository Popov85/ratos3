package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;
import ua.edu.ratos.service.dto.session.ComplaintInDto;

/**
 * Educational session provides more possibility for students.
 * These operations only available for training sort of sessions, not for knowledge control
 * @author Andrey P.
 */
@Service
public interface EducationalSessionService extends StarredSessionService {

    /**
     * Serialize SessionData state to database.
     * There is a limit of total preservation per user within a RATOS installation (5 by default).
     * @param sessionData sessionData object associated with the current learning session
     * @return key for retrieval this session from DB
     */
    String preserve(SessionData sessionData);


    //------------------------------------------------------Ajax--------------------------------------------------------


    /**
     * Provides help for the given question;
     * Technically, many Helps can be associated with a question, but for the sake of simplicity
     * we only allow a single Help for now (this can be modified if requirements change).
     * Front-end has to ensure that Help is present and is allowed by settings before calling this method
     * @param questionId ID of question whose Help is requested
     * @param sessionData sessionData object associated with the current learning session
     * @return help DTO
     */
    HelpMinOutDto help(Long questionId, SessionData sessionData);


    /**
     * Skips the question, puts it to the end of the list to appear in the following batches;
     * Front-end has to ensure that the question being skipped disappeared from the screen;
     * This is no-reversible operation within a learning session;
     * Front-end has to ensure calling next() programmatically if this is the single question in the batchOut.<br>
     *    Algorithm:
     *    <ol>
     *      <li>Check if Skip is allowed by settings;</li>
     *      <li>If so, remove this question from current batchOut, we are not gonna evaluate it since it is skipped</li>
     *      <li>Do shifting to the end;</li>
     *     <li>MetaData processing</li>
     *    </ol>
     * @param questionId ID of question that is gonna be skipped
     * @param sessionData sessionData object associated with the current learning session
     */
    void skip(Long questionId, SessionData sessionData);


    /**
     * Complain about a question in case it has any issues
     * @param complaint
     * @param sessionData sessionData object associated with the current learning session
     */
    void complain(ComplaintInDto complaint, SessionData sessionData);
}
