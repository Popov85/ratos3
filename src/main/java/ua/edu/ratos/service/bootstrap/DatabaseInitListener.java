package ua.edu.ratos.service.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;

@Slf4j
@Service
public class DatabaseInitListener {

    private JdbcTemplate jdbc;

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${ratos.init.re_init}")
    private boolean init;

    @Value("${ratos.init.locale}")
    private String locale;

    @Order(1)
    @EventListener(ContextRefreshedEvent.class)
    public void init() throws Exception {

        // Before switching to the profile, make sure the ratos3 DB exists and empty!
        if ("dev".equals(profile))  {
            if (init) {
                ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("script/clear.sql"));
                ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("script/init.sql"));
                log.info("Data initialized for MySql dev profile at start-up");
            } else {
                log.info("Data will not be initialized for MySql dev profile at start-up");
            }
        }

        // Before switching to the profile, make sure the ratos3 DB is deployed and empty!
        if ("stage".equals(profile)) {
            if (init) {
                ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("script/clear.sql"));
                String init = "init_en";// fallback locale
                if (AppProperties.Init.Language.EN.equals(locale)) init = "init_en";
                if (AppProperties.Init.Language.FR.equals(locale)) init = "init_fr";
                if (AppProperties.Init.Language.RU.equals(locale)) init = "init_ru";
                ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("script/" + init + ".sql"));
                log.info("Staging data initialized for MySql prod profile at start-up");
            } else {
                log.info("Staging data will not be initialized for MySql prod profile at start-up");
            }
        }
    }
}
