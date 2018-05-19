package ua.edu.ratos.service.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.domain.dao.SessionRepository;

import java.util.Set;

@Slf4j
@Component
public class RedisScheduler {

    private static final String JOB_GROUP = "DEFAULT";

    @Autowired
    private Scheduler scheduler;

    /**
     * Sets time to live (TTL) for a given Redis key
     * @param redisKey, a key to be removed from Redis
     * @param ttl, time to live in sec
     */
    public void setTTL(String redisKey, int ttl) {
        try {
            JobDetail jobDetail = getJobDetail(redisKey, RemoveExpiredSessionJob.class);
            Trigger jobTrigger = getTrigger(ttl, jobDetail);
            scheduler.scheduleJob(jobDetail,jobTrigger);
            log.info("Job is scheduled to remove a redisKey :: {} in :: {} sec", jobDetail, ttl);
        } catch (SchedulerException e) {
            log.error("Failed to schedule a job to remove a redisKey :: {}, error:: {}",redisKey, e);
            throw new RuntimeException(e);
        }
    }

    public Set<JobKey> findAll() {
        Set<JobKey> names;
        try {
            names = scheduler.getJobKeys(GroupMatcher.groupEquals(JOB_GROUP));
        } catch (SchedulerException e) {
            log.error("Failed to get all scheduled jobs :: {}", e);
            throw new RuntimeException(e);
        }
        return names;
    }

    private JobDetail getJobDetail (String key, Class<? extends org.quartz.Job> clazz) {
        return JobBuilder.newJob().ofType(clazz)
                .storeDurably(false)
                .withIdentity(key)
                .withDescription("This key will be removed from Redis store when time expires.")
                .build();
    }

    private Trigger getTrigger(int ttl, JobDetail jobDetail) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.SECOND, ttl);
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withDescription("This trigger fires once to remove an expired dto from Redis store.")
                .startAt(calendar.getTime())
                .build();
    }

    @Slf4j
    @Component
    private static class RemoveExpiredSessionJob implements Job {

        @Autowired
        private SessionRepository sessionRepository;

        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            String key = jobExecutionContext
                    .getJobDetail()
                    .getKey()
                    .getName();
            sessionRepository.deleteById(key);
            log.info("RedisKey is removed due timeout :: {}", key);
        }
    }
}
