package ua.edu.ratos.service.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.QuestionService;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes=QuestionServiceConfig.class)
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
    public void findByIdTest() throws Exception {
        final Question question = questionService.findOneById(21L);
        log.info("Question :: "+question.getQuestion());
    }


}
