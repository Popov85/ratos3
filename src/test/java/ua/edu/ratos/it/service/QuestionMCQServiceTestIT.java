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
import ua.edu.ratos.service.dto.in.QuestionMCQInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionMCQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/question_mcq_in_dto_new.json";
    public static final String QUESTION_NEW = "Question #1";

    public static final String FIND = "select q from QuestionMCQ q join fetch q.answers left join fetch q.helps left join fetch q.resources where q.questionId=:questionId";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        QuestionMCQInDto dto = objectMapper.readValue(json, QuestionMCQInDto.class);
        questionService.save(dto);
        final QuestionMCQ foundQuestion =
            (QuestionMCQ) em.createQuery(FIND)
                .setParameter("questionId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundQuestion);
        Assert.assertEquals(QUESTION_NEW, foundQuestion.getQuestion());
        Assert.assertEquals(4, foundQuestion.getAnswers().size());
        Assert.assertTrue(foundQuestion.getHelp().isPresent());
        Assert.assertTrue(foundQuestion.getResources().isEmpty());
    }

}
