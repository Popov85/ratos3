package ua.edu.ratos.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("ratos")
public class AppProperties {

    private Data data;

    private LTI lti;

    private Session session;

    @Getter
    @Setter
    public static class Data {
        private int collection_size;
        private int table_size;
    }

    @Getter
    @Setter
    public static class LTI {
        private String launch_url;
        private String launch_path;
        private boolean launch_url_fix;
    }

    @Getter
    @Setter
    public static class Session {
        private boolean include_groups;
        private boolean shuffle_enabled;
    }
}
