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
import ua.edu.ratos.service.generator.suite.ResultsGeneratorsSuite;
import java.util.Date;

/**
 * This is a web-based educational system for knowledge control embeddable to LMS (Learning Management Systems) named
 * e-RATOS (Embeddable Remote Automatised Teaching and Learning System); <br/>
 * <b>Features:</b>
 * <ul>
 * <li>Embeddable tool provider via LTI 1.x;</li>
 * <li>Unlimited test banks and huge test sets per learning session;</li>
 * <li>5 tried-and-true types of exercises;</li>
 * <li>Control and educational learning sessions;</li>
 * <li>Extensive reports on tests outcomes;</li>
 * <li>Configurable learning schemes: exercise sequence strategies, grading strategies, etc.</li>
 * </ul>
 * @author Andrey P.
 */
@Slf4j
@SpringBootApplication()
public class RatosApplication {

	private final JdbcTemplate jdbc;

/*	@Autowired
	private ResultsGeneratorsSuite resultsGeneratorsSuite;*/

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
		log.debug("Profile = {}", profile);

		if ("demo".equals(profile))  {
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/schema.sql"));
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/init.sql"));
			//resultsGeneratorsSuite.generateMin();
			log.debug("Data initialized for h2 demo profile, see /h2-console endpoint for info");
		}

		if ("dev".equals(profile))  {
			// Dev production DB profile for performance testing
			// Before switch to the profile, make sure the ratos3 DB exists and empty!
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/init.sql"));
			//resultsGeneratorsSuite.generateMax();
			log.debug("Data initialized for MySql dev profile");
		}

		if ("prod".equals(profile))  {
			// Choose locale to init {en, fr, de, ru, ua, pl}
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/prod/init_en.sql"));
			log.debug("Data initialized for mysql dev profile");
		}

		log.info("Launched ratos app {}", new Date());
	}
}
