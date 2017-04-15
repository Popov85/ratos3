package ua.zp.zsmu.ratos.learning_session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJPA;
import ua.zp.zsmu.ratos.learning_session.service.*;
import ua.zp.zsmu.ratos.learning_session.service.cache.QuestionContainer;

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
        public ThemeDAOJDBC themeDAOJDBC() {
                return new ThemeDAOJDBC();
        }

        @Bean
        public ThemeDAOJPA themeDAOJPA() {
                return new ThemeDAOJPA();
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
        public CachedRandomQuestionProvider cachedRuestionSequenceProducer() {
                return new CachedRandomQuestionProvider();
        }

        @Bean
        public DBRandomQuestionProvider dbRuestionSequenceProducer() {
                return new DBRandomQuestionProvider();
        }

        @Bean
        public QuestionContainer questionContainer() {
                return new QuestionContainer();
        }

}
