package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.QuestionGenerator;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.*;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.EvaluatingService;
import ua.edu.ratos.service.session.ProgressDataService;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ProgressDataServiceTestIT extends QuestionGenerator {

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private EvaluatingService evaluatingService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeDomainTransformer schemeDomainTransformer;

    private SessionData sessionData;

    @Before
    public void init() {
        // The test's sql script ExecutionPhase.BEFORE_TEST_METHOD is invoked before @Before
        Scheme scheme = schemeRepository.findForSessionById(1L);
        SchemeDomain schemeDomain = schemeDomainTransformer.toDomain(scheme);

        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3", true),
                createMQ(4L, "Matcher QuestionDomain #4", true),
                createSQ(5L, "Sequence question #5"));

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8TYD7EDA2381E69776A40B3B11C")
                .forUser(1L)
                .takingScheme(schemeDomain)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(3)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();

        List<QuestionDomain> questions = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3", true));

        sessionData.setCurrentBatch(new BatchOutDto.Builder()
                .withQuestions(questions.stream().map(q->q.toDto()).collect(Collectors.toList()))
                .withBatchesLeft(0)
                .withQuestionsLeft(0)
                .withTimeLeft(-1)
                .withBatchTimeLimit(-1)
                .inMode(schemeDomain.getModeDomain())
                .build());

        // set time issued 10 sec before now
        sessionData.setCurrentBatchIssued(LocalDateTime.now().minusSeconds(10));
        sessionData.setCurrentIndex(3);

        this.sessionData = sessionData;
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/progress_data_service.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateAndAllPublicMethodsTest() {
        // Prepare first 3 correct responses
        Map<Long, Response> responses = new HashMap<>();

        Response responseMCQ = new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L)));
        responses.put(1L, responseMCQ);

        Response responseFBSQ = new ResponseFBSQ(2L, "PhraseDomain #4");
        responses.put(2L, responseFBSQ);

        ResponseFBMQ.Pair p1 = new ResponseFBMQ.Pair(1L, "PhraseDomain #4 for FBMQ answer #1");
        ResponseFBMQ.Pair p2 = new ResponseFBMQ.Pair(2L, "PhraseDomain #4 for FBMQ answer #2");
        ResponseFBMQ.Pair p3 = new ResponseFBMQ.Pair(3L, "PhraseDomain #4 for FBMQ answer #3");
        ResponseFBMQ.Pair p4 = new ResponseFBMQ.Pair(4L, "PhraseDomain #4 for FBMQ answer #4");

        Response responseFBMQ = new ResponseFBMQ(3L, new HashSet(Arrays.asList(p1, p2, p3, p4)));
        responses.put(3L, responseFBMQ);

        BatchInDto batchInDto = new BatchInDto(responses);

        BatchEvaluated batchEvaluated = evaluatingService.getBatchEvaluated(batchInDto, sessionData);

        progressDataService.update(sessionData, batchEvaluated);

        // Actual test begins
        ProgressData progressData = sessionData.getProgressData();
        assertEquals(1, progressData.getBatchesEvaluated().size());
        // 10 actually
        assertTrue(progressData.getTimeSpent() >0 && progressData.getTimeSpent()<15);
        assertEquals(3, progressData.getScore(), 0.1);

        // Proceed with another test case

        List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);

        assertEquals(3, responsesEvaluated.size());

        assertTrue((responsesEvaluated.get(0).getResponse() instanceof ResponseMCQ));
        assertEquals(100.0, responsesEvaluated.get(0).getScore(), 0.1);
        assertEquals(1L, responsesEvaluated.get(0).getQuestionId().longValue());

        assertTrue((responsesEvaluated.get(1).getResponse() instanceof ResponseFBSQ));
        assertEquals(100.0, responsesEvaluated.get(1).getScore(), 0.1);
        assertEquals(2L, responsesEvaluated.get(1).getQuestionId().longValue());

        assertTrue((responsesEvaluated.get(2).getResponse() instanceof ResponseFBMQ));
        assertEquals(100.0, responsesEvaluated.get(2).getScore(), 0.1);
        assertEquals(3L, responsesEvaluated.get(2).getQuestionId().longValue());

        // Proceed with another test

        double currentScore = progressDataService.getCurrentScore(sessionData);
        assertEquals(60.0, currentScore, 0.1);

        // Proceed with the last test

        double effectiveScore = progressDataService.getEffectiveScore(sessionData);
        assertEquals(100.0, effectiveScore, 0.1);

    }
}
