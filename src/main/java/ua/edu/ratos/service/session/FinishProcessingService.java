package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;

public interface FinishProcessingService {

    String CORRUPT_FINISH_REQUEST = "Corrupt finish request, there are still some questions and time to answer!";

    ResultOutDto finish(SessionData sessionData);

    String type();
}
