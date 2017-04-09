package ua.zp.zsmu.ratos.learning_session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJPA;
import ua.zp.zsmu.ratos.learning_session.service.*;

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
        @Scope("prototype")
        public ISession randomSession() {
                return new RandomSession();
        }

}
