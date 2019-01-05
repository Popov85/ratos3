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
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.in.QuestionInDto;
import javax.persistence.EntityManager;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionServiceTestIT {

    public static final String FIND = "select q from QuestionMCQ q join fetch q.answers join fetch q.helps join fetch q.resources where q.questionId=:questionId";

    public static final String JSON_UPD = "classpath:json/question_in_dto_upd.json";
    public static final String QUESTION_UPD = "Updated question";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/question_mcq_test_data.sql", "/scripts/question_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        QuestionInDto dto = objectMapper
                .readValue(json, QuestionInDto.class);
        questionService.update(1L, dto);
        final QuestionMCQ foundQuestion =
            (QuestionMCQ) em.createQuery(FIND)
                .setParameter("questionId", 1L)
                .getSingleResult();
        Assert.assertNotNull(foundQuestion);
        Assert.assertEquals(2, foundQuestion.getLevel());
        Assert.assertEquals(QUESTION_UPD, foundQuestion.getQuestion());
        Assert.assertTrue(foundQuestion.getHelp().isPresent());
        Assert.assertEquals(2, foundQuestion.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/question_mcq_test_data.sql", "/scripts/question_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() throws Exception {
        QuestionMCQ foundQuestion;
        foundQuestion = em.find(QuestionMCQ.class, 1L);
        Assert.assertNotNull(foundQuestion);
        questionService.deleteById(1L);
        foundQuestion = em.find(QuestionMCQ.class, 1L);
        Assert.assertNull(foundQuestion);
    }


}
