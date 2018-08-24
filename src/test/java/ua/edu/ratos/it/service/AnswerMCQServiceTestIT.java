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
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import javax.persistence.EntityManager;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMCQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/answer_mcq_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/answer_mcq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerMultipleChoice a left join fetch a.resources where a.answerId=:answerId";

    public static final String ANSWER_NEW = "Represents an attribute node of an entity graph";
    public static final String ANSWER_UPD = "An attribute node of an entity graph";
    public static final String RESOURCE_NAME = "Schema#1";
    public static final String RESOURCE_LINK = "https://image.slidesharecdn.com/schema01.jpg";


    @Autowired
    private AnswerMCQService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        AnswerMCQInDto dto = objectMapper.readValue(json, AnswerMCQInDto.class);
        answerService.save(dto);
        final AnswerMultipleChoice foundAnswer =
            (AnswerMultipleChoice) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_NEW, foundAnswer.getAnswer());
        Assert.assertEquals(100, foundAnswer.getPercent());
        Assert.assertEquals(true, foundAnswer.isRequired());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK, RESOURCE_NAME)));
    }

    @Test
    @Sql(scripts = {"/scripts/answer_mcq_test_data.sql", "/scripts/answer_mcq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerMCQInDto dto = objectMapper.readValue(json, AnswerMCQInDto.class);
        answerService.update(dto);
        final AnswerMultipleChoice foundAnswer =
            (AnswerMultipleChoice) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_UPD, foundAnswer.getAnswer());
        Assert.assertEquals(50, foundAnswer.getPercent());
        Assert.assertEquals(false, foundAnswer.isRequired());
        Assert.assertEquals(0, foundAnswer.getResources().size());
    }

    @Test
    @Sql(scripts = {"/scripts/answer_mcq_test_data.sql", "/scripts/answer_mcq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() throws Exception {
        Assert.assertNotNull(em.find(AnswerMultipleChoice.class, 1L));
        answerService.deleteById(1L);
        Assert.assertNull(em.find(AnswerMultipleChoice.class, 1L));
    }
}