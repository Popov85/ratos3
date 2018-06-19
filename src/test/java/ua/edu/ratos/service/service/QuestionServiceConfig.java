package ua.edu.ratos.service.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ua.edu.ratos.service.QuestionService;

@TestConfiguration
public class QuestionServiceConfig {
    @Bean
    public QuestionService questionService() {
        return new QuestionService();
    }
}
