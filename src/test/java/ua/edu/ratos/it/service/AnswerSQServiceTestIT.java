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
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.entity.AnswerSQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerSQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/answer_sq_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/answer_sq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerSequence a left join fetch a.resources where a.answerId=:answerId";

    public static final String PHRASE = "clean";
    public static final String PHRASE_UPD = "cleaning";
    public static final String RESOURCE_DESCRIPTION_1 = "Schema#1";
    public static final String RESOURCE_LINK_1 = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_DESCRIPTION_2 = "Schema#2";
    public static final String RESOURCE_LINK_2 = "https://image.slidesharecdn.com/schema02.jpg";


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AnswerSQService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        AnswerSQInDto dto = objectMapper.readValue(json, AnswerSQInDto.class);
        answerService.save(dto);
        final AnswerSequence foundAnswer =
            (AnswerSequence) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE, foundAnswer.getPhrase());
        Assert.assertEquals(0, foundAnswer.getOrder());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
    }

    @Test
    @Sql(scripts = {"/scripts/answer_sq_test_data.sql", "/scripts/answer_sq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerSQInDto dto = objectMapper.readValue(json, AnswerSQInDto.class);
        answerService.update(dto);
        final AnswerSequence foundAnswer =
            (AnswerSequence) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE_UPD, foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getOrder());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_2, RESOURCE_DESCRIPTION_2)));
    }

    @Test
    @Sql(scripts = {"/scripts/answer_sq_test_data.sql", "/scripts/answer_sq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() throws Exception {
        Assert.assertNotNull(em.find(AnswerSequence.class, 1L));
        answerService.deleteById(1L);
        Assert.assertNull(em.find(AnswerSequence.class, 1L));
    }

}
