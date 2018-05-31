package ua.edu.ratos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import ua.edu.ratos.config.DataSourceConfig;
import ua.edu.ratos.domain.dao.ResourceRepository;
import ua.edu.ratos.domain.dao.ThemeRepository;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Import(DataSourceConfig.class)
public class RatosApplication {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ThemeRepository themeRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	public static void main(String[] args) {
		SpringApplication.run(RatosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void bootstrap() {
		System.out.println("@EventListener annotation on start-up");
		System.out.println("All themes");
		themeRepository.findAll().forEach(System.out::println);
		System.out.println("All resources");
		resourceRepository.findAll().forEach(System.out::println);
		//config();
	}

	private void config() {
		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		System.out.println("BeanDefinitions length: "+beanDefinitionNames.length);
		Arrays.stream(beanDefinitionNames).sorted().forEach((bean) ->
				System.out.println("BeanName::"+context.getBean(bean)
						+"BeanClass::"+context.getBean(bean).getClass()));
	}


/*	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("ua.edu.ratos.domain");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	private final Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}*/
}
