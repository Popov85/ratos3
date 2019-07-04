package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.*;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.BatchEvaluatorService;
import ua.edu.ratos.QuestionGenerator;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;

/**
 * Plan:
 * 1. Manually create a SessionData object with a given sequence of 10 questions (2 of each type)
 * 2. Manually Create BatchOut of 5 first questions and set to SessionData
 * 3. Manually Create BatchIn with responses to these 5 questions
 *    (all 5 correct/ all 5 incorrect/ 3 out of 5 correct/ 5 partly correct/ empty batch in)
 * 4. Call doEvaluate and obtain the map of evaluated responses
 * 5. Do asserts to make sure the evaluation was successful
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BatchEvaluatorServiceTestIT extends QuestionGenerator {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeDomainTransformer schemeDomainTransformer;

    @Autowired
    private BatchEvaluatorService batchEvaluatorService;

    private SessionData sessionData;

    @Before
    public void init() {
        Scheme scheme = schemeRepository.findForSessionById(1L);
        SchemeDomain schemeDomain = schemeDomainTransformer.toDomain(scheme);

        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3", true),
                createMQ(4L, "Matcher QuestionDomain #4", true),
                createSQ(5L, "Sequence question #5"));

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(schemeDomain)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(5)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();

        sessionData.setCurrentBatch(new BatchOutDto.Builder()
                .withQuestions(sequence.stream().map(q->q.toDto()).collect(Collectors.toList()))
                .withBatchesLeft(0)
                .withQuestionsLeft(0)
                .withTimeLeft(-1)
                .withBatchTimeLimit(-1)
                .inMode(schemeDomain.getModeDomain())
                .build());

        String str = "2019-01-09 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        sessionData.setCurrentBatchIssued(dateTime);
        sessionData.setCurrentIndex(5);

        this.sessionData = sessionData;
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_evaluator_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doEvaluateAll5CorrectTest() {
        // Prepare all correct responses
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

        ResponseMQ.Triple t1 = new ResponseMQ.Triple(1L, 21L, 22L);
        ResponseMQ.Triple t2 = new ResponseMQ.Triple(2L, 23L, 24L);
        ResponseMQ.Triple t3 = new ResponseMQ.Triple(3L, 25L, 26L);
        ResponseMQ.Triple t4 = new ResponseMQ.Triple(4L, 27L, 28L);
        Response responseMQ = new ResponseMQ(4L, new HashSet<>(Arrays.asList(t1, t2, t3, t4)));
        responses.put(4L, responseMQ);

        Response responseSQ = new ResponseSQ(5L, Arrays.asList(1L, 2L, 3L, 4L, 5L));
        responses.put(5L, responseSQ);

        BatchInDto batchInDto = new BatchInDto(responses);

        // Actual test begin

        Map<Long, ResponseEvaluated> map = batchEvaluatorService.doEvaluate(batchInDto, sessionData);

        assertThat(map, hasKey(1L));
        assertThat(map, hasKey(2L));
        assertThat(map, hasKey(3L));
        assertThat(map, hasKey(4L));
        assertThat(map, hasKey(5L));

        assertEquals(100.0, map.get(1L).getScore(), 0.1);
        assertEquals(100.0, map.get(2L).getScore(), 0.1);
        assertEquals(100.0, map.get(3L).getScore(), 0.1);
        assertEquals(100.0, map.get(4L).getScore(), 0.1);
        assertEquals(100.0, map.get(5L).getScore(), 0.1);
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_evaluator_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doEvaluateAll5InCorrectTest() {
        // Prepare all incorrect responses
        Map<Long, Response> responses = new HashMap<>();

        Response responseMCQ = new ResponseMCQ(1L, new HashSet<>(Arrays.asList(4L)));
        responses.put(1L, responseMCQ);

        Response responseFBSQ = new ResponseFBSQ(2L, "PhraseDomain #4 Incorrect");
        responses.put(2L, responseFBSQ);

        ResponseFBMQ.Pair p1 = new ResponseFBMQ.Pair(1L, "PhraseDomain #4 for FBMQ answer #1 Incorrect");
        ResponseFBMQ.Pair p2 = new ResponseFBMQ.Pair(2L, "PhraseDomain #4 for FBMQ answer #2 Incorrect");
        ResponseFBMQ.Pair p3 = new ResponseFBMQ.Pair(3L, "PhraseDomain #4 for FBMQ answer #3 Incorrect");
        ResponseFBMQ.Pair p4 = new ResponseFBMQ.Pair(4L, "PhraseDomain #4 for FBMQ answer #4 Incorrect");

        Response responseFBMQ = new ResponseFBMQ(3L, new HashSet(Arrays.asList(p1, p2, p3, p4)));
        responses.put(3L, responseFBMQ);

        ResponseMQ.Triple t1 = new ResponseMQ.Triple(1L, 22L, 21L);
        ResponseMQ.Triple t2 = new ResponseMQ.Triple(2L, 24L, 23L);
        ResponseMQ.Triple t3 = new ResponseMQ.Triple(3L, 26L, 25L);
        ResponseMQ.Triple t4 = new ResponseMQ.Triple(4L, 28L, 27L);
        Response responseMQ = new ResponseMQ(4L, new HashSet<>(Arrays.asList(t1, t2, t3, t4)));
        responses.put(4L, responseMQ);

        Response responseSQ = new ResponseSQ(5L, Arrays.asList(5L, 4L, 3L, 2L, 1L));
        responses.put(5L, responseSQ);

        BatchInDto batchInDto = new BatchInDto(responses);

        // Actual test begin
        Map<Long, ResponseEvaluated> map = batchEvaluatorService.doEvaluate(batchInDto, sessionData);

        assertThat(map, hasKey(1L));
        assertThat(map, hasKey(2L));
        assertThat(map, hasKey(3L));
        assertThat(map, hasKey(4L));
        assertThat(map, hasKey(5L));

        assertEquals(0.0, map.get(1L).getScore(), 0.1);
        assertEquals(0.0, map.get(2L).getScore(), 0.1);
        assertEquals(0.0, map.get(3L).getScore(), 0.1);
        assertEquals(0.0, map.get(4L).getScore(), 0.1);
        assertEquals(0.0, map.get(5L).getScore(), 0.1);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_evaluator_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doEvaluateAll3OutOf5CorrectTest() {
        // Prepare 3 out of 5 correct responses
        Map<Long, Response> responses = new HashMap<>();

        Response responseMCQ = new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L)));
        responses.put(1L, responseMCQ);

        // Incorrect
        Response responseFBSQ = new ResponseFBSQ(2L, "PhraseDomain #4 Incorrect");
        responses.put(2L, responseFBSQ);

        ResponseFBMQ.Pair p1 = new ResponseFBMQ.Pair(1L, "PhraseDomain #4 for FBMQ answer #1");
        ResponseFBMQ.Pair p2 = new ResponseFBMQ.Pair(2L, "PhraseDomain #4 for FBMQ answer #2");
        ResponseFBMQ.Pair p3 = new ResponseFBMQ.Pair(3L, "PhraseDomain #4 for FBMQ answer #3");
        ResponseFBMQ.Pair p4 = new ResponseFBMQ.Pair(4L, "PhraseDomain #4 for FBMQ answer #4");

        Response responseFBMQ = new ResponseFBMQ(3L, new HashSet(Arrays.asList(p1, p2, p3, p4)));
        responses.put(3L, responseFBMQ);

        ResponseMQ.Triple t1 = new ResponseMQ.Triple(1L, 21L, 22L);
        ResponseMQ.Triple t2 = new ResponseMQ.Triple(2L, 23L, 24L);
        ResponseMQ.Triple t3 = new ResponseMQ.Triple(3L, 25L, 26L);
        ResponseMQ.Triple t4 = new ResponseMQ.Triple(4L, 27L, 28L);
        Response responseMQ = new ResponseMQ(4L, new HashSet<>(Arrays.asList(t1, t2, t3, t4)));
        responses.put(4L, responseMQ);

        // Incorrect
        Response responseSQ = new ResponseSQ(5L, Arrays.asList(1L, 2L, 5L, 4L, 3L));
        responses.put(5L, responseSQ);

        BatchInDto batchInDto = new BatchInDto(responses);

        // Actual test begin
        Map<Long, ResponseEvaluated> map = batchEvaluatorService.doEvaluate(batchInDto, sessionData);

        assertThat(map, hasKey(1L));
        assertThat(map, hasKey(2L));
        assertThat(map, hasKey(3L));
        assertThat(map, hasKey(4L));
        assertThat(map, hasKey(5L));

        assertEquals(100.0, map.get(1L).getScore(), 0.1);
        assertEquals(0.0, map.get(2L).getScore(), 0.1);
        assertEquals(100.0, map.get(3L).getScore(), 0.1);
        assertEquals(100.0, map.get(4L).getScore(), 0.1);
        assertEquals(0.0, map.get(5L).getScore(), 0.1);
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_evaluator_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doEvaluatePartlyWherePossibleCorrectTest() {
        // Prepare partly where possible correct responses
        Map<Long, Response> responses = new HashMap<>();

        Response responseMCQ = new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L)));
        responses.put(1L, responseMCQ);

        Response responseFBSQ = new ResponseFBSQ(2L, "PhraseDomain #4");
        responses.put(2L, responseFBSQ);

        // Partly
        ResponseFBMQ.Pair p1 = new ResponseFBMQ.Pair(1L, "PhraseDomain #4 for FBMQ answer #1");
        ResponseFBMQ.Pair p2 = new ResponseFBMQ.Pair(2L, "PhraseDomain #4 for FBMQ answer #2 Incorrect");
        ResponseFBMQ.Pair p3 = new ResponseFBMQ.Pair(3L, "PhraseDomain #4 for FBMQ answer #3");
        ResponseFBMQ.Pair p4 = new ResponseFBMQ.Pair(4L, "PhraseDomain #4 for FBMQ answer #4 Incorrect");

        Response responseFBMQ = new ResponseFBMQ(3L, new HashSet(Arrays.asList(p1, p2, p3, p4)));
        responses.put(3L, responseFBMQ);

        // Partly
        ResponseMQ.Triple t1 = new ResponseMQ.Triple(1L, 21L, 22L);
        ResponseMQ.Triple t2 = new ResponseMQ.Triple(2L, 24L, 23L);
        ResponseMQ.Triple t3 = new ResponseMQ.Triple(3L, 25L, 26L);
        ResponseMQ.Triple t4 = new ResponseMQ.Triple(4L, 28L, 27L);
        Response responseMQ = new ResponseMQ(4L, new HashSet<>(Arrays.asList(t1, t2, t3, t4)));
        responses.put(4L, responseMQ);

        Response responseSQ = new ResponseSQ(5L, Arrays.asList(1L, 2L, 3L, 4L, 5L));
        responses.put(5L, responseSQ);

        BatchInDto batchInDto = new BatchInDto(responses);

        // Actual test begin
        Map<Long, ResponseEvaluated> map = batchEvaluatorService.doEvaluate(batchInDto, sessionData);

        assertThat(map, hasKey(1L));
        assertThat(map, hasKey(2L));
        assertThat(map, hasKey(3L));
        assertThat(map, hasKey(4L));
        assertThat(map, hasKey(5L));

        assertEquals(100.0, map.get(1L).getScore(), 0.1);
        assertEquals(100.0, map.get(2L).getScore(), 0.1);
        assertEquals(50.0, map.get(3L).getScore(), 0.1);
        assertEquals(50.0, map.get(4L).getScore(), 0.1);
        assertEquals(100.0, map.get(5L).getScore(), 0.1);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_evaluator_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doEvaluateEmptyTest() {
        // Prepare empty batch of responses
        Map<Long, Response> responses = new HashMap<>();

        BatchInDto batchInDto = new BatchInDto(responses);

        // Actual test begin
        Map<Long, ResponseEvaluated> map = batchEvaluatorService.doEvaluate(batchInDto, sessionData);

        assertThat(map, hasKey(1L));
        assertThat(map, hasKey(2L));
        assertThat(map, hasKey(3L));
        assertThat(map, hasKey(4L));
        assertThat(map, hasKey(5L));

        assertEquals(0.0, map.get(1L).getScore(), 0.1);
        assertEquals(0.0, map.get(2L).getScore(), 0.1);
        assertEquals(0.0, map.get(3L).getScore(), 0.1);
        assertEquals(0.0, map.get(4L).getScore(), 0.1);
        assertEquals(0.0, map.get(5L).getScore(), 0.1);
    }
}
