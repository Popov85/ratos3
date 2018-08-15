package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@Slf4j
@SpringBootApplication
public class RatosApplication {


	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() {
		log.info("Launched app...");
	}


}
