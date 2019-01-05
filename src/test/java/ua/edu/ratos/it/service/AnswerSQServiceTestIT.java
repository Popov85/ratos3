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
import ua.edu.ratos.dao.entity.answer.AnswerSQ;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.in.AnswerSQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerSQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/answer_sq_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/answer_sq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerSQ a where a.answerId=:answerId";

    public static final String PHRASE = "phrase";
    public static final String PHRASE_UPD = "updated phrase";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AnswerSQService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        AnswerSQInDto dto = objectMapper.readValue(json, AnswerSQInDto.class);
        answerService.save(dto);
        final AnswerSQ foundAnswer =
            (AnswerSQ) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE, foundAnswer.getPhrase().getPhrase());
        Assert.assertEquals(0, foundAnswer.getOrder());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/answer_sq_test_data.sql", "/scripts/answer_sq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerSQInDto dto = objectMapper.readValue(json, AnswerSQInDto.class);
        answerService.update(1L, dto);
        final AnswerSQ foundAnswer =
            (AnswerSQ) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE_UPD, foundAnswer.getPhrase().getPhrase());
        Assert.assertEquals(1, foundAnswer.getOrder());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/answer_sq_test_data.sql", "/scripts/answer_sq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() throws Exception {
        Assert.assertNotNull(em.find(AnswerSQ.class, 1L));
        answerService.deleteById(1L);
        Assert.assertNull(em.find(AnswerSQ.class, 1L));
    }

}
