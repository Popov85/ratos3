package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;

@Slf4j
@Service
@SuppressWarnings("SpellCheckingInspection")
public class AsyncFinishProcessingService {

    private AppProperties appProperties;

    private ResultService resultService;

    private ResultDetailsService resultDetailsService;

    private GameService gameService;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

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
    public void saveResults(@NonNull final ResultDomain resultDomain, @NonNull final SessionData sessionData) {
        boolean cancelled = resultDomain.isCancelled();
        boolean timeOuted = resultDomain.isTimeOuted();
        if (gameService.isGameOn()) {
            if (isGameProcessing(cancelled, timeOuted)) {
                // Save gamification points, possible bonuses,
                // increase time spent or reset strike
                double percent = resultDomain.getPercent();
                long timeSpent = resultDomain.getTimeSpent();
                gameService.doGameProcessing(sessionData, percent, timeSpent);
            }
        }
        if (isSaveable(cancelled, timeOuted)) {
            // Save general results to DB, make sure that result
            // contains info about game points if any were gained
            Long resultId = resultService.save(resultDomain);
            // Save result JSON-serialised details to DB
            resultDetailsService.save(sessionData, resultId);
            log.debug("Saved session result = {}", resultDomain);
        }
    }


    private boolean isSaveable(boolean isCancelled, boolean isTimeOuted) {
        AppProperties.Session sessionProps = appProperties.getSession();
        return ((!isCancelled && !isTimeOuted)
                || (isCancelled && sessionProps.isSaveCancelledResults()))
                || (isTimeOuted && sessionProps.isSaveTimeoutedResults());
    }

    private boolean isGameProcessing(boolean isCancelled, boolean isTimeOuted) {
        AppProperties.Game gameProps = appProperties.getGame();
        return ((!isCancelled && !isTimeOuted)
                || (isCancelled && gameProps.isProcessCancelledResults()))
                || (isTimeOuted && gameProps.isProcessTimeoutedResults());
    }
}
