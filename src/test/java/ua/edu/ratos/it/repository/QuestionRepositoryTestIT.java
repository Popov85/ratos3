package ua.edu.ratos.it.repository;

import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.question.*;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTestIT {

    @Autowired
    private QuestionRepository questionRepository;


    @Test
    @Sql(scripts = "/scripts/question_test_data_types.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findTypesTest() throws Exception {
        final Set<Long> types = questionRepository.findTypes(1L);
        Assert.assertEquals(3, types.size());
        Assert.assertThat(types, containsInAnyOrder(1L, 2L, 3L));
    }


    @Test
    @Sql(scripts = "/scripts/question_mcq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionMultipleChoice> questions = questionRepository.findAllMCQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionMultipleChoice question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Multiple choice question"));
            Assert.assertEquals(4, question.getAnswers().size());
            question.getAnswers().forEach(a->Assert.assertTrue(Hibernate.isInitialized(a.getResources())));
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().get().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertEquals(1, question.getHelp().get().size());
            question.getHelp().get().forEach(h->Assert.assertTrue(Hibernate.isInitialized(h.getResources())));
        }
    }


    @Test
    @Sql(scripts = "/scripts/question_fbsq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionFillBlankSingle> questions = questionRepository.findAllFBSQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionFillBlankSingle question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Fill blank single question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer()));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer().getSettings()));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswer().getAcceptedPhrases()));
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().get().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertEquals(1, question.getHelp().get().size());
            question.getHelp().get().forEach(h->Assert.assertTrue(Hibernate.isInitialized(h.getResources())));
        }
    }


    @Test
    @Sql(scripts = "/scripts/question_fbmq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionFillBlankMultiple> questions = questionRepository.findAllFBMQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionFillBlankMultiple question : questions) {
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
            Assert.assertEquals(1, question.getResources().get().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertEquals(1, question.getHelp().get().size());
            question.getHelp().get().forEach(h->Assert.assertTrue(Hibernate.isInitialized(h.getResources())));
        }
    }


    @Test
    @Sql(scripts = "/scripts/question_mq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionMatcher> questions = questionRepository.findAllMQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionMatcher question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Matcher question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswers()));
            question.getAnswers().forEach(a->{
                Assert.assertTrue(Hibernate.isInitialized(a.getResources()));
                Assert.assertEquals(1, a.getResources().size());
            });
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().get().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertEquals(1, question.getHelp().get().size());
            question.getHelp().get().forEach(h->Assert.assertTrue(Hibernate.isInitialized(h.getResources())));
        }
    }


    @Test
    @Sql(scripts = "/scripts/question_sq_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQWithEverythingByThemeIdTest() throws Exception {
        Assert.assertEquals(5, questionRepository.findAll().size());
        final Set<QuestionSequence> questions = questionRepository.findAllSQWithEverythingByThemeId(1L);
        Assert.assertEquals(3, questions.size());
        for (QuestionSequence question : questions) {
            Assert.assertTrue(question.getQuestion().startsWith("Sequence question"));
            Assert.assertTrue(Hibernate.isInitialized(question.getAnswers()));
            question.getAnswers().forEach(a->{
                Assert.assertTrue(Hibernate.isInitialized(a.getResources()));
                Assert.assertEquals(1, a.getResources().size());
            });
            Assert.assertTrue(Hibernate.isInitialized(question.getLang()));
            Assert.assertTrue(Hibernate.isInitialized(question.getType()));
            Assert.assertTrue(Hibernate.isInitialized(question.getTheme()));
            Assert.assertTrue(Hibernate.isInitialized(question.getResources()));
            Assert.assertEquals(1, question.getResources().get().size());
            Assert.assertTrue(Hibernate.isInitialized(question.getHelp()));
            Assert.assertEquals(1, question.getHelp().get().size());
            question.getHelp().get().forEach(h->Assert.assertTrue(Hibernate.isInitialized(h.getResources())));
        }
    }

}
