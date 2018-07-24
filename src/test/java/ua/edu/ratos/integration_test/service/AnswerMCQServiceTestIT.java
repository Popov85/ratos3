package ua.edu.ratos.integration_test.service;

import org.hibernate.Hibernate;
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
import ua.edu.ratos.domain.repository.StaffRepository;
import ua.edu.ratos.service.AnswerMCQService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerMCQServiceTestIT {

    public static final String ANSWER = "Represents an attribute node of an entity graph";
    public static final String RESOURCE_LINK = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_DESCRIPTION = "Schema#1";

    @Autowired
    private AnswerMCQService answerService;

    @Autowired
    private AnswerMCQRepository answerRepository;

    @Autowired
    private QuestionMCQRepository questionRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)100, true);
        answerService.save(answer, null);
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)100, true);
        answerService.save(answer, 1L);
        Assert.assertTrue(answerRepository.findById(1L).isPresent());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourcesTest() {
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)100, true);
        answer.setResources(new HashSet<>(Arrays.asList(resource)));
        answerService.save(answer, 1L);

        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        final Set<Resource> resources = foundAnswer.getResources();
        Assert.assertTrue(Hibernate.isInitialized(resources));
        Assert.assertEquals(1, resources.size());
        Assert.assertTrue(resources.contains(resource));
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        //1. Insert
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)0, false);
        answer.setQuestion(questionRepository.getOne(1L));
        answerRepository.save(answer);

        // 2. Update
        final AnswerMultipleChoice insertedAnswer = answerRepository.findById(1L).get();
        insertedAnswer.setPercent((short)100);
        insertedAnswer.setRequired(true);
        answerService.update(insertedAnswer);

        // 3. Find and compare
        final Optional<AnswerMultipleChoice> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals((short)100, foundAnswer.get().getPercent());
        Assert.assertTrue(foundAnswer.get().isRequired());
        Assert.assertNotNull(foundAnswer.get().getQuestion());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceTest() {
        // 1. Insert with a resource
        insert();

        // 2. Add 2-d resource
        Resource resource2 = new Resource("https://image.slidesharecdn.com/schema02.jpg", "Schema#02");
        resource2.setStaff(staffRepository.getOne(1L));
        answerService.addResource(resource2, 1L);

        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getResources()));
        Assert.assertEquals(2, foundAnswer.getResources().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceExistingTest() {
        // 1. Insert with a resource
        insert();

        // 2. Add 2-d resource
        Resource resource2 = new Resource("https://image.slidesharecdn.com/schema02.jpg", "Schema#02");
        resource2.setStaff(staffRepository.getOne(1L));
        final Resource savedResource = resourceRepository.save(resource2);
        answerService.addResource(savedResource.getResourceId(), 1L);

        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getResources()));
        Assert.assertEquals(2, foundAnswer.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteResourceTest() {
        // 1. Insert with a resource
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)50, true);
        answer.setQuestion(questionRepository.getOne(1L));
        answer.setResources(new HashSet<>(Arrays.asList(resource)));
        answerRepository.save(answer);

        // 2. Delete this single resource
        answerService.deleteResource(resource.getResourceId(), 1L, false);

        // 3. Find with resources and compare
        final AnswerMultipleChoice foundAnswer = answerRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getResources()));
        Assert.assertTrue(foundAnswer.getResources().isEmpty());
    }

    @Test
    @Sql(scripts = "/scripts/answer_mcq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_mcq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() {
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)100, true);
        answer.setQuestion(questionRepository.getOne(1L));
        answerRepository.save(answer);

        Assert.assertTrue(answerRepository.findById(1L).isPresent());

        answerService.deleteById(1L);

        Assert.assertFalse(answerRepository.findById(1L).isPresent());
    }

    private void insert() {
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        AnswerMultipleChoice answer = new AnswerMultipleChoice(ANSWER, (short)50, true);
        answer.setQuestion(questionRepository.getOne(1L));
        answer.setResources(new HashSet<>(Arrays.asList(resource)));
        answerRepository.save(answer);
    }

}
