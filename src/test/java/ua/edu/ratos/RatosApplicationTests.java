package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class RatosApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void contextLoads() {
		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		log.info("AppID: {}, App name:  {}",
				context.getId(),
				(context.getApplicationName() != "") ? context.getApplicationName() : "Not defined");

		log.info("BeanDefinitions length: {}", beanDefinitionNames.length);

		Arrays.stream(beanDefinitionNames).sorted().forEach((bean) ->
				log.info("BeanName: {}, BeanClass {}", bean, context.getBean(bean).getClass()));

	}

}
