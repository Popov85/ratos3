package ua.edu.ratos.it.service.session.sessions;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.dao.entity.ResultDetails;
import ua.edu.ratos.dao.entity.ResultTheme;
import ua.edu.ratos.dao.entity.ResultThemeId;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.EducationalSessionService;
import ua.edu.ratos.service.session.GenericSessionService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenericAndEducationalSessionServiceMQTestIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SessionTestMQSupport sessionTestMQSupport;

    @Autowired
    private GenericSessionService genericSessionService;

    @Autowired
    private EducationalSessionService educationalSessionService;

    //-------------------------------------------------Non-Dynamic, not batched-----------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_mq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MQ10PerBatch1IncorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MQ questions, all are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all incorrect questions
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question and collect the last response

        log.debug("About to launch finish request...");
        Long questionId = currentBatch.getBatch().get(0).getQuestionId();
        Map<Long, Response> response=sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertFalse(resultOutDto.isPassed());
        assertEquals(0.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(2.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId", resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);

        // Expected gamification changes: a new single entry in Week (points(5), strike(1), timeSpent(~0)), a new single entry in Game (points(5), bonuses (0), timeSpent(~0)),
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }

    //-------------------------------------------------Non-Dynamic, batched 2-------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mq_scheme_non_dynamic_batched_2.sql", "/scripts/session/case_simple_mq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MQ10PerBatch2Correct2Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MQ questions, all are requested;
         * 2 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 2 correct questions in the last batch
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Long question2Id = currentBatch.getBatch().get(1).getQuestionId();
            Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, false);
            Map<Long, Response> response2 = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, question2Id, false);
            response.putAll(response2);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, currentBatch, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertFalse(resultOutDto.isPassed());
        assertEquals(20.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(2.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId", resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);

        // Expected gamification changes: a new single entry in Week (points(5), strike(1), timeSpent(~0)), a new single entry in Game (points(5), bonuses (0), timeSpent(~0)),
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }



    //-------------------------------------------------Dynamic, not batched---------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mq_scheme_dynamic_not_batched.sql", "/scripts/session/case_simple_mq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MQ10PerBatch1Skip0Correct2Test() {
        /*
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MQ questions, all are requested;
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-pyramidal! Incorrect-s are not returned back! Never!
         * Two-point grading.
         * Non-LMS environment;
         * Scenario: Only 2 correct questions {3L, 6L};
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
           if (questionId.equals(3L)) {
                Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else if (questionId.equals(6L)) {
               Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, true);
               currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
           } else {
                Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected result:
        assertFalse(resultOutDto.isPassed());
        assertEquals(20.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(0.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // Gamification is disabled for educational sessions ({skips, pyramid, right answers})
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId", resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);
        log.debug("Finished");
    }


    //-------------------------------------------------Dynamic, batched 3-----------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mq_scheme_dynamic_batched_3.sql", "/scripts/session/case_simple_mq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MQ10PerBatch3Skip1Incorrect4Test() {
        /*
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MQ questions, all are requested;
         * 3 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-pyramidal! Incorrect-s are not returned back! Never!
         * Free-point grading.
         * Non-LMS environment;
         * Scenario: 1 skip in the first batch, single incorrect in each out of 4 batches
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter == 0) {
                // skipped
                Long skippedQuestionId = currentBatch.getBatch().get(0).getQuestionId();
                // perform skip
                educationalSessionService.skip(skippedQuestionId, sessionData);
                batchCounter++;
            }
            Map<Long, Response> response = sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, currentBatch, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            log.debug("Next batch = {}", currentBatch);
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(60.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(120.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // Gamification is disabled for educational sessions ({skips, pyramid, right answers})
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId", resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);
        log.debug("Finished");
    }
}
