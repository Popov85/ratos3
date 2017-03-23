package ua.zp.zsmu.ratos.learning_session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.zp.zsmu.ratos.learning_session.service.QuestionService;

/**
 * Created by Andrey on 3/23/2017.
 */
@Configuration
public class SessionConfig {

        @Bean
        public QuestionService questionService() {
                return new QuestionService();
        }
}
