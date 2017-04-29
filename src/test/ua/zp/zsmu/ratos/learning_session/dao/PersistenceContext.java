package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.service.DBQuestionProvider;
import ua.zp.zsmu.ratos.learning_session.service.SchemeService;
import ua.zp.zsmu.ratos.learning_session.service.cache.CacheGuava;
import ua.zp.zsmu.ratos.learning_session.service.IPChecker;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Andrey on 23.03.2017.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ua.zp.zsmu.ratos.learning_session.dao"})
public class PersistenceContext {

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                em.setDataSource(dataSource());
                em.setPackagesToScan(new String[] {"ua.zp.zsmu.ratos"});

                JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
                em.setJpaVendorAdapter(vendorAdapter);
                em.setJpaProperties(additionalProperties());
                return em;
        }

        private Properties additionalProperties() {
                Properties properties = new Properties();
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                return properties;
        }

        @Bean
        public DataSource dataSource(){
                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/ratos");
                dataSource.setUsername("root");
                dataSource.setPassword("root");
                return dataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
                JpaTransactionManager transactionManager = new JpaTransactionManager();
                transactionManager.setEntityManagerFactory(emf);
                return transactionManager;
        }

        @Bean
        public ThemeDAOJDBC themeDAOJDBC() {
                return new ThemeDAOJDBC();
        }

        @Bean
        public CacheGuava cacheGuava() {
                return new CacheGuava();
        }

        @Bean
        public SchemeService schemeService() {
                return new SchemeService();
        }

        @Bean
        public DBQuestionProvider dbQuestionProvider() {
                return new DBQuestionProvider();
        }

        @Bean
        public IPChecker ipChecker() {
                return new IPChecker();
        }
}
