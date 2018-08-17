package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.AnswerFBSQService;
import ua.edu.ratos.service.dto.entity.AnswerFBSQInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerFBSQServiceTestIT {

    public static final String JSON_UPD = "classpath:json/answer_fbsq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerFillBlankSingle a join fetch a.acceptedPhrases where a.answerId=:answerId";

    public static final String PHRASE1 = "CascadeType";
    public static final String PHRASE2 = "Enum<CascadeType>";
    public static final String PHRASE3_UPD = "javax.persistence.CascadeType";
    public static final long SETTINGS_ID_UPD = 2L;


    @Autowired
    private AnswerFBSQService answerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerFBSQInDto dto = objectMapper.readValue(json, AnswerFBSQInDto.class);
        answerService.update(dto);
        final AnswerFillBlankSingle foundAnswer =
            (AnswerFillBlankSingle) em.createQuery(FIND)
                .setParameter("answerId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(SETTINGS_ID_UPD, foundAnswer.getSettings().getSettingsId().longValue());
        Assert.assertEquals(3, foundAnswer.getAcceptedPhrases().size());
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new AcceptedPhrase(PHRASE1)));
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new AcceptedPhrase(PHRASE2)));
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new AcceptedPhrase(PHRASE3_UPD)));
    }
}
