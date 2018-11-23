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
import ua.edu.ratos.dao.entity.question.QuestionFillBlankSingle;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.entity.QuestionFBSQInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionFBSQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/question_fbsq_in_dto_new.json";
    public static final String QUESTION_NEW = "Question #1";

    public static final String FIND = "select q from QuestionFillBlankSingle q join fetch q.answer left join fetch q.help left join fetch q.resources where q.questionId=:questionId";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        QuestionFBSQInDto dto = objectMapper.readValue(json, QuestionFBSQInDto.class);
        questionService.save(dto);
        final QuestionFillBlankSingle foundQuestion =
            (QuestionFillBlankSingle) em.createQuery(FIND)
                .setParameter("questionId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundQuestion);
        Assert.assertNotNull(foundQuestion.getAnswer());
        Assert.assertEquals(QUESTION_NEW, foundQuestion.getQuestion());
        Assert.assertEquals(1, foundQuestion.getHelp().get().size());
        Assert.assertEquals(1, foundQuestion.getResources().get().size());
    }

}
