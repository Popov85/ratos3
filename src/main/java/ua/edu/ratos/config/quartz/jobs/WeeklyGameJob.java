package ua.edu.ratos.config.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.session.GameService;

@Slf4j
@Component
@SuppressWarnings("SpellCheckingInspection")
public class WeeklyGameJob extends QuartzJobBean {

    //If task failed, re-fire it again in 1 min
    private static final long RETRY_AFTER = 60000;

    private static final int RETRY_TIMES = 5;

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            gameService.calculateAndSaveWeeklyWinners();
            log.info("The regular job of calculating weekly winners has been successfully done");
        } catch (Exception e) {
            int refireCounter = jobExecutionContext.getRefireCount();
            if (refireCounter>=RETRY_TIMES) {
                log.error("The regular job of calculating weekly winners failed after multiple attempts");
            } else {
                log.error("The regular job of calculating weekly winners failed..., counter = {}", refireCounter);
                try {
                    Thread.sleep(RETRY_AFTER);
                } catch (InterruptedException e1) {
                    log.error("Interrupted error");
                }
                //fire it again
                throw new JobExecutionException(e, true);
            }
        }
    }
}
