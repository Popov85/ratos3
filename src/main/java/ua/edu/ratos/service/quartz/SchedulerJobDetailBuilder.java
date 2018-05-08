package ua.edu.ratos.service.quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SchedulerJobDetailBuilder {
/*
    private String key;
    private Class<? extends Job> clazz;

    @Bean
    @Scope(value = "prototype")
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(clazz)
                .storeDurably(false)
                .withIdentity(key)
                .withDescription("This key will be removed from Redis store when time expires.")
                .build();
    }
*/

}
