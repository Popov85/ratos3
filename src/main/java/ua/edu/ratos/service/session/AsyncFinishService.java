package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;

@Service
public class AsyncFinishService {

    private ResultService resultService;

    private ResultDetailsService resultDetailsService;

    private GameService gameService;

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @Autowired
    public void setResultDetailsService(ResultDetailsService resultDetailsService) {
        this.resultDetailsService = resultDetailsService;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Async
    @Transactional
    public void saveResult(@NonNull final ResultDomain resultDomain, @NonNull final SessionData sessionData) {
        // Save results to DB, optional LMS include
        Long resultId = resultService.save(resultDomain);
        // Save result details to DB
        resultDetailsService.save(sessionData, resultId);
        // Save gamification points
        gameService.savePoints(resultDomain);
    }
}
