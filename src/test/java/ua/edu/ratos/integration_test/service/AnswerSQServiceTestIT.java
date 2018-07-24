package ua.edu.ratos.integration_test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.domain.entity.question.QuestionSequence;
import ua.edu.ratos.domain.repository.AnswerSQRepository;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.entity.AnswerSQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerSQServiceTestIT {

    public static final String PHRASE = "clean";
    public static final String PHRASE_UPD = "cleaning";
    public static final String RESOURCE_LINK_1 = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_DESCRIPTION_1 = "Schema#1";
    public static final String RESOURCE_LINK_2 = "https://image.slidesharecdn.com/schema02.jpg";
    public static final String RESOURCE_DESCRIPTION_2 = "Schema#2";

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private AnswerSQService answerService;
    @Autowired
    private AnswerSQRepository answerRepository;


    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        // 1. Try to save with wrong parameters
        AnswerSQInDto dto = new AnswerSQInDto(null, "", (short)1, 0, null);
        answerService.save(dto);
    }

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        // 1. Try to save correct dto
        AnswerSQInDto dto = new AnswerSQInDto(null, PHRASE, (short)1, 0, 1L);
        answerService.save(dto);
        // 2. Retrieve and compare
        final Optional<AnswerSequence> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(PHRASE, foundAnswer.get().getPhrase());
    }

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourcesTest() {
        // 1. Try to save with a resource
        AnswerSQInDto dto = new AnswerSQInDto(null, PHRASE, (short)1, 1L, 1L);
        answerService.save(dto);
        // 2. Retrieve and compare
        final AnswerSequence foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE, foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
    }

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        // 1. Insert correct answer
        insert();
        // 2. Update
        AnswerSQInDto dto = new AnswerSQInDto(1L, PHRASE_UPD, (short)1, 0, 1L);
        answerService.update(dto);
        // 3. Retrieve and compare
        final Optional<AnswerSequence> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(PHRASE_UPD, foundAnswer.get().getPhrase());
    }

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithResourcesTest() {
        // 1. Insert correct answer
        insert();
        // 2. Update with another resource {2L}
        AnswerSQInDto dto = new AnswerSQInDto(1L, PHRASE_UPD, (short)1, 2L, 1L);
        answerService.update(dto);
        // 3. Retrieve and compare
        final AnswerSequence foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE_UPD, foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_2, RESOURCE_DESCRIPTION_2)));
    }

    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithoutResourcesTest() {
        // 1. Insert correct answer
        insert();
        // 2. Update without resource
        AnswerSQInDto dto = new AnswerSQInDto(1L, PHRASE_UPD, (short)1, 0, 1L);
        answerService.update(dto);
        // 3. Retrieve and compare
        final AnswerSequence foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE_UPD, foundAnswer.getPhrase());
        Assert.assertEquals(0, foundAnswer.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/answer_sq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_sq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deletedByIdTest() {
        // 1. Insert correct answer
        insert();
        Assert.assertTrue(answerRepository.findById(1L).isPresent());
        answerService.deletedById(1L);
        // 3. Retrieve and compare
        Assert.assertFalse(answerRepository.findById(1L).isPresent());
    }

    private void insert() {
        AnswerSequence answer = new AnswerSequence();
        answer.setPhrase(PHRASE);
        answer.setOrder((short)1);
        answer.setQuestion(em.getReference(QuestionSequence.class, 1L));
        answer.addResource(em.find(Resource.class, 1L));
        answerRepository.save(answer);
    }

}
