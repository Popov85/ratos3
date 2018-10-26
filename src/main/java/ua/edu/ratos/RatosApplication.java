package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import java.util.Date;


@Slf4j
@SpringBootApplication
public class RatosApplication {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() throws Exception {
		if ("dev".equals(this.environment.getProperty("spring.profiles.active")))  {
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/clear.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/init.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/populate.sql"));
			log.info("Data initialized");
		}
		log.info("Launched ratos app {}", new Date());
	}
}
