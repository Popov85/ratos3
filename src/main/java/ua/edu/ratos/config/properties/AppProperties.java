package ua.edu.ratos.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@ToString
@Component
@Validated
@ConfigurationProperties("ratos")
public class AppProperties {

    private boolean init;

    private String init_locale;

    private LTI lti;

    private Session session;

    private Game game;

    @Getter
    @Setter
    @ToString
    public static class LTI {
        /**
         * As per LTI v1.1.1, this is the base URL which TC (LMS) should request with POST to perform launch request
         * Change this value for the actual domain name after production deployment
         */
        private String launch_url;
        private String launch_path;

        /**
         * Whether we should replace https (requested) to http (actual) protocols?
         */
        private boolean launch_url_fix;
    }

    @Getter
    @Setter
    @ToString
    public static class Session {
        /**
         * Whether we should take into account student groups when deciding if to allow access to a given schema?
         */
        private boolean include_groups;
        /**
         * Whether we should shuffle answers each time? If true, randomizes answers of totalByType where possible.
         */
        private boolean shuffle_enabled;
        /**
         * How many sessions can be preserved by a single user within this installation of RATOS
         */
        private int preserved_limit;

        /**
         * How many starred totalByType can be kept by a single user within this installation of RATOS
         */
        private int starred_limit;
    }

    @Getter
    @Setter
    @ToString
    public static class Game {
        /**
         * Whether game mode is enabled in this installation of RATOS
         */
        private boolean game_on;

        /**
         * Boundaries for granting points, exceptionally for control questionType sessions:
         * (a user has to pass a test from the first attempt, a scheme must not allow right answers,
         * skips, pyramid, and is time-limited);
         */
        @Min(1)
        private double low_boundary_from;
        private double middle_boundary_from;
        private double high_boundary_from;

        private double low_boundary_to;
        private double middle_boundary_to;
        @Max(100)
        private double high_boundary_to;

        private int low_boundary_points;
        private int middle_boundary_points;
        private int high_boundary_points;

        /**
         * How many strikes in a row (during a week) a user should get to be granted a bonus
         */
        private int bonus_strike;

        /**
         * What is the bonus size (global value)?
         */
        private int bonus_size;

        /**
         * Each week, there is a user rating based on scored points during this week.
         * Top n-% from this rating list are considered to be winners;
         * This parameter represents exactly the desired percentage of users from the top of the list.
         */
        @Min(1)
        @Max(50)
        private int top_weekly;

        /**
         * Day of week when it is considered the end of the week to reset weeklyAchievements results.
         */
        private DayOfWeek day_reset_weekly;

        /**
         * Local time at the designated day to reset the weeklyAchievements results
         */
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime time_reset_weekly;

        /**
         * How a user is titled depending on the number of weeks he won in the competition;
         * Key - number of weeks;
         * Value - corresponding title
         */
        private Map<Integer, String> user_label;
    }


}
