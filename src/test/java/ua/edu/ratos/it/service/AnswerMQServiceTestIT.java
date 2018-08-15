package ua.edu.ratos.it.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;
import ua.edu.ratos.domain.repository.AnswerMQRepository;
import ua.edu.ratos.service.AnswerMQService;
import ua.edu.ratos.service.dto.entity.AnswerMQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMQServiceTestIT {

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
    private AnswerMQRepository answerRepository;


    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        // 1. Try to save with wrong parameters
        AnswerMQInDto dto = new AnswerMQInDto(null, "", "", 0, 0L);
        answerService.save(dto);
    }

    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        // 1. Try to save correct dto
        AnswerMQInDto dto = new AnswerMQInDto(null, LEFT_PHRASE, RIGHT_PHRASE, 0, 1L);
        answerService.save(dto);

        // 2. Retrieve and compare
        final Optional<AnswerMatcher> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(LEFT_PHRASE, foundAnswer.get().getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.get().getRightPhrase());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourcesTest() {
        // 1. Try to save with a resource
        AnswerMQInDto dto = new AnswerMQInDto(null, LEFT_PHRASE, RIGHT_PHRASE, 1, 1L);
        answerService.save(dto);

        // 2. Retrieve and compare
        final AnswerMatcher foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        // 1. Insert correct answer
       insert();

       // 2. Update
        AnswerMQInDto dto = new AnswerMQInDto(1L, LEFT_PHRASE_UPD, RIGHT_PHRASE, 1, 1L);
        answerService.update(dto);

        // 3. Retrieve and compare
        final AnswerMatcher foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE_UPD, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
    }


    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithResourcesTest() {
        // 1. Insert correct answer
        insert();

        // 2. Update with another resource {2L}
        AnswerMQInDto dto = new AnswerMQInDto(1L, LEFT_PHRASE_UPD, RIGHT_PHRASE, 2, 1L);
        answerService.update(dto);

        // 3. Retrieve and compare
        final AnswerMatcher foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE_UPD, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_2, RESOURCE_DESCRIPTION_2)));
    }

    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithoutResourcesTest() {
        // 1. Insert correct answer
        insert();

        // 2. Update without resource
        AnswerMQInDto dto = new AnswerMQInDto(1L, LEFT_PHRASE_UPD, RIGHT_PHRASE, 0, 1L);
        answerService.update(dto);

        // 3. Retrieve and compare
        final AnswerMatcher foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(LEFT_PHRASE_UPD, foundAnswer.getLeftPhrase());
        Assert.assertEquals(RIGHT_PHRASE, foundAnswer.getRightPhrase());
        Assert.assertEquals(0, foundAnswer.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/answer_mq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deletedByIdTest() {
        // 1. Insert correct answer
        insert();
        // 2. Delete
        Assert.assertTrue(answerRepository.findById(1L).isPresent());
        answerService.deletedById(1L);
        // 3. Retrieve and compare
        Assert.assertFalse(answerRepository.findById(1L).isPresent());
    }


    private void insert() {
        AnswerMatcher answer = new AnswerMatcher();
        answer.setLeftPhrase(LEFT_PHRASE);
        answer.setRightPhrase(RIGHT_PHRASE);
        answer.setQuestion(em.getReference(QuestionMatcher.class, 1L));
        answer.addResource(em.find(Resource.class, 1L));
        answerRepository.save(answer);
    }
}
