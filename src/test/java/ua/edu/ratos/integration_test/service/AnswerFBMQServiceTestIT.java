package ua.edu.ratos.integration_test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.domain.entity.answer.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankMultiple;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.service.AnswerFBMQService;
import ua.edu.ratos.service.dto.entity.AnswerFBMQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerFBMQServiceTestIT {

    public static final String PHRASE = "Query.setLockMode()";

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private AnswerFBMQService answerService;
    @Autowired
    private AnswerFBMQRepository answerRepository;


    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        // 1. Try to save with wrong parameters
        AnswerFBMQInDto dto = new AnswerFBMQInDto(null, PHRASE,
                (byte)1, 0, 0, null);
        answerService.save(dto);
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveCorrectTest() {
        // 1. Try to save with phrases
        AnswerFBMQInDto dto = new AnswerFBMQInDto(null, PHRASE,
                (byte)1, 1L, 1L, new HashSet<>(Arrays.asList(1L, 2L)));
        answerService.save(dto);

        // 2. Retrieve and compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(PHRASE, foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getOccurrence());
        Assert.assertEquals(2, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        // 1. Insert correct answer
        insert();

        // 2. Update phrase and settings, do not touch accepted phrases collection
        AnswerFBMQInDto updatedDto = new AnswerFBMQInDto(1L, "setLockMode()", (
                byte)1, 2L, 1L, new HashSet<>(Arrays.asList(1L, 2L)));
        answerService.update(updatedDto);

        // 3. Retrieve ans compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals("setLockMode()", foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getOccurrence());
        Assert.assertEquals(2, foundAnswer.getAcceptedPhrases().size());
        Assert.assertEquals(2, foundAnswer.getSettings().getSettingsId().longValue());

    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithPhrasesTest() {
        // 1. Insert with 2 accepted phrases {1L, 2L}
        insert();

        // 2. Update answer: phrase and settings with reduced number of accepted phrases {2L}
        AnswerFBMQInDto updatedDto = new AnswerFBMQInDto(1L, "setLockMode()",
                (byte)1, 2L, 1L, new HashSet<>(Arrays.asList(2L)));
        answerService.update(updatedDto);

        // 3. Retrieve ans compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals("setLockMode()", foundAnswer.getPhrase());
        Assert.assertEquals(1, foundAnswer.getOccurrence());
        Assert.assertEquals(1, foundAnswer.getAcceptedPhrases().size());
        Assert.assertEquals(2, foundAnswer.getSettings().getSettingsId().longValue());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseTest() {
        // 1. Insert with 2 accepted phrases {1L, 2L}
        insert();
        // 2. Add accepted phrase {3L}
        answerService.addAcceptedPhrase(1L, 3L);
        // 3. Retrieve and compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(3, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseExistingTest() {
        // 1. Insert with 2 accepted phrases {1L, 2L}
        insert();
        // 2. Add existing accepted phrase {2L}
        answerService.addAcceptedPhrase(1L, 2L);
        // 3. Retrieve and compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(2, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAcceptedPhraseTest() {
        // 1. Insert with 2 accepted phrases {1L, 2L}
        insert();
        // 2. Delete accepted phrase {2L}
        answerService.deleteAcceptedPhrase(1L, 2L);
        // 3. Retrieve and compare
        final AnswerFillBlankMultiple foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(1, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbmq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbmq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        // 1. Insert correct answer
        insert();
        Assert.assertTrue(answerRepository.findById(1L).isPresent());
        // 2. Delete
        answerService.deleteById(1L);
        // 3. Retrieve and get null
        Assert.assertFalse(answerRepository.findById(1L).isPresent());
    }


    private void insert() {
        AnswerFillBlankMultiple answer = new AnswerFillBlankMultiple();
        answer.setPhrase(PHRASE);
        answer.setOccurrence((byte)1);
        answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, 1L));
        answer.setQuestion(em.getReference(QuestionFillBlankMultiple.class, 1L));
        answer.addPhrase(em.find(AcceptedPhrase.class, 1L));
        answer.addPhrase(em.find(AcceptedPhrase.class, 2L));
        answerRepository.save(answer);
    }

}
