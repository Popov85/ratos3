package ua.edu.ratos.it.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_test_data_types.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findTypesTest() {
        final Set<Long> types = questionRepository.findTypes(1L);
        Assert.assertEquals(3, types.size());
        Assert.assertThat(types, containsInAnyOrder(1L, 2L, 3L));
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQWithEverythingByThemeIdTest() {
        final Set<QuestionMCQ> questions = questionRepository.findAllMCQWithEverythingByThemeId(1L);
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
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbsq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQWithEverythingByThemeIdTest() {
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
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbmq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQWithEverythingByThemeIdTest() {
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
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQWithEverythingByThemeIdTest(){
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
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_sq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQWithEverythingByThemeIdTest() {
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQForEditByThemeIdTest() {
        final Page<QuestionMCQ> questions = questionRepository.findAllMCQForEditByThemeId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Page<QuestionMCQ> questions = questionRepository.findAllMCQForEditByThemeIdAndQuestionLettersContains(1L, "eng", PageRequest.of(0, 50));
        Assert.assertEquals(1, questions.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbsq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQForEditByThemeIdTest() {
        final Page<QuestionFBSQ> questions = questionRepository.findAllFBSQForEditByThemeId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbsq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Page<QuestionFBSQ> questions = questionRepository.findAllFBSQForEditByThemeIdAndQuestionLettersContains(1L, "Best", PageRequest.of(0, 50));
        Assert.assertEquals(1, questions.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbmq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQForEditByThemeIdTest() {
        final Page<QuestionFBMQ> questions = questionRepository.findAllFBMQForEditByThemeId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbmq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Page<QuestionFBMQ> questions = questionRepository.findAllFBMQForEditByThemeIdAndQuestionLettersContains(1L, "required", PageRequest.of(0, 50));
        Assert.assertEquals(1, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQForEditByThemeIdTest(){
        final Page<QuestionMQ> questions = questionRepository.findAllMQForEditByThemeId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQForEditByThemeIdAndQuestionLettersContainsTest(){
        final Page<QuestionMQ> questions = questionRepository.findAllMQForEditByThemeIdAndQuestionLettersContains(1L, "#2", PageRequest.of(0, 50));
        Assert.assertEquals(2, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_sq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQForEditByThemeIdTest() {
        final Page<QuestionSQ> questions = questionRepository.findAllSQForEditByThemeId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_sq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Page<QuestionSQ> questions = questionRepository.findAllSQForEditByThemeIdAndQuestionLettersContains(1L, "advanced", PageRequest.of(0, 50));
        Assert.assertEquals(1, questions.getContent().size());
    }

}
