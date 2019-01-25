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
import ua.edu.ratos.dao.entity.answer.AnswerMCQ;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMCQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/answer_mcq_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/answer_mcq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerMCQ a left join fetch a.resources where a.answerId=:answerId";

    public static final String ANSWER_NEW = "Answer #1";
    public static final String ANSWER_UPD = "Updated answer #1";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AnswerMCQService answerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/answer_mcq_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        AnswerMCQInDto dto = objectMapper.readValue(json, AnswerMCQInDto.class);
        answerService.save(1L, dto);
        final AnswerMCQ foundAnswer = (AnswerMCQ) em.createQuery(FIND).setParameter("answerId",1L).getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_NEW, foundAnswer.getAnswer());
        Assert.assertEquals(100, foundAnswer.getPercent());
        Assert.assertEquals(true, foundAnswer.isRequired());
        Assert.assertTrue(foundAnswer.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/answer_mcq_test_data.sql", "/scripts/answer_mcq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerMCQInDto dto = objectMapper.readValue(json, AnswerMCQInDto.class);
        answerService.update(1L, dto);
        final AnswerMCQ foundAnswer = (AnswerMCQ) em.createQuery(FIND).setParameter("answerId",1L).getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_UPD, foundAnswer.getAnswer());
        Assert.assertEquals(50, foundAnswer.getPercent());
        Assert.assertFalse(foundAnswer.isRequired());
        Assert.assertFalse(foundAnswer.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/answer_mcq_test_data.sql", "/scripts/answer_mcq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() {
        Assert.assertNotNull(em.find(AnswerMCQ.class, 1L));
        answerService.deleteById(1L);
        Assert.assertNull(em.find(AnswerMCQ.class, 1L));
    }
}
