package ua.edu.ratos.it.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTestIT {

    @Autowired
    private QuestionRepository questionRepository;


    @Test
    @Sql(scripts = {"/scripts/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_test_data_types.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findTypesTest() throws Exception {
        final Set<Long> types = questionRepository.findTypes(1L);
        Assert.assertEquals(3, types.size());
        Assert.assertThat(types, containsInAnyOrder(1L, 2L, 3L));
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_mcq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionMCQ> questions = questionRepository.findAllMCQWithEverythingByThemeId(1L);
        log.debug("questions of theme  =1 {}"+questions);
        Assert.assertEquals(3, questions.size());
        for (QuestionMCQ question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Multiple choice question"));
            Assert.assertEquals(4, question.getAnswers().size());
            question.getAnswers().forEach(a->Assert.assertTrue(Hibernate.isInitialized(a.getResource())));
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertTrue(question.getHelp().isPresent());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
        }
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_fbsq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionFBSQ> questions = questionRepository.findAllFBSQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionFBSQ question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Fill blank single question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer()));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer().getSettings()));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer().getAcceptedPhrases()));
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertTrue(question.getHelp().isPresent());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp().get()));
        }
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_fbmq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionFBMQ> questions = questionRepository.findAllFBMQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionFBMQ question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Fill blank multiple question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswers()));
            question.getAnswers().forEach(a->{
                Assert.assertTrue(Hibernate.isInitialized(a.getSettings()));
                Assert.assertTrue(Hibernate.isInitialized(a.getAcceptedPhrases()));
            });
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertTrue(question.getHelp().isPresent());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp().get()));
        }
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_mq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionMQ> questions = questionRepository.findAllMQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionMQ question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Matcher question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswers()));
            question.getAnswers().forEach(a->{
                Assert.assertTrue(Hibernate.isInitialized(a.getLeftPhrase()));
                Assert.assertTrue(Hibernate.isInitialized(a.getRightPhrase()));
                Assert.assertTrue(Hibernate.isInitialized(a.getRightPhrase().getResource().get()));
            });
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertTrue(question.getHelp().isPresent());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp().get()));
        }
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/question_sq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionSQ> questions = questionRepository.findAllSQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionSQ question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Sequence question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswers()));
            question.getAnswers().forEach(a->{
                Assert.assertTrue(Hibernate.isInitialized(a.getPhrase()));
                Assert.assertTrue(Hibernate.isInitialized(a.getPhrase().getResource().get()));
            });
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertTrue(question.getHelp().isPresent());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp().get()));
        }
    }

}
