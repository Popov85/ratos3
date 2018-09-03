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
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;
import ua.edu.ratos.domain.repository.AnswerMQRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.AnswerMQService;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import ua.edu.ratos.service.dto.entity.AnswerMQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/answer_mq_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/answer_mq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerMatcher a left join fetch a.resources where a.answerId=:answerId";

    public static final String LEFT_PHRASE = "Interface used to interact with the second-level cache";
    public static final String LEFT_PHRASE_UPD = "Interface used to interact with the 2d cache";
    public static final String RIGHT_PHRASE = "Cache";
    public static final String RESOURCE_LINK_1 = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_DESCRIPTION_1 = "Schema#1";
    public static final String RESOURCE_LINK_2 = "https://image.slidesharecdn.com/schema02.jpg";
    public static final String RESOURCE_DESCRIPTION_2 = "Schema#2";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AnswerMQService answerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        AnswerMQInDto dto = objectMapper.readValue(json, AnswerMQInDto.class);
        answerService.save(dto);
        final AnswerMatcher foundAnswer =
            (AnswerMatcher) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
    }

    @Test
    @Sql(scripts = {"/scripts/answer_mq_test_data.sql", "/scripts/answer_mq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerMQInDto dto = objectMapper.readValue(json, AnswerMQInDto.class);
        answerService.update(dto);
        final AnswerMatcher foundAnswer =
            (AnswerMatcher) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE_UPD, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertFalse(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_2, RESOURCE_DESCRIPTION_2)));
    }

    @Test
    @Sql(scripts = {"/scripts/answer_mq_test_data.sql", "/scripts/answer_mq_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() throws Exception {
        Assert.assertNotNull(em.find(AnswerMatcher.class, 1L));
        answerService.deleteById(1L);
        Assert.assertNull(em.find(AnswerMatcher.class, 1L));
    }
}
