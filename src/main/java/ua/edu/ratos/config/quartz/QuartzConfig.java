package ua.edu.ratos.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.config.quartz.jobs.RegularCleanJob;
import ua.edu.ratos.config.quartz.jobs.WeeklyGameJob;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.CronScheduleBuilder.monthlyOnDayAndHourAndMinute;
import static org.quartz.CronScheduleBuilder.weeklyOnDayAndHourAndMinute;
import static org.quartz.DateBuilder.IntervalUnit.SECOND;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@Configuration
@Profile({"prod", "dev", "demo"})
public class QuartzConfig {

    private static final int DELAY = 30;

    private CronResolver cronResolver;

    @Autowired
    public void setCronResolver(CronResolver cronResolver) {
        this.cronResolver = cronResolver;
    }

    @Bean
    @ConditionalOnProperty(prefix = "ratos", name = "result.clean_on", havingValue = "true")
    public JobDetail cleaningJobDetail() {
        return JobBuilder.newJob().ofType(RegularCleanJob.class)
                .withIdentity("Cleaning job")
                .withDescription("This job cleans the ResultDetails table by removing all expired entries")
                .storeDurably(true)
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "ratos", name = "result.clean_on", havingValue = "true")
    public Trigger cleaningTrigger() {
        return newTrigger()
                .forJob(cleaningJobDetail())
                .startAt(futureDate(DELAY, SECOND))
                .withSchedule(cronResolver.getForCleaning())
                .withDescription("This trigger launches regular cleaning job: result details that contains heavy JSON serialized sessions")
                .build();
    }


    @Bean
    @ConditionalOnProperty(prefix = "ratos", name = "game.game_on", havingValue = "true")
    public JobDetail gamificationJobDetail() {
        return JobBuilder.newJob().ofType(WeeklyGameJob.class)
                .withIdentity("Gamification job")
                .withDescription("This job resets the Week table that stores weekly gamification points")
                .storeDurably(true)
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "ratos", name = "game.game_on", havingValue = "true")
    public Trigger gamificationTrigger() {
        return newTrigger()
                .forJob(gamificationJobDetail())
                .startAt(futureDate(DELAY, SECOND))
                .withSchedule(cronResolver.getForGamification())
                .withDescription("This trigger launches weekly gamification job: deciding the winner and resent week table")
                .build();
    }


    @Slf4j
    @Component
    static class CronResolver {

        private AppProperties appProperties;

        @Autowired
        public void setAppProperties(AppProperties appProperties) {
            this.appProperties = appProperties;
        }

        CronScheduleBuilder getForCleaning() {
            CronScheduleBuilder cronScheduleBuilder;
            AppProperties.Result props = appProperties.getResult();
            AppProperties.Result.Period period = props.getPeriod();
            int hour = props.getCleanHour();
            int minute = props.getCleanMinute();
            if (period.equals(AppProperties.Result.Period.DAILY)) {
                cronScheduleBuilder = dailyAtHourAndMinute(hour, minute);
            } else if (period.equals(AppProperties.Result.Period.WEEKLY)) {
                cronScheduleBuilder = weeklyOnDayAndHourAndMinute(1, hour, minute);
            } else if (period.equals(AppProperties.Result.Period.MONTHLY)) {
                cronScheduleBuilder = monthlyOnDayAndHourAndMinute(1, hour, minute);
            } else {
                log.error("Failed to decide when to trigger the cleaning job, period = {}", period);
                throw new IllegalStateException("Failed to decide when to trigger the cleaning job");
            }
            return cronScheduleBuilder;
        }

        CronScheduleBuilder getForGamification() {
            AppProperties.Game props = appProperties.getGame();
            int day = props.getResetWeeklyDay().getValue();
            int dateBuilder;
            if (day==1) {
                dateBuilder = DateBuilder.MONDAY;
            } else if (day==2) {
                dateBuilder = DateBuilder.TUESDAY;
            }  else if (day==3) {
                dateBuilder = DateBuilder.WEDNESDAY;
            }  else if (day==4) {
                dateBuilder = DateBuilder.THURSDAY;
            }  else if (day==5) {
                dateBuilder = DateBuilder.FRIDAY;
            }  else if (day==6) {
                dateBuilder = DateBuilder.SATURDAY;
            }  else if (day==7) {
                dateBuilder = DateBuilder.SUNDAY;
            } else {
                log.error("Failed to decide when to trigger the gamification job, period = {}", day);
                throw new IllegalStateException("Failed to decide when to trigger the gamification job");
            }
            int hour = props.getResetWeeklyHour();
            int minute = props.getResetWeeklyMinute();
            return weeklyOnDayAndHourAndMinute(dateBuilder, hour, minute);
        }
    }
}
