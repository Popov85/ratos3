package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class RatosApplication {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() {
		log.info("Launched app...");
	}

	private void config() {
		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		System.out.println("BeanDefinitions length: "+beanDefinitionNames.length);
		Arrays.stream(beanDefinitionNames).sorted().forEach((bean) ->
				System.out.println("BeanName::"+context.getBean(bean)
						+"BeanClass::"+context.getBean(bean).getClass()));
	}
}
