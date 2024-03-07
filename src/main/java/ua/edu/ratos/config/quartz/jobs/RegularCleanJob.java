package ua.edu.ratos.config.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.ResultDetailsService;

@Slf4j
@Component
@SuppressWarnings("SpellCheckingInspection")
public class RegularCleanJob extends QuartzJobBean {

    //If task failed, re-fire it again in 1 min
    private static final long RETRY_AFTER = 60000;

    private static final int RETRY_TIMES = 5;

    private ResultDetailsService resultDetailsService;

    @Autowired
    public void setResultDetailsService(ResultDetailsService resultDetailsService) {
        this.resultDetailsService = resultDetailsService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            resultDetailsService.cleanExpired();
            log.info("The regular job of cleaning the result details has been successfully done");
        } catch (Exception e) {
            int refireCounter = jobExecutionContext.getRefireCount();
            if (refireCounter>=RETRY_TIMES) {
                log.error("The regular job of cleaning the result details failed after multiple attempts");
            } else {
                log.error("The regular job of cleaning the result details failed..., counter = {}", refireCounter);
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
