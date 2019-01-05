package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import java.util.Date;

@Slf4j
@SpringBootApplication()
public class RatosApplication {

	private final JdbcTemplate jdbc;

	@Autowired
	public RatosApplication(final JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Value("${spring.profiles.active}")
	private String profile;

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() throws Exception {
		log.debug("Profile :: {}", profile);

		if ("demo".equals(profile))  {
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/schema.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/init.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/populate.sql"));
			log.debug("Data initialized for h2 demo profile, see /h2-console endpoint for info");
		}

		if ("dev".equals(profile))  {
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/prod/clear.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/prod/init.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/prod/populate.sql"));
			log.debug("Data initialized for mysql dev profile");
		}
		log.info("Launched ratos app {}", new Date());
	}
}
