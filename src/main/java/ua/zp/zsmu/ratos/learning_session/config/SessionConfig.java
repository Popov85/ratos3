package ua.zp.zsmu.ratos.learning_session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.zp.zsmu.ratos.learning_session.dao.impl.QuestionDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJPA;
import ua.zp.zsmu.ratos.learning_session.service.*;
import ua.zp.zsmu.ratos.learning_session.service.cache.CacheGuava;

/**
 * Created by Andrey on 3/23/2017.
 */
@Configuration
public class SessionConfig {

        @Bean
        public QuestionService questionService() {
                return new QuestionService();
        }

        @Bean
        public SchemeService schemeService() {
                return new SchemeService();
        }

        @Bean
        public ThemeService themeService() {
                return new ThemeService();
        }

        @Bean
        public QuestionDAOJDBC questionDAOJDBC() {
                return new QuestionDAOJDBC();
        }

        @Bean
        public ThemeDAOJPA themeDAOJPA() {
                return new ThemeDAOJPA();
        }

        @Bean
        public ThemeDAOJDBC themeDAOJDBC() {
                return new ThemeDAOJDBC();
        }

        @Bean
        public SessionService sessionService() {
                return new SessionService();
        }

        @Bean
        public RandomQuestionProvider questionSequenceProducer() {
                return new RandomQuestionProvider();
        }

        @Bean
        public CachedRandomQuestionProvider cachedQuestionSequenceProvider() {
                return new CachedRandomQuestionProvider();
        }

        @Bean
        public DBRandomQuestionProvider dbQuestionSequenceProvider() {
                return new DBRandomQuestionProvider();
        }

        @Bean
        public DBQuestionProvider dbQuestionProvider() {
                return new DBQuestionProvider();
        }

        @Bean
        public CacheGuava cache() {
                return new CacheGuava();
        }

        @Bean
        //@Scope("prototype")
        public IPChecker ipChecker() {
                return new IPChecker();
        }

}
