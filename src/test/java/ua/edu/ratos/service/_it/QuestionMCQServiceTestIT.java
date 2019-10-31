package ua.edu.ratos.service._it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.in.QuestionMCQInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMCQServiceTestIT {

    public static final String JSON_NEW = "classpath:json/question_mcq_in_dto_new.json";
    public static final String FIND = "select q from QuestionMCQ q join fetch q.answers left join fetch q.helps left join fetch q.resources where q.questionId=:questionId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/question_mcq_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        QuestionMCQInDto dto = objectMapper.readValue(json, QuestionMCQInDto.class);
        // Actual test begins
        questionService.save(dto);
        final QuestionMCQ question = (QuestionMCQ) em.createQuery(FIND).setParameter("questionId",1L).getSingleResult();
        assertThat("Found Question object is not as expected", question, allOf(
                hasProperty("questionId", equalTo(1L)),
                hasProperty("question", equalTo("Question #1")),
                hasProperty("answers", hasSize(4)),
                hasProperty("resources", empty()),
                hasProperty("help", hasProperty("present", equalTo(true)))
        ));
    }
}
