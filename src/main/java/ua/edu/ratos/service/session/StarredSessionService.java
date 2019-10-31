package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.StarredInDto;

/**
 * Operations during learning session to mark selected questions with stars.
 */
public interface StarredSessionService {

    /**
     * Star a question with 1 to 5 stars for future review by user himself.
     * User can view all starred questions via his own account.
     * There is a limit of total questions that can be starred by a user within a RATOS installation (100 by default).
     * @param dto pair of question and stars
     * @param sessionData sessionData object associated with the current learning session
     */
    void star(StarredInDto dto, SessionData sessionData);

    /**
     * Update the value of stares for a question.
     * Can be performed from within a user's personal page.
     * @param dto pair of question and stars
     * @param sessionData sessionData object associated with the current learning session

    void reStar(StarredInDto dto, SessionData sessionData);*/

    /**
     * Remove stars from a question.
     * Can be performed from within a user's personal page.
     * @param questionId question from which to remove stars
     * @param sessionData sessionData object associated with the current learning session
     */
    void unStar(Long questionId, SessionData sessionData);
}
