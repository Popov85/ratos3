package ua.edu.ratos.service._it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.in.QuestionInDto;

import javax.persistence.EntityManager;
import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTestIT {

    public static final String JSON_UPD = "classpath:json/question_in_dto_upd.json";
    public static final String FIND = "select q from QuestionMCQ q join fetch q.answers where q.questionId=:questionId";

    @Autowired
    private EntityManager em;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @Test(timeout = 10000)
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/question_mcq_test_data.sql", "/scripts/question_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        // Given: a question with some long title, of level 1 without any resource and no help present
        File json = ResourceUtils.getFile(JSON_UPD);
        QuestionInDto dto = objectMapper.readValue(json, QuestionInDto.class);
        // Actual test begins
        questionService.update(1L, dto);
        final QuestionMCQ question = (QuestionMCQ) em.createQuery(FIND).setParameter("questionId", 1L).getSingleResult(); //(QuestionMCQ) questionRepository.findById(1L).get();
        assertThat("Updated Question object is not as expected", question, allOf(
                hasProperty("questionId", equalTo(1L)),
                hasProperty("question", equalTo("Updated question")), // Updated
                hasProperty("level", equalTo((byte) 2)), // Updated
                hasProperty("answers", hasSize(4)),
                hasProperty("resources", hasSize(2)), // Updated
                hasProperty("help", hasProperty("present", equalTo(true))) // Updated
        ));
    }

}
