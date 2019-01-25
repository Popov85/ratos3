package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.ProgressData;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.SessionDataBuilder;
import java.time.LocalDateTime;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SessionDataBuilderTestIT {

    private static final long LEEWAY = 10;

    @Autowired
    private SessionDataBuilder sessionDataBuilder;

    @Autowired
    private SchemeRepository schemeRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session_data_builder.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void buildNoLMSTest()  {
        // We build a SessionData object with 5 different type questions, not from within LMS
        Scheme scheme = schemeRepository.findForSessionById(1L);
        SessionData sessionData = sessionDataBuilder
                .build("38CB1A2F36F6FC1B217D335D87D57376", 1L, scheme);

        Assert.assertEquals("38CB1A2F36F6FC1B217D335D87D57376", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        Assert.assertFalse(sessionData.getLMSId().isPresent());
        Assert.assertNotNull(sessionData.getSchemeDomain());
        Assert.assertEquals(1L, sessionData.getSchemeDomain().getSchemeId().longValue());
        Assert.assertEquals(5, sessionData.getQuestionDomains().size());
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertFalse(sessionData.getCurrentBatch().isPresent());
        Assert.assertEquals(-1, sessionData.getPerQuestionTimeLimit());
        Assert.assertEquals(1, sessionData.getQuestionsPerBatch());
        // Now + 300 sec (60*5)
        Assert.assertTrue(sessionData.getSessionTimeout().isBefore(LocalDateTime.now().plusSeconds(310)));
        Assert.assertTrue(sessionData.getCurrentBatchTimeOut().isEqual(LocalDateTime.MAX));
        Assert.assertTrue(sessionData.getCurrentBatchIssued().isBefore(LocalDateTime.now().plusSeconds(LEEWAY)));
        Assert.assertEquals(0, sessionData.getCurrentIndex());
        // Progress
        ProgressData progressData = sessionData.getProgressData();
        Assert.assertNotNull(progressData);
        Assert.assertEquals(0.0, progressData.getScore(), 0.1);
        Assert.assertEquals(0, progressData.getTimeSpent());
        Assert.assertTrue(progressData.getBatchesEvaluated().isEmpty());
        // Meta
        Assert.assertNotNull(sessionData.getMetaData());
        Assert.assertEquals(0, sessionData.getMetaData().size());
        // methods
        Assert.assertTrue(sessionData.hasMoreQuestions());
        Assert.assertTrue(sessionData.hasMoreTime());
        Assert.assertFalse(sessionData.isLMSSession());

        //log.debug("SessionData = {}", sessionData);
    }
}
