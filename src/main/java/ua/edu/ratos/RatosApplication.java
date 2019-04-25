package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RATOS app
 * @author Andrey P.
 */
@Slf4j
@SpringBootApplication
public class RatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

}
