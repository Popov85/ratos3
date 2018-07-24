package ua.edu.ratos.integration_test.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankMultiple;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankSingle;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.domain.repository.LanguageRepository;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.domain.repository.StaffRepository;
import ua.edu.ratos.service.QuestionService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionServiceTestIT {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @Test(expected = LazyInitializationException.class)
    public void findByIdTest() throws Exception {
        final Question question = questionRepository.findById(1L).get();
        log.info("Question :: "+question.getQuestion());
        log.info("Question.resources :: "+question.getResources());// Here LazyInitializationException is expected
    }

    @Test
    public void updateTest() throws Exception {
        Question updatedQuestion = new QuestionMultipleChoice();
        updatedQuestion.setQuestionId(1L);
        updatedQuestion.setQuestion("Interface used to interact with the second-level cache.");
        updatedQuestion.setLevel((byte)1);
        updatedQuestion.setLang(languageRepository.getOne(1L));
        questionService.update(updatedQuestion);
    }

    @Test
    public void findAllWithEverythingByThemeId() {

        final QuestionMultipleChoice questionMultipleChoice = questionRepository.findAllMCQWithEverythingByThemeId(1L).get(0);
        System.out.println("Question MCQ :: "+ questionMultipleChoice+" Answers :: "+ questionMultipleChoice.getAnswers());

        final QuestionFillBlankSingle questionFillBlankSingle = questionRepository.findAllFBSQWithEverythingByThemeId(1L).get(0);
        System.out.println("Question FBSQ :: "+ questionFillBlankSingle+" Answer :: "+ questionFillBlankSingle.getAnswer());

        final QuestionFillBlankMultiple questionFillBlankMultiple = questionRepository.findAllFBMQWithEverythingByThemeId(1L).get(0);
        System.out.println("Question FBMQ :: "+ questionFillBlankMultiple+" Answers :: "+ questionFillBlankMultiple.getAnswers());
    }

    @Test
    public void findByIdWithResourcesTest() throws Exception {
        final Question question = questionRepository.findByIdWithResources(1L);
        log.info("Question :: "+question);
        Assert.assertTrue(question.getResources().isPresent());
        log.info("Question.resources :: "+question.getResources());
    }

    @Test
    public void addResourceTest() throws Exception {
        Resource resource = new Resource();
        resource.setLink("https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-graph");
        resource.setDescription("Spring Data JPA - Reference Documentation");
        resource.setStaff(staffRepository.getOne(1L));
        questionService.addResource(resource, 2L);
    }

    @Test
    public void removeResourceTest() throws Exception {
        Resource resource = resourceRepository.findById(4L).get();
        questionService.deleteResource(resource, true, 2L);
    }

    @Test
    public void deleteTest() {
        questionService.deleteById(1L);
    }

}
