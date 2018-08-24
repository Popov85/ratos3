package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.entity.QuestionInDto;
import javax.persistence.EntityManager;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionServiceTestIT {

    public static final String FIND = "select q from QuestionMultipleChoice q join fetch q.answers join fetch q.help join fetch q.resources where q.questionId=:questionId";

    public static final String JSON_UPD = "classpath:json/question_in_dto_upd.json";
    public static final String QUESTION_UPD = "Interface used to interact with the 2d level cache";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/scripts/question_mcq_test_data.sql", "/scripts/question_test_data_upd.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        QuestionInDto dto = objectMapper
                .readValue(json, QuestionInDto.class);
        questionService.update(dto);
        final QuestionMultipleChoice foundQuestion =
            (QuestionMultipleChoice) em.createQuery(FIND)
                .setParameter("questionId", 1L)
                .getSingleResult();
        Assert.assertNotNull(foundQuestion);
        Assert.assertEquals(2, foundQuestion.getLevel());
        Assert.assertEquals(QUESTION_UPD, foundQuestion.getQuestion());
        Assert.assertEquals(1, foundQuestion.getHelp().get().size());
        Assert.assertEquals(2, foundQuestion.getResources().get().size());
    }


    @Test
    @Sql(scripts = {"/scripts/question_mcq_test_data.sql", "/scripts/question_test_data_upd.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() throws Exception {
        QuestionMultipleChoice foundQuestion;
        foundQuestion = em.find(QuestionMultipleChoice.class, 1L);
        Assert.assertNotNull(foundQuestion);
        questionService.deleteById(1L);
        foundQuestion = em.find(QuestionMultipleChoice.class, 1L);
        Assert.assertNull(foundQuestion);
    }



    @Test
    @Sql(scripts = "/scripts/question_mcq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByThemeIdTest() throws Exception {
        // TODO
    }


}
