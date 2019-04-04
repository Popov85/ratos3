package ua.edu.ratos.it.service.session.sequence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.session.sequence.*;

import java.util.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionLoaderTest {

    @Autowired
    private QuestionLoaderFactory questionLoaderFactory;


    @Autowired
    private SchemeRepository schemeRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/questions_all_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void loadAllQuestionsToMapWithSimpleImplTest() {

        // Get Scheme 1L
        // Load questions to map
        // Check what map contains.

        Scheme scheme = schemeRepository.findForSessionById(1L);

        QuestionLoader simpleQuestionLoader = questionLoaderFactory.getQuestionLoader("simple");

        Map<Affiliation, Set<Question>> result = simpleQuestionLoader.loadAllQuestionsToMap(scheme);

        doAsserts(result);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/questions_all_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void loadAllQuestionsToMapWithCachedImplTest() {

        // Get Scheme 1L
        // Load questions to map
        // Check what map contains.

        Scheme scheme = schemeRepository.findForSessionById(1L);

        QuestionLoader simpleQuestionLoader = questionLoaderFactory.getQuestionLoader("cached");

        Map<Affiliation, Set<Question>> result = simpleQuestionLoader.loadAllQuestionsToMap(scheme);

        doAsserts(result);
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/questions_all_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void loadAllQuestionsToMapWithThreadLimitedImplTest() {

        // Get Scheme 1L
        // Load questions to map
        // Check what map contains.

        Scheme scheme = schemeRepository.findForSessionById(1L);

        QuestionLoader simpleQuestionLoader = questionLoaderFactory.getQuestionLoader("thread-limited");

        Map<Affiliation, Set<Question>> result = simpleQuestionLoader.loadAllQuestionsToMap(scheme);

        doAsserts(result);
    }


    private void doAsserts(Map<Affiliation, Set<Question>> result) {
        assertEquals(5, result.size());

        Affiliation key1 = new Affiliation(1L, 1L, (byte) 1, (short) 5);
        Set<Question> questions1 = result.get(key1);
        assertEquals(10, questions1.size());

        Affiliation key2 = new Affiliation(2L, 2L, (byte) 1, (short) 5);
        Set<Question> questions2 = result.get(key2);
        assertEquals(10, questions2.size());

        Affiliation key3 = new Affiliation(3L, 3L, (byte) 1, (short) 5);
        Set<Question> questions3 = result.get(key3);
        assertEquals(10, questions3.size());

        Affiliation key4 = new Affiliation(4L, 4L, (byte) 1, (short) 5);
        Set<Question> questions4 = result.get(key4);
        assertEquals(10, questions4.size());

        Affiliation key5 = new Affiliation(5L, 5L, (byte) 1, (short) 5);
        Set<Question> questions5 = result.get(key5);
        assertEquals(10, questions5.size());
    }
}
