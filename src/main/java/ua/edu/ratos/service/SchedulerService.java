package ua.edu.ratos.service;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.quartz.RedisScheduler;

import java.util.Set;

@Service
public class SchedulerService {

    @Autowired
    public Scheduler scheduler;

    @Autowired
    public RedisScheduler redisScheduler;

    public Set<JobKey> allScheduledJobs() {
        return redisScheduler.findAll();
    }

}
