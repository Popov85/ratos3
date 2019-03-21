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
import java.util.*;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenericAndEducationalSessionServiceMCQTestIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SessionTestMCQSupport sessionTestMCQSupport;

    @Autowired
    private GenericSessionService genericSessionService;

    @Autowired
    private EducationalSessionService educationalSessionService;

    //-----------------------------------------------Non-dynamic, not batched-------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch1IncorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: no correct answers (empty responses) for any of 10 questions.
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i=0;
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
            // Client clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(9, i);
        // Here you show the last question and collect the last response(empty!)
        log.debug("About to launch finish request...");
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(new HashMap<>()), sessionData);

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

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch1CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        ;
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Long questionId = currentBatch.getBatch().get(0).getQuestionId();
        Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(5, resultOutDto.getPoints().intValue());
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch1Incorrect2Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: majority correct answers for each out of 10 questions, 2 incorrect responses: {2L, 9L};
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Map<Long, Response> response;
            if (questionId.equals(2L) || questionId.equals(9L)) {
                response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, false);
            } else {
                response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
            }
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        ;
        // Here you show the last question and collect the last response
        Map<Long, Response> response;
        log.debug("About to launch finish request...");
        // Create a correct response!
        Long questionId = currentBatch.getBatch().get(0).getQuestionId();
        if (questionId.equals(2L) || questionId.equals(9L)) {
            response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, false);
        } else {
            response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
        }
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
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        final Game game = (Game) em.createQuery("select g from Game g where g.gameId =:gameId").setParameter("gameId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        Assert.assertNotNull(game);
        log.debug("Finished");
    }

    //-----------------------------------------------------Batched all--------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatchAllIncorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, non-dynamic implementation (you launch finish just after first batch);
         * Non-LMS environment;
         * Scenario: no correct answers (empty responses) for all of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First and the last batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        log.debug("batchesLeft = {}", batchesLeft);
        ResultOutDto resultOutDto = null;
        // Here you show the last question and collect the last response(empty!)
        if (batchesLeft == 0) {
            log.debug("About to launch finish request...");
            resultOutDto = genericSessionService.finish(new BatchInDto(new HashMap<>()), sessionData);
        }
        // Expected result:
        assertFalse(resultOutDto.isPassed());
        assertEquals(0.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(2.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
         final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId",1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId",1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId",resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatchAllCorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, non-dynamic implementation (you launch finish just after first batch);
         * Non-LMS environment;
         * Scenario: all are correct answers for every out of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First and the last batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        log.debug("batchesLeft = {}", batchesLeft);
        ResultOutDto resultOutDto = null;
        // Here you show the last question and collect the last response(empty!)
        if (batchesLeft == 0) {
            log.debug("About to launch finish request...");
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, null);
            resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);
        }
        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(5, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId",1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId",1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId",resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatchAllIncorrect1Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, non-dynamic implementation (you launch finish just after first batch);
         * Non-LMS environment;
         * Scenario: all are correct answers except one, incorrect questionId = {5L}
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First and the last batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        log.debug("batchesLeft = {}", batchesLeft);
        ResultOutDto resultOutDto = null;
        // Here you show the last question and collect the last response(empty!)
        if (batchesLeft == 0) {
            log.debug("About to launch finish request...");
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, 5L);
            resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);
        }

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(90.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(3, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        // Expected: new single entry in each: Result, ResultDetails, ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId",1L).getSingleResult();
        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId",1L).getSingleResult();
        ResultThemeId resultThemeId = new ResultThemeId(1L, 1L);
        final ResultTheme resultTheme = (ResultTheme) em.createQuery("select r from ResultTheme r where r.resultThemeId =:resultThemeId").setParameter("resultThemeId",resultThemeId).getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(resultDetails);
        Assert.assertNotNull(resultTheme);

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    //--------------------------------------------------Batched sessions 2 per sheet------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch2IncorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: no correct answers (empty responses) for any of 10 questions.
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i=0;
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
            // Client clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(4, i);
        // Here you show the last question and collect the last response(empty!)
        log.debug("About to launch finish request...");
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(new HashMap<>()), sessionData);

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

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch2CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        ;
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(5, resultOutDto.getPoints().intValue());
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch2Incorrect5Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 5 incorrect-s (1 per batch of 2 questions each) out of 10 in total
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        ;
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(50.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(3.0, resultOutDto.getGrade().doubleValue(), 0.01);
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

    //--------------------------------------------------Batched sessions 3 per sheet------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_3.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch3IncorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 3 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: no correct answers (empty responses) for any of 10 questions.
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i = 0;
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
            // Client clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(3, i);
        // Here you show the last question and collect the last response(empty!)
        log.debug("About to launch finish request...");
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(new HashMap<>()), sessionData);

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

        // Expected gamification changes: a single entry in Week, updated timeSpent
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId =:weekId").setParameter("weekId", 2L).getSingleResult();
        Assert.assertNotNull(week);
        log.debug("Finished");
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_3.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch3CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 3 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions.
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i=0;
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(3, i);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // ResultOutDto(user=Maria Medvedeva, scheme=Sample scheme #1, passed=true, percent=100.0, grade=5.0, points=5, themeResults=null, questionResults=null)
        // log.debug("ResultOutDto = {}", resultOutDto);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(100.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(5, resultOutDto.getPoints().intValue());
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_batched_3.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void nonDynamicCaseS1T1MCQ10PerBatch3Incorrect3Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 3 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: 3 (one per each first 3 batches, each per 3 questions) are incorrect out of 10 in total.
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchesLeft = currentBatch.getBatchesLeft();
        int i=0;
        while (batchesLeft > 0) {
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
            i++;
        }
        assertEquals(3, i);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request...");
        // Create a correct response!
        Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // ResultOutDto(user=Maria Medvedeva, scheme=Sample scheme #1, passed=true, percent=70.0, grade=4.0, points=0, themeResults=null, questionResults=null)
        // log.debug("ResultOutDto = {}", resultOutDto);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(70.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(4.0, resultOutDto.getGrade().doubleValue(), 0.01);
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




    //--------------------------------------------------Dynamic---------------------------------------------------------



    //--------------------------------------------------Not batched-----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch1CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
        }
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

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch1Skip1CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, 1 skip performed questionId = {5L};
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int skipCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            if (skipCounter==0 && questionId.equals(5L)) {
                educationalSessionService.skip(5L, sessionData);
                currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
                log.debug("Skip performed, next batch = {}", currentBatch);
                skipCounter++;
            } else {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);
        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        MetaData meta = metaData.get(5L);
        assertEquals(1, meta.getSkipped());

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

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch1Incorrect1Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, 1 incorrect, questionId = {5L};
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int incorrectCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Long questionId = currentBatch.getBatch().get(0).getQuestionId();
            if (incorrectCounter==0 && questionId.equals(5L)) {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                log.debug("Incorrectly answered, next batch = {}", currentBatch);
                incorrectCounter++;
            } else {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        MetaData meta = metaData.get(5L);
        assertEquals(1, meta.getIncorrect());

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

    //------------------------------------------------Batched all-------------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatchAllCorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
        }
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



    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatchAllSkip3CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, 3 skips performed questionId = {2L, 5L, 7L};
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int skipCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Skip 3 questions and for the rest of them create correct responses!
            if (skipCounter==0) {
                educationalSessionService.skip(2L, sessionData);
                educationalSessionService.skip(5L, sessionData);
                educationalSessionService.skip(7L, sessionData);
                // Basically here we should sent empty responses for skipped questions!!!
                // Instead we send the correct answers that will not be counted as correct with this request.
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                log.debug("3 skips performed, next batch = {}", currentBatch);
                skipCounter++;
            } else {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);
        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        MetaData meta1 = metaData.get(2L);
        MetaData meta2 = metaData.get(5L);
        MetaData meta3 = metaData.get(7L);
        assertEquals(1, meta1.getSkipped());
        assertEquals(1, meta2.getSkipped());
        assertEquals(1, meta3.getSkipped());


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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_all.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatchAllIncorrect3Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * All questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, 3 incorrect-s;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int incorrectCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Skip 3 questions and for the rest of them create correct responses!
            if (incorrectCounter <3) {
                // Basically here we should sent empty responses for skipped questions!!!
                // Instead we send the correct answers that will not be counted as correct with this request.
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                log.debug("Incorrect performed, next batch = {}", currentBatch);
                incorrectCounter++;
            } else {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);
        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        assertEquals(1, metaData.size());

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

    //---------------------------------------------------Batched 2------------------------------------------------------


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch2CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchCounter++;
        }
        assertEquals(5, batchCounter);
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch2Skip1CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, 1 skip performed in 3-d batch;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter==3) {
                // Perform skip in 3-d batch
                Long skippedQuestionId = currentBatch.getBatch().get(0).getQuestionId();
                Long answeredQuestionId = currentBatch.getBatch().get(1).getQuestionId();
                educationalSessionService.skip(skippedQuestionId, sessionData);
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, answeredQuestionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else {
                // Create a correct response!
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
            batchCounter++;
        }
        assertEquals(6, batchCounter);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        assertEquals(1, metaData.size());

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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch2SkipAllCorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions, skip all questions in 2-d batch;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter==2) {
                // Perform skip in 2-d batch
                Long skipped1QuestionId = currentBatch.getBatch().get(0).getQuestionId();
                Long skipped2QuestionId = currentBatch.getBatch().get(1).getQuestionId();
                educationalSessionService.skip(skipped1QuestionId, sessionData);
                educationalSessionService.skip(skipped2QuestionId, sessionData);
                currentBatch = genericSessionService.next(new BatchInDto(new HashMap<>()), sessionData);
            } else {
                // Create a correct response!
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
            batchCounter++;
        }
        assertEquals(6, batchCounter);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        assertEquals(2, metaData.size());

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

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_2.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch2Skip1Incorrect1Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 2 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions except one, skip 1 in batch 2 and perform incorrect in batch 4;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter==2) {
                // Perform skip in 2-d batch
                Long skipped1QuestionId = currentBatch.getBatch().get(0).getQuestionId();
                Long answeredQuestionId = currentBatch.getBatch().get(1).getQuestionId();
                educationalSessionService.skip(skipped1QuestionId, sessionData);
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, answeredQuestionId, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else if (batchCounter==4) {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else {
                // Create a correct response!
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
            batchCounter++;
        }
        assertEquals(6, batchCounter);
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected sessionData state:
        Map<Long, MetaData> metaData = sessionData.getMetaData();
        assertEquals(2, metaData.size());

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

    //----------------------------------------------------Batched 3-----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_3.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch3CorrectAllTest() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 3 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            // Here you show question and collect response (empty!)
            // Create a correct response!
            Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            // Client just clicked button Next>>
            log.debug("Next batch = {}", currentBatch);
            batchCounter++;
        }
        assertEquals(4, batchCounter);
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


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_dynamic_batched_3.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void dynamicCaseS1T1MCQ10PerBatch2Skip1Incorrect2Test() {
        /**
         * UserId = 2L;
         * Simplest case: simple scheme of single theme of 10 MCQ questions, all are requested;
         * 3 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all correct answers for each out of 10 questions except 2, skip 1 in batch 0 and perform incorrect in batch 0 and 1;
         */
        SessionData sessionData = genericSessionService.start(new StartData("123456", 1L, 2L));
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int batchCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            if (batchCounter==0) {
                // Perform skip in 0-st batch
                Long skipped1QuestionId = currentBatch.getBatch().get(0).getQuestionId();
                Long answeredQuestionId = currentBatch.getBatch().get(1).getQuestionId();
                Long answered2QuestionId = currentBatch.getBatch().get(2).getQuestionId();
                educationalSessionService.skip(skipped1QuestionId, sessionData);
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, answeredQuestionId, false);
                Map<Long, Response> response2 = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, answered2QuestionId, true);
                response.putAll(response2);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else if (batchCounter==1) {
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, true);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            } else {
                // Create a correct response!
                Map<Long, Response> response = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, currentBatch, false);
                currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
                // Client just clicked button Next>>
                log.debug("Next batch = {}", currentBatch);
            }
            batchCounter++;
        }
        assertEquals(5, batchCounter);
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
}
