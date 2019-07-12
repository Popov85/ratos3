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
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.session.EducationalSessionService;
import ua.edu.ratos.service.session.GenericSessionService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenericAndEducationalSessionServiceAllTestIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SessionTestFBSQSupport sessionTestFBSQSupport;

    @Autowired
    private SessionTestFBMQSupport sessionTestFBMQSupport;

    @Autowired
    private SessionTestMCQSupport sessionTestMCQSupport;

    @Autowired
    private SessionTestMQSupport sessionTestMQSupport;

    @Autowired
    private SessionTestSQSupport sessionTestSQSupport;

    @Autowired
    private GenericSessionService genericSessionService;

    @Autowired
    private EducationalSessionService educationalSessionService;

    //-------------------------------------------------Non-Dynamic, not batched-----------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_complex_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_complex_all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void complexNonDynamicCaseS1T5Q50PerBatch1Incorrect1Test() {
        /**
         * UserId = 2L;
         * Complex case: complex scheme of 5 themes, each theme contains 10 questions, 5 out of 10 of each type are requested;
         * Single question per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: all are correct, but one incorrect (last one)
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // here you show questions of the next batch
            log.debug("Next batch = {}", currentBatch);
            QuestionSessionOutDto question = currentBatch.getBatch().get(0);
            Long questionId = question.getQuestionId();
            long type = question.getType();
            Map<Long, Response> response = getResponseMap(type, questionsMap, questionId, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            BatchEvaluated batchEvaluated = currentBatch.getPreviousBatchResult().getBatchEvaluated();
            assertEquals(1, batchEvaluated.getResponsesEvaluated().size());
            assertTrue(batchEvaluated.getIncorrectResponseIds().isEmpty());
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question and collect the last response
        log.debug("Last batch = {}", currentBatch);
        QuestionSessionOutDto question = currentBatch.getBatch().get(0);
        Long questionId = question.getQuestionId();
        long type = question.getType();
        Map<Long, Response> response = getResponseMap(type, questionsMap, questionId, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(96.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(5.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // From session with right answers displayed - no gamification points can be granted!
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertEquals(5, resultOutDto.getThemeResults().size());
        assertEquals(25, resultOutDto.getQuestionResults().size());

        // Expected: new single entry in each: Result, ResultDetails, 5 entries in ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        Assert.assertNotNull(result);
        assertEquals(1L, result.getResultId().longValue());
        assertTrue(result.isPassed());
        assertFalse(result.isPoints());
        assertEquals(96.0, result.getPercent(), 0.1);
        assertEquals(5.0, result.getGrade(), 0.1);

        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        Assert.assertNotNull(resultDetails);
        assertEquals(1L, resultDetails.getDetailId().longValue());
        assertFalse(resultDetails.getJsonData().isEmpty());
        assertTrue(LocalDateTime.now().isAfter(resultDetails.getWhenRemove().minusHours(24)));

        final List<ResultTheme> resultThemes = (List<ResultTheme>) em.createQuery("select r from ResultTheme r join r.result rr where rr.resultId =:resultId").setParameter("resultId", 1L).getResultList();
        Assert.assertEquals(5, resultThemes.size());

        ResultTheme resultTheme1 = resultThemes.get(0);
        assertEquals(1L, resultTheme1.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme1.getQuantity());
        assertEquals(100.0, resultTheme1.getPercent(), 0.1);

        ResultTheme resultTheme2 = resultThemes.get(1);
        assertEquals(2L, resultTheme2.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme2.getQuantity());
        assertEquals(100.0, resultTheme2.getPercent(), 0.1);

        ResultTheme resultTheme3 = resultThemes.get(2);
        assertEquals(3L, resultTheme3.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme3.getQuantity());
        assertEquals(100.0, resultTheme3.getPercent(), 0.1);

        ResultTheme resultTheme4 = resultThemes.get(3);
        assertEquals(4L, resultTheme4.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme4.getQuantity());
        assertEquals(100.0, resultTheme4.getPercent(), 0.1);

        ResultTheme resultTheme5 = resultThemes.get(4);
        assertEquals(5L, resultTheme5.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme5.getQuantity());
        assertEquals(80.0, resultTheme5.getPercent(), 0.1);

        log.debug("Finished");
    }

   //-------------------------------------------------Non-Dynamic, batched 2-------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_complex_scheme_non_dynamic_batched_2.sql", "/scripts/session/case_complex_all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void complexNonDynamicCaseS1T5Q50PerBatch2Correct13Test() {
        /*
         * UserId = 2L;
         * Complex case: simple scheme of single theme of 10 SQ questions, all are requested;
         * 2 questions per batch, non-dynamic implementation (you do know when to launch finish with last batch);
         * Non-LMS environment;
         * Scenario: Single incorrect per each batch, except the last batch where all are correct!
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        int batchesLeft = currentBatch.getBatchesLeft();
        while (batchesLeft > 0) {
            // here you show questions of the next batch
            log.debug("Next batch = {}", currentBatch);
            Map<Long, Response> response = getResponseMap(questionsMap, currentBatch, true);
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            BatchEvaluated batchEvaluated = currentBatch.getPreviousBatchResult().getBatchEvaluated();
            assertEquals(2, batchEvaluated.getResponsesEvaluated().size());
            assertEquals(1, batchEvaluated.getIncorrectResponseIds().size());
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question and collect the last response
        log.debug("Last batch = {}", currentBatch);
        Map<Long, Response> response = getResponseMap(questionsMap, currentBatch, false);
        ResultOutDto resultOutDto = genericSessionService.finish(new BatchInDto(response), sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(52.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(3.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // From session with right answers displayed - no gamification points can be granted!
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertEquals(5, resultOutDto.getThemeResults().size());
        assertEquals(25, resultOutDto.getQuestionResults().size());

        // Expected: new single entry in each: Result, ResultDetails, 5 entries in ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        Assert.assertNotNull(result);
        assertEquals(1L, result.getResultId().longValue());
        assertTrue(result.isPassed());
        assertFalse(result.isPoints());
        assertEquals(52.0, result.getPercent(), 0.1);
        assertEquals(3.0, result.getGrade(), 0.1);

        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        Assert.assertNotNull(resultDetails);
        assertEquals(1L, resultDetails.getDetailId().longValue());
        assertFalse(resultDetails.getJsonData().isEmpty());
        assertTrue(LocalDateTime.now().isAfter(resultDetails.getWhenRemove().minusHours(24)));

        final List<ResultTheme> resultThemes = (List<ResultTheme>) em.createQuery("select r from ResultTheme r join r.result rr where rr.resultId =:resultId").setParameter("resultId", 1L).getResultList();
        Assert.assertEquals(5, resultThemes.size());

        ResultTheme resultTheme1 = resultThemes.get(0);
        assertEquals(1L, resultTheme1.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme1.getQuantity());
        assertTrue(resultTheme1.getPercent()>=40);

        ResultTheme resultTheme2 = resultThemes.get(1);
        assertEquals(2L, resultTheme2.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme2.getQuantity());
        assertTrue(resultTheme2.getPercent()>=40);

        ResultTheme resultTheme3 = resultThemes.get(2);
        assertEquals(3L, resultTheme3.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme3.getQuantity());
        assertTrue(resultTheme3.getPercent()>=40);

        ResultTheme resultTheme4 = resultThemes.get(3);
        assertEquals(4L, resultTheme4.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme4.getQuantity());
        assertTrue(resultTheme4.getPercent()>=40);

        ResultTheme resultTheme5 = resultThemes.get(4);
        assertEquals(5L, resultTheme5.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme5.getQuantity());
        assertTrue(resultTheme5.getPercent()>=40);

        log.debug("Finished");
    }

    private Map<Long, Response> getResponseMap(Long type, Map<Long, QuestionDomain> questionsMap, Long questionId, boolean isCorrect) {
        if (type.equals(1L)) return sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, questionId, isCorrect);
        if (type.equals(2L)) return sessionTestFBSQSupport.getCorrectResponseFBSQMap(questionsMap, questionId, isCorrect);
        if (type.equals(3L)) return sessionTestFBMQSupport.getCorrectResponseFBMQMap(questionsMap, questionId, isCorrect);
        if (type.equals(4L)) return sessionTestMQSupport.getCorrectResponseMQMap(questionsMap, questionId, isCorrect);
        if (type.equals(5L)) return sessionTestSQSupport.getCorrectResponseSQMap(questionsMap, questionId, isCorrect);
        throw new UnsupportedOperationException("Unrecognized type");
    }

    private Map<Long, Response> getResponseMap(Map<Long, QuestionDomain> questionsMap, BatchOutDto currentBatch, boolean containsIncorrect) {
        List<QuestionSessionOutDto> questions = currentBatch.getBatch();
        Random r = new Random();
        int randomIncorrect = r.ints(0, questions.size()).findFirst().getAsInt();
        Map<Long, Response> response = new HashMap<>();
        for (QuestionSessionOutDto question : questions) {
            Long questionId = question.getQuestionId();
            long type = question.getType();
            Map<Long, Response> resp;
            if(containsIncorrect && question.getQuestionId().equals(questions.get(randomIncorrect).getQuestionId())) {
                resp = getResponseMap(type, questionsMap, questionId, false);
            } else {
                resp = getResponseMap(type, questionsMap, questionId, true);
            }
            response.putAll(resp);
        }
        return response;
    }



    //-------------------------------------------------Dynamic, not batched---------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_complex_scheme_dynamic_not_batched.sql", "/scripts/session/case_complex_all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void complexDynamicCaseS1T5Q50PerBatch1Incorrect4Test() {
        /*
         * UserId = 2L;
         * Complex case: 5 themes with questions per theme = {1, 5, 0, 5, 10}, total = 21 question
         * Single question per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-pyramidal! Incorrect-s are not returned back! Never!
         * Non-LMS environment;
         * Scenario: first 4 questions are incorrect
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int incorrectCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            QuestionSessionOutDto question = currentBatch.getBatch().get(0);
            Long questionId = question.getQuestionId();
            long type = question.getType();
            Map<Long, Response> response;
            if (incorrectCounter <= 3) {
                response = getResponseMap(type, questionsMap, questionId, false);
            } else {
                response = getResponseMap(type, questionsMap, questionId, true);
            }
            incorrectCounter++;
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            if (currentBatch.getPreviousBatchResult() != null) {
                BatchEvaluated batchEvaluated = currentBatch.getPreviousBatchResult().getBatchEvaluated();
                assertEquals(1, batchEvaluated.getResponsesEvaluated().size());
                if (incorrectCounter <= 4) {
                    assertEquals(1, batchEvaluated.getIncorrectResponseIds().size());
                } else {
                    assertEquals(0, batchEvaluated.getIncorrectResponseIds().size());
                }
            }
            log.debug("Next batch = {}", currentBatch);
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(80.95, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(4.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // From session with right answers displayed - no gamification points can be granted!
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertEquals(4, resultOutDto.getThemeResults().size());
        assertEquals(21, resultOutDto.getQuestionResults().size());

        // Expected: new single entry in each: Result, ResultDetails, 5 entries in ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        Assert.assertNotNull(result);
        assertEquals(1L, result.getResultId().longValue());
        assertTrue(result.isPassed());
        assertFalse(result.isPoints());
        assertEquals(80.95, result.getPercent(), 0.1);
        assertEquals(4.0, result.getGrade(), 0.1);

        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        Assert.assertNotNull(resultDetails);
        assertEquals(1L, resultDetails.getDetailId().longValue());
        assertFalse(resultDetails.getJsonData().isEmpty());
        assertTrue(LocalDateTime.now().isAfter(resultDetails.getWhenRemove().minusHours(24)));

        final List<ResultTheme> resultThemes = (List<ResultTheme>) em.createQuery("select r from ResultTheme r join r.result rr where rr.resultId =:resultId").setParameter("resultId", 1L).getResultList();
        Assert.assertEquals(4, resultThemes.size());

        ResultTheme resultTheme1 = resultThemes.get(0);
        assertEquals(1L, resultTheme1.getTheme().getThemeId().longValue());
        assertEquals(1, resultTheme1.getQuantity());
        assertTrue(resultTheme1.getPercent()>=0);

        ResultTheme resultTheme2 = resultThemes.get(1);
        assertEquals(2L, resultTheme2.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme2.getQuantity());
        assertTrue(resultTheme2.getPercent()>=20);

        ResultTheme resultTheme4 = resultThemes.get(2);
        assertEquals(4L, resultTheme4.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme4.getQuantity());
        assertTrue(resultTheme4.getPercent()>=20);

        ResultTheme resultTheme5 = resultThemes.get(3);
        assertEquals(5L, resultTheme5.getTheme().getThemeId().longValue());
        assertEquals(10, resultTheme5.getQuantity());
        assertTrue(resultTheme5.getPercent()>=60);

        log.debug("Finished");
    }


    //-------------------------------------------------Dynamic, batched 4-----------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_complex_scheme_dynamic_batched_4.sql", "/scripts/session/case_complex_all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void complexDynamicCaseS1T5Q50PerBatch4Skip1Incorrect4Test() {
        /*
         * UserId = 2L;
         * Complex case: 5 themes {5, 5, 5, 5, 5}
         * 4 questions per batch, dynamic implementation (you do NOT know when to launch finish with last batch);
         * Non-pyramidal! Incorrect-s are not returned back! Never!
         * Non-LMS environment;
         * Scenario: Single incorrect in first 4 batches out of 7 in total, one skip performed
         */
        SessionData sessionData = genericSessionService.start(1L, "123456");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        BatchOutDto currentBatch = sessionData.getCurrentBatch().get();
        log.debug("First batch = {}", currentBatch);
        int incorrectCounter = 0;
        while (!currentBatch.isEmpty()) {// even if no batches left launch next to get empty batch out
            Map<Long, Response> response;
            if (incorrectCounter <= 3) {
                response = getResponseMap(questionsMap, currentBatch, true);
            } else {
                if (incorrectCounter==5) {
                    Long skippedId = currentBatch.getBatch().get(0).getQuestionId();
                    educationalSessionService.skip(skippedId, sessionData);
                }
                response = getResponseMap(questionsMap, currentBatch, false);
            }
            incorrectCounter++;
            currentBatch = genericSessionService.next(new BatchInDto(response), sessionData);
            if (currentBatch.getPreviousBatchResult() != null) {
                BatchEvaluated batchEvaluated = currentBatch.getPreviousBatchResult().getBatchEvaluated();
                assertTrue(batchEvaluated.getResponsesEvaluated().size()==3 || batchEvaluated.getResponsesEvaluated().size()==4);
                if (incorrectCounter <= 4) {
                    assertEquals(1, batchEvaluated.getIncorrectResponseIds().size());
                } else {
                    assertEquals(0, batchEvaluated.getIncorrectResponseIds().size());
                }
            }
            log.debug("Next batch = {}", currentBatch);
        }
        // Here you show the last question and collect the last response
        log.debug("About to launch finish request without evaluating last batch...");
        ResultOutDto resultOutDto = genericSessionService.finish(sessionData);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(84.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(4.0, resultOutDto.getGrade().doubleValue(), 0.01);
        // From session with right answers displayed - no gamification points can be granted!
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertEquals(5, resultOutDto.getThemeResults().size());
        assertEquals(25, resultOutDto.getQuestionResults().size());

        // Expected: new single entry in each: Result, ResultDetails, 5 entries in ResultTheme
        final Result result = (Result) em.createQuery("select r from Result r where r.resultId =:resultId").setParameter("resultId", 1L).getSingleResult();
        Assert.assertNotNull(result);
        assertEquals(1L, result.getResultId().longValue());
        assertTrue(result.isPassed());
        assertFalse(result.isPoints());
        assertEquals(84.0, result.getPercent(), 0.1);
        assertEquals(4.0, result.getGrade(), 0.1);

        final ResultDetails resultDetails = (ResultDetails) em.createQuery("select r from ResultDetails r where r.detailId =:detailId").setParameter("detailId", 1L).getSingleResult();
        Assert.assertNotNull(resultDetails);
        assertEquals(1L, resultDetails.getDetailId().longValue());
        assertFalse(resultDetails.getJsonData().isEmpty());
        assertTrue(LocalDateTime.now().isAfter(resultDetails.getWhenRemove().minusHours(24)));

        final List<ResultTheme> resultThemes = (List<ResultTheme>) em.createQuery("select r from ResultTheme r join r.result rr where rr.resultId =:resultId").setParameter("resultId", 1L).getResultList();
        Assert.assertEquals(5, resultThemes.size());

        ResultTheme resultTheme1 = resultThemes.get(0);
        assertEquals(1L, resultTheme1.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme1.getQuantity());
        assertTrue(resultTheme1.getPercent()>=20);

        ResultTheme resultTheme2 = resultThemes.get(1);
        assertEquals(2L, resultTheme2.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme2.getQuantity());
        assertTrue(resultTheme2.getPercent()>=20);

        ResultTheme resultTheme3 = resultThemes.get(2);
        assertEquals(3L, resultTheme3.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme3.getQuantity());
        assertTrue(resultTheme3.getPercent()>=20);

        ResultTheme resultTheme4 = resultThemes.get(3);
        assertEquals(4L, resultTheme4.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme4.getQuantity());
        assertTrue(resultTheme4.getPercent()>=20);

        ResultTheme resultTheme5 = resultThemes.get(4);
        assertEquals(5L, resultTheme5.getTheme().getThemeId().longValue());
        assertEquals(5, resultTheme5.getQuantity());
        assertTrue(resultTheme5.getPercent()>=20);

        log.debug("Finished");
    }
}
