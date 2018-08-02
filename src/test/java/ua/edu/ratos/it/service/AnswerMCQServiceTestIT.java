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
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.repository.AnswerMCQRepository;
import ua.edu.ratos.domain.repository.QuestionMCQRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import java.util.Optional;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMCQServiceTestIT {

    public static final String ANSWER = "Represents an attribute node of an entity graph";
    public static final String ANSWER_UPD = "An attribute node of an EntityGraph";
    public static final String RESOURCE_LINK_1 = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_DESCRIPTION_1 = "Schema#1";
    public static final String RESOURCE_LINK_2 = "https://image.slidesharecdn.com/schema02.jpg";
    public static final String RESOURCE_DESCRIPTION_2 = "Schema#2";

    @Autowired
    private AnswerMCQService answerService;

    @Autowired
    private AnswerMCQRepository answerRepository;
    @Autowired
    private QuestionMCQRepository questionRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        // 1. Try to insert invalid data
        AnswerMCQInDto dto = new AnswerMCQInDto(null, ANSWER, (short)100, true, 0, null);
        answerService.save(dto);
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        // 1. Try to insert valid data
        AnswerMCQInDto dto = new AnswerMCQInDto(null, ANSWER, (short)100, true, 0, 1L);
        answerService.save(dto);

        // 2. Retrieve and compare
        final Optional<AnswerMultipleChoice> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(ANSWER, foundAnswer.get().getAnswer());
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourcesTest() {
        // 1. Try to insert valid data with a resource
        AnswerMCQInDto dto = new AnswerMCQInDto(null, ANSWER, (short)100, true, 1, 1L);
        answerService.save(dto);

        // 2. Retrieve and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        final Set<Resource> resources = foundAnswer.getResources();
        Assert.assertEquals(1, resources.size());
        Assert.assertTrue(resources.contains(new Resource(RESOURCE_LINK_1, RESOURCE_DESCRIPTION_1)));
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        //1. Insert
        insert();

        // 2. Update
        AnswerMCQInDto dto = new AnswerMCQInDto(1L, ANSWER_UPD, (short)100, true, 1, 1L);
        answerService.update(dto);

        // 3. Find and compare
        final Optional<AnswerMultipleChoice> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(ANSWER_UPD, foundAnswer.get().getAnswer());
        Assert.assertEquals((short)100, foundAnswer.get().getPercent());
        Assert.assertTrue(foundAnswer.get().isRequired());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithResourcesTest() {
        //1. Insert
        insert();

        // 2. Update answer, percent and resource {2}
        AnswerMCQInDto dto = new AnswerMCQInDto(1L, ANSWER_UPD, (short)100, true, 2, 1L);
        answerService.update(dto);

        // 3. Find and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_UPD, foundAnswer.getAnswer());
        Assert.assertEquals((short)100, foundAnswer.getPercent());
        Assert.assertTrue(foundAnswer.isRequired());
        Assert.assertEquals(1, foundAnswer.getResources().size());
        Assert.assertTrue(foundAnswer.getResources().contains(new Resource(RESOURCE_LINK_2, RESOURCE_DESCRIPTION_2)));
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithoutResourcesTest() {
        //1. Insert
        insert();

        // 2. Update answer, percent and remove resource : {0}
        AnswerMCQInDto dto = new AnswerMCQInDto(1L, ANSWER_UPD, (short)100, true, 0, 1L);
        answerService.update(dto);

        // 3. Find and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(ANSWER_UPD, foundAnswer.getAnswer());
        Assert.assertEquals((short)100, foundAnswer.getPercent());
        Assert.assertTrue(foundAnswer.isRequired());
        Assert.assertEquals(0, foundAnswer.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceTest() {
        // 1. Insert with a resource
        insert();
        // 2. Add 2-d resource
        answerService.addResource(1L, 2L);
        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertEquals(2, foundAnswer.getResources().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceWrongTest() {
        // 1. Insert with a resource
        insert();
        // 2. Add 2-d resource (1L is already associated with the answer)
        answerService.addResource(1L, 1L);
        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertEquals(1, foundAnswer.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteResourceTest() {
        // 1. Insert with a resource
        insert();
        // 2. Delete this single resource
        answerService.deleteResource(1L, 1L, false);
        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertTrue(foundAnswer.getResources().isEmpty());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() {
        // 1. Insert
        insert();
        Assert.assertTrue(answerRepository.findById(1L).isPresent());
        // 2. Delete
        answerService.deleteById(1L);
        // 3. Make sure deleted
        Assert.assertFalse(answerRepository.findById(1L).isPresent());
    }

    private void insert() {
        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswer(ANSWER);
        answer.setPercent((short)50);
        answer.setRequired(true);
        answer.setQuestion(questionRepository.getOne(1L));
        answer.addResource(resourceRepository.findById(1L).get());
        answerRepository.save(answer);
    }

}
