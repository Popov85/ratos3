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
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.MetaData;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.EducationalSessionService;
import ua.edu.ratos.service.session.GenericSessionService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenericAndEducationalSessionServiceFBMQTestIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SessionTestFBMQSupport sessionTestFBMQSupport;

    @Autowired
    private GenericSessionService genericSessionService;

    @Autowired
    private EducationalSessionService educationalSessionService;

    //-------------------------------------------------Non-Dynamic, not batched-----------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_fbmq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_fbmq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1FBMQ10PerBatch1Incorrect1Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 FBMQ questions, all are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 1 incorrect question, {10L}
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Map<Long, Response> response;
            if (questionId.equals(10L)) {
                response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, false);
            } else {
                response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, true);
            }
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question and collect the last response
        Map<Long, Response> response;
        log.debug("About to launch finish request...");
        Long questionId = currentBatch.getBatch().get(0).getQuestionId();
        if (questionId.equals(10L)) {
            response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, false);
        } else {
            response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, true);
        }
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(90.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(3, resultOutDto.getPoints().intValue());
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
        final Game game = (Game) em.createQuery("select g from Game g where g.gameId =:gameId").setParameter("gameId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        Assert.assertNotNull(game);
        log.debug("Finished");
    }

    //-------------------------------------------------Non-Dynamic, batched 5-------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_fbmq_scheme_non_dynamic_batched_5.sql", "/scripts/session/case_simple_fbmq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1FBMQ10PerBatch5Incorrect2Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 FBMQ questions, all are requested;
         * 4 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 2 incorrect questions (single incorrect each of 2 batches)
         */

        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i=0;
        while (batchesLeft > 0) {
            Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, currentBatch, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(1, i);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, currentBatch, true);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(80.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(4.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(1, resultOutDto.getPoints().intValue());
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
        // Expected gamification changes: a new single entry in Week (points(5), strike(1), timeSpent(~0)), a new single entry in Game (points(5), bonuses (0), timeSpent(~0)),
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        final Game game = (Game) em.createQuery("select g from Game g where g.gameId =:gameId").setParameter("gameId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        Assert.assertNotNull(game);
        log.debug("Finished");
    }


    //-------------------------------------------------Dynamic, not batched---------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_fbmq_scheme_dynamic_not_batched.sql", "/scripts/session/case_simple_fbmq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1FBMQ10PerBatch1Skip2Incorrect2Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 FBMQ questions, all are requested;
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 2 skips {9L, 10L}, 2 incorrect question {5L, 6L};
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int skipCounter1 = 0;
        int skipCounter2 = 0;
        int incorrectCounter1 = 0;
        int incorrectCounter2 = 0;
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            if (skipCounter1 == 0 && questionId.equals(9L)) {
                educationalSessionService.skip(questionId, sessionData);
                currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
                skipCounter1++;
            } if (skipCounter2 == 0 && questionId.equals(10L)) {
                educationalSessionService.skip(questionId, sessionData);
                currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
                skipCounter2++;
            } else if (incorrectCounter1==0 && questionId.equals(5L)) {
                Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                incorrectCounter1++;
            } else if (incorrectCounter2==0 && questionId.equals(6L)) {
                Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                incorrectCounter2++;
            } else {
                Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                log.debug("Next batch = {}", currentBatch);
            }
            batchCounter++;
        }
        assertEquals(14, batchCounter);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
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


    //-------------------------------------------------Dynamic, batched 10-----------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_fbmq_scheme_dynamic_batched_10.sql", "/scripts/session/case_simple_fbmq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1FBMQ10PerBatch10CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 FBMQ questions, all are requested;
         * 10 questions per batch (all), dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 5 skips
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter == 0) {
                // skipped
                Long skipped1QuestionId = currentBatch.getBatch().get(0).getQuestionId();
                Long skipped2QuestionId = currentBatch.getBatch().get(1).getQuestionId();
                Long skipped3QuestionId = currentBatch.getBatch().get(2).getQuestionId();
                Long skipped4QuestionId = currentBatch.getBatch().get(3).getQuestionId();
                Long skipped5QuestionId = currentBatch.getBatch().get(4).getQuestionId();

                educationalSessionService.skip(skipped1QuestionId, sessionData);
                educationalSessionService.skip(skipped2QuestionId, sessionData);
                educationalSessionService.skip(skipped3QuestionId, sessionData);
                educationalSessionService.skip(skipped4QuestionId, sessionData);
                educationalSessionService.skip(skipped5QuestionId, sessionData);

                Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, currentBatch, false);

                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                batchCounter++;
            } else {
                Map<Long, Response> response = sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response.
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        assertEquals(5, metaData.size());

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
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
