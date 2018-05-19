package ua.edu.ratos.service;

import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.Answer;
import ua.edu.ratos.service.dto.Batch;
import ua.edu.ratos.service.dto.Help;

import java.util.List;

/**
 * Educational session provides more possibility for students.
 * These operation only available for training sort of sessions, not knowledge control
 * @author Andrey P.
 */
@Service
public interface EducationalSession {

    /**
     * Stops time (for max 12 hours) while session is still alive in memory
     * @param key
     */
    void pause(String key);

    /**
     * Resumes paused session
     * @param key
     * @return
     */
    Batch resume(String key);

    /**
     * Serialize session state to database
     * @param key
     */
    void preserve(String key);

    /**
     * Retrieves preserved session
     * @param key id in database
     * @return a newly generated key for im-memory storage
     */
    String retrieve(String key);


    /*--------------Ajax-------------*/

    /**
     * Provides right answers to the question
     * @param key Session key
     * @param qid Question id in database
     * @return
     */
    List<Answer> answer(String key, long qid);

    /**
     * Provides help for the given question
     * @param key
     * @param qid
     * @return
     */
    Help help(String key, long qid);

    /**
     * Skips the question, puts it to the end of the list to appear in the following batches
     * @param key
     * @param qid
     */
    void skip(String key, long qid);

    /**
     * There are the following types of complaints supported:
     * -Incorrect statement of question
     * -Typo in question
     * -Typo in an answer
     * -Bad question formatting
     * -Bad answer formatting
     * @param qid
     * @param complaint
     */
    void complain(String complaint, long qid);

}
