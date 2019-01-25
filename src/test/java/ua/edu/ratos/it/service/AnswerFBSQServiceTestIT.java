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
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.answer.AnswerFBSQ;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.AnswerFBSQService;
import ua.edu.ratos.service.dto.in.AnswerFBSQInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerFBSQServiceTestIT {

    public static final String JSON_UPD = "classpath:json/answer_fbsq_in_dto_upd.json";

    public static final String FIND = "select a from AnswerFBSQ a join fetch a.acceptedPhrases where a.answerId=:answerId";

    public static final String PHRASE1 = "Phrase #1";
    public static final String PHRASE2 = "Phrase #2";
    public static final String PHRASE3_UPD = "Phrase #3";
    public static final long SETTINGS_ID_UPD = 2L;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AnswerFBSQService answerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/answer_fbsq_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        AnswerFBSQInDto dto = objectMapper.readValue(json, AnswerFBSQInDto.class);
        answerService.update(dto);
        final AnswerFBSQ foundAnswer = (AnswerFBSQ) em.createQuery(FIND).setParameter("answerId",1L).getSingleResult();
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(SETTINGS_ID_UPD, foundAnswer.getSettings().getSettingsId().longValue());
        Assert.assertEquals(3, foundAnswer.getAcceptedPhrases().size());
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new Phrase(PHRASE1)));
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new Phrase(PHRASE2)));
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new Phrase(PHRASE3_UPD)));
    }
}
