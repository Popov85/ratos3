package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
 * <li>Turbo speed: load cache on start-up</li>
 * <li>Gamification</li>
 * </ul>
 *
 * @author Andrey P.
 */
@Slf4j
@SpringBootApplication
public class RatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

}
