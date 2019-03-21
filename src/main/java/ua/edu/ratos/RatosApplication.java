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
import ua.edu.ratos.config.properties.AppProperties;

import java.util.Date;

/**
 * This is a web-based educational system for training and knowledge control, embeddable to LMS (Learning Management Systems)
 * called e-RATOS (Embeddable Remote Automatised Teaching and Learning System); <br/>
 * <b>Features:</b>
 * <ul>
 * <li>Embeddable tool provider via LTI 1.x;</li>
 * <li>Unlimited test banks and huge test sets per learning session;</li>
 * <li>5 tried-and-true types of exercises;</li>
 * <li>Control and educational learning sessions;</li>
 * <li>Extensive reports on tests outcomes;</li>
 * <li>Configurable learning schemes: exercise sequence strategies, grading strategies, etc.</li>
 * <li>Gamification</li>
 * </ul>
 *
 * @author Andrey P.
 */
@Slf4j
@SpringBootApplication
public class RatosApplication {

	private final JdbcTemplate jdbc;

	@Autowired
	public RatosApplication(final JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Value("${spring.profiles.active}")
	private String profile;

	@Value("${ratos.init.re_init}")
	private boolean init;

	@Value("${ratos.init.locale}")
	private String locale;

	/*@Autowired
	private SessionSuite sessionSuite;*/

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() throws Exception {

		if ("demo".equals(profile))  {
			ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
					new ClassPathResource("script/demo/schema.sql"));
			if (init) {
				ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
						new ClassPathResource("script/demo/init.sql"));
			}
			log.info("Data initialized for H2 in-memory demo profile, see /h2-console endpoint for info");
		}

		if ("dev".equals(profile))  {
			// Dev production DB profile for performance testing
			// Before switching to the profile, make sure the ratos3 DB exists and empty!
			if (init) {
				ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
						new ClassPathResource("script/prod/clear.sql"));
				ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
						new ClassPathResource("script/prod/init.sql"));
				//sessionSuite.generateAvgAndStep();
				log.info("Data initialized for MySql dev profile");
			}
		}

		// Before switching to the profile, make sure the ratos3 DB is deployed and empty!
		if ("prod".equals(profile)) {
			if (init) {
				ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
						new ClassPathResource("script/prod/clear.sql"));
				String init = "init_en";// fallback locale
				if (AppProperties.Init.Language.EN.equals(locale)) init = "init_en";
				if (AppProperties.Init.Language.FR.equals(locale)) init = "init_fr";
				if (AppProperties.Init.Language.RU.equals(locale)) init = "init_ru";
				ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
						new ClassPathResource("script/prod/" + init + ".sql"));
				log.info("Production data initialized for MySql prod profile");
			}
		}
		log.info("Launched ratos app {}, profile = {}, locale = {}", new Date(), profile, locale);
	}

}
