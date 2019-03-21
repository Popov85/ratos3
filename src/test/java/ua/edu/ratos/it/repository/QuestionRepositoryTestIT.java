package ua.edu.ratos.it.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.dao.repository.projections.TypeAndCount;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

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
        assertEquals(3, types.size());
        Assert.assertThat(types, containsInAnyOrder(1L, 2L, 3L));
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_test_data_types.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countTypesTest() {
        Set<TypeAndCount> result = questionRepository.countAllTypesByThemeId(1L);
        assertEquals(3, result.size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQForEditByThemeIdTest() {
        final Page<QuestionMCQ> questions = questionRepository.findAllMCQForEditByThemeId(1L, PageRequest.of(0, 50));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMCQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Slice<QuestionMCQ> questions = questionRepository.findAllMCQForSearchByDepartmentIdAndTitleContains(1L, "MCQ", PageRequest.of(0, 30));
        assertEquals(2, questions.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbsq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQForEditByThemeIdTest() {
        final Page<QuestionFBSQ> questions = questionRepository.findAllFBSQForEditByThemeId(1L, PageRequest.of(0, 50));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbsq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBSQForEditByThemeIdAndQuestionLettersContainsTest() {
        Slice<QuestionFBSQ> result = questionRepository.findAllFBSQForSearchByDepartmentIdAndTitleContains(1L, "FBSQ", PageRequest.of(0, 30));
        assertEquals(3, result.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbmq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQForEditByThemeIdTest() {
        final Page<QuestionFBMQ> questions = questionRepository.findAllFBMQForEditByThemeId(1L, PageRequest.of(0, 50));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_fbmq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllFBMQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Slice<QuestionFBMQ> questions = questionRepository.findAllFBMQForSearchByDepartmentIdAndTitleContains(1L, "FBMQ", PageRequest.of(0, 30));
        assertEquals(1, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQForEditByThemeIdTest(){
        final Page<QuestionMQ> questions = questionRepository.findAllMQForEditByThemeId(1L, PageRequest.of(0, 50));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMQForEditByThemeIdAndQuestionLettersContainsTest(){
        final Slice<QuestionMQ> questions = questionRepository.findAllMQForSearchByDepartmentIdAndTitleContains(1L, "MQ", PageRequest.of(0, 30));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_sq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQForEditByThemeIdTest() {
        final Page<QuestionSQ> questions = questionRepository.findAllSQForEditByThemeId(1L, PageRequest.of(0, 50));
        assertEquals(3, questions.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_sq_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllSQForEditByThemeIdAndQuestionLettersContainsTest() {
        final Slice<QuestionSQ> questions = questionRepository.findAllSQForSearchByDepartmentIdAndTitleContains(1L, "SQ", PageRequest.of(0, 50));
        assertEquals(2, questions.getContent().size());
    }


    //--------------------------------------------------STUDENT SESSION JPA---------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_session_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForSessionByThemeIdAndTypeTest() {
        final Set<Question> questions = questionRepository.findAllForSessionByThemeIdAndType(1L, 1L);
        assertEquals(3, questions.size());
    }

    //--------------------------------------------------STUDENT SESSION DB----------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_session_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllRequiredByThemeIdAndTypeAndLevelTest() {
        final Set<Question> questions = questionRepository.findAllRequiredForSessionByThemeIdAndTypeAndLevel(1L, 1L, (byte)1);
        assertEquals(1, questions.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_session_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findNOutOfMForSessionByThemeIdAndTypeAndLevelTest() {
        Set<Question> questions = questionRepository.findNOutOfMForSessionByThemeIdAndTypeAndLevel(2L, 1L, (byte) 1, 2);
        assertEquals(2, questions.size());
    }


}
