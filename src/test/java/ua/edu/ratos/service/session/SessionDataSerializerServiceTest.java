package ua.edu.ratos.service.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.answer.*;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.domain.question.*;
import ua.edu.ratos.service.domain.response.*;
import ua.edu.ratos.service.dto.session.question.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SessionDataSerializerServiceTest {

    public static final String JSON_INIT = "classpath:json/session_data_init.json";

    public static final String JSON_INTERMEDIATE = "classpath:json/session_data_intermediate.json";

    public static final String JSON_TERMINAL = "classpath:json/session_data_terminal.json";

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private SessionDataSerializerService serializer;

    private SchemeDomain schemeDomain;

    @Before
    public void init() {
        schemeDomain = new SchemeDomain();
        schemeDomain.setSchemeId(1L);
        schemeDomain.setName("SchemeDomain#1");
        schemeDomain.setModeDomain(new ModeDomain().setModeId(1L).setName("ModeDomain#1"));
        schemeDomain.setSettingsDomain(new SettingsDomain().setSetId(1L).setName("SettingsDomain#1")
                .setDaysKeepResultDetails((short)1)
                .setLevel2Coefficient(1)
                .setLevel3Coefficient(1)
                .setQuestionsPerSheet((short)5)
                .setSecondsPerQuestion(60));
        schemeDomain.setStrategyDomain(new StrategyDomain().setStrId(1L).setName("StrategyDomain#1"));
        schemeDomain.setGradingDomain(new GradingDomain().setGradingId(1L).setName("GradingDomain #1"));

        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME);
        objectMapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, localDateTimeDeserializer));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void serializeInitialStateWith5DifferentQuestionsTest() throws Exception{
        // init
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3"),
                createMQ(4L, "Matcher QuestionDomain #4"),
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

        String str = "2018-03-22 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        sessionData.setCurrentBatchIssued(dateTime);

        sessionData.setCurrentIndex(5);

        // actual test begins

        File json = ResourceUtils.getFile(JSON_INIT);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());

        String actual = serializer.serialize(sessionData);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        log.debug("result :: {}", actual);
    }

    @Test
    // Typical use case (1 question ber batch, limited in time, preserved in the middle of the test)
    public void serializeIntermediateStateWith1QuestionPerBatchTest() throws Exception {
        // init
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3"),
                createMQ(4L, "Matcher QuestionDomain #4"),
                createSQ(5L, "Sequence question #5"));

        schemeDomain.getSettingsDomain().setQuestionsPerSheet((short) 1);

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(schemeDomain)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(1)
                .withSessionTimeout(LocalDateTime.parse("2018-11-26T09:33:45"))
                .withPerQuestionTimeLimit(-1)
                .build();

        // last but one question in the current batch
        sessionData.setCurrentBatch(new BatchOutDto.Builder()
                .withQuestions(Arrays.asList(sequence.get(3).toDto()))
                .withBatchesLeft(1)
                .withQuestionsLeft(1)
                .withTimeLeft(250)
                .withBatchTimeLimit(-1)
                .inMode(schemeDomain.getModeDomain())
                .build());

        sessionData.setCurrentBatchIssued(LocalDateTime.parse("2018-11-26T09:25:20"));
        sessionData.setCurrentBatchTimeOut(LocalDateTime.MAX);

        sessionData.setCurrentIndex(4);

        ProgressData progressData = new ProgressData();
        progressData.setTimeSpent(330);
        progressData.setScore(75.63);

        ResponseEvaluated r0 = new ResponseEvaluated(1L,
                new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L))), 100);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFBSQ(2L, "wrong phraseDomain"), 0);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFBMQ(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFBMQ.Pair(1L, "(correct) Target phraseDomain for FBMQ answer #1"),
                                new ResponseFBMQ.Pair(2L, "(correct) Target phraseDomain for FBMQ answer #2"),
                                new ResponseFBMQ.Pair(3L, "(correct) Target phraseDomain for FBMQ answer #3"),
                                new ResponseFBMQ.Pair(4L, "(correct) Target phraseDomain for FBMQ answer #4")
                        ))), 100);
        BatchEvaluated b2 = new BatchEvaluated(Collections.singletonMap(3L, r2), Arrays.asList(), 25);

        progressData.setBatchesEvaluated(Arrays.asList(b0, b1, b2));

        sessionData.setProgressData(progressData);

        Map<Long, MetaData> metaDataMap = new HashMap<>();
        MetaData metaL1 = new MetaData();
        metaL1.setStarred((byte)4).setIncorrect((short)0).setSkipped((short)0).setHelp((short)0).setComplained(false);
        metaDataMap.put(1L, metaL1);

        MetaData metaL2 = new MetaData();
        metaL2.setStarred(null).setIncorrect((short)1).setSkipped((short)0).setHelp((short)1).setComplained(false);
        metaDataMap.put(2L, metaL2);

        MetaData metaL3 = new MetaData();
        metaL3.setStarred((byte)4).setIncorrect((short)0).setSkipped((short)1).setHelp((short)1).setComplained(true);
        metaDataMap.put(3L, metaL3);

        sessionData.setMetaData(metaDataMap);

        // actual test begins

        File json = ResourceUtils.getFile(JSON_INTERMEDIATE);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());

        String actual = serializer.serialize(sessionData);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        //log.debug("result :: {}", actual);
    }


    @Test
    public void serializeTerminalStateWith1QuestionPerBatchTest() throws Exception{
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3"),
                createMQ(4L, "Matcher QuestionDomain #4"),
                createSQ(5L, "Sequence question #5"));

        schemeDomain.getSettingsDomain().setQuestionsPerSheet((short)1);

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(schemeDomain)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(1)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();

        sessionData.setCurrentBatch(null);
        sessionData.setCurrentBatchIssued(null);
        sessionData.setCurrentBatchTimeOut(null);

        sessionData.setCurrentIndex(5);

        ProgressData progressData = new ProgressData();
        progressData.setTimeSpent(250);
        progressData.setScore(88.5);

        ResponseEvaluated r0 = new ResponseEvaluated(1L,
                new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L))), 100);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFBSQ(2L, "wrong phraseDomain"), 0);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFBMQ(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFBMQ.Pair(1L, "(correct) Target phraseDomain for FBMQ answer #1"),
                                new ResponseFBMQ.Pair(2L, "(correct) Target phraseDomain for FBMQ answer #2"),
                                new ResponseFBMQ.Pair(3L, "(correct) Target phraseDomain for FBMQ answer #3"),
                                new ResponseFBMQ.Pair(4L, "(correct) Target phraseDomain for FBMQ answer #4")
                        ))), 100);
        BatchEvaluated b2 = new BatchEvaluated(Collections.singletonMap(3L, r2), Arrays.asList(), 25);

        ResponseEvaluated r3 = new ResponseEvaluated(4L,
                new ResponseMQ(4L, new HashSet<>(Arrays.asList(
                        new ResponseMQ.Triple(1L, 21L, 22L),
                        new ResponseMQ.Triple(2L, 23L, 24L),
                        new ResponseMQ.Triple(3L, 25L, 26L),
                        new ResponseMQ.Triple(4L, 27L, 28L)
                ))), 100);
        BatchEvaluated b3 = new BatchEvaluated(Collections.singletonMap(4L, r3), Arrays.asList(), 30);

        ResponseEvaluated r4 = new ResponseEvaluated(5L,
                new ResponseSQ(5L, Arrays.asList(29L, 30L, 31L, 32L, 33L)), 100);
        BatchEvaluated b4 = new BatchEvaluated(Collections.singletonMap(5L, r4), Arrays.asList(), 15);

        progressData.setBatchesEvaluated(Arrays.asList(b0, b1, b2, b3, b4));

        sessionData.setProgressData(progressData);

        Map<Long, MetaData> metaDataMap = new HashMap<>();
        MetaData metaL1 = new MetaData();
        metaL1.setStarred((byte)5).setIncorrect((short)0).setSkipped((short)0).setHelp((short)0).setComplained(false);
        metaDataMap.put(1L, metaL1);

        MetaData metaL2 = new MetaData();
        metaL2.setStarred(null).setIncorrect((short)1).setSkipped((short)0).setHelp((short)1).setComplained(false);
        metaDataMap.put(2L, metaL2);

        MetaData metaL3 = new MetaData();
        metaL3.setStarred((byte)4).setIncorrect((short)0).setSkipped((short)0).setHelp((short)0).setComplained(true);
        metaDataMap.put(3L, metaL3);

        MetaData metaL4 = new MetaData();
        metaL4.setStarred(null).setIncorrect((short)0).setSkipped((short)0).setHelp((short)1).setComplained(false);
        metaDataMap.put(4L, metaL4);

        MetaData metaL5 = new MetaData();
        metaL5.setStarred(null).setIncorrect((short)0).setSkipped((short)0).setHelp((short)1).setComplained(false);
        metaDataMap.put(5L, metaL5);

        sessionData.setMetaData(metaDataMap);

        // actual test begins

        File json = ResourceUtils.getFile(JSON_TERMINAL);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());

        String actual = serializer.serialize(sessionData);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        //log.debug("result :: {}", actual);
    }

    //-----------deserializer------------------

    @Test
    public void deserializeInitStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_INIT);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());
        SessionData sessionData = serializer.deserialize(expected);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // gradingDomain
        Assert.assertNotNull(sessionData.getSchemeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getModeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getSettingsDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getGradingDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getStrategyDomain());
        // questions
        Assert.assertEquals(5, sessionData.getQuestionDomains().size());
        Assert.assertTrue(sessionData.getQuestionDomains().get(0) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(1) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(2) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(3) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(4) instanceof QuestionSQDomain);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQDomain);
        // sessionTimeOut
        Assert.assertEquals(LocalDateTime.MAX, sessionData.getSessionTimeout());
        // perQuestionTimeLimit
        Assert.assertEquals(-1L, sessionData.getPerQuestionTimeLimit());
        // questionsPerBatch
        Assert.assertEquals(5, sessionData.getQuestionsPerBatch());

        // setters
        Assert.assertEquals(5, sessionData.getCurrentIndex());

        // currentBatch
        Assert.assertNotNull(sessionData.getCurrentBatch());
        Assert.assertNotNull(sessionData.getCurrentBatch().getBatch());
        Assert.assertNotNull(sessionData.getCurrentBatch().getBatchMap());
        Assert.assertNotNull(sessionData.getCurrentBatch().getModeDomain());
        Assert.assertNull(sessionData.getCurrentBatch().getPreviousBatchResult());

        // currentBatch's questions
        Assert.assertEquals(5, sessionData.getCurrentBatch().getBatch().size());
        Assert.assertTrue(sessionData.getCurrentBatch().getBatch().get(0) instanceof QuestionMCQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatch().get(1) instanceof QuestionFBSQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatch().get(2) instanceof QuestionFBMQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatch().get(3) instanceof QuestionMQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatch().get(4) instanceof QuestionSQOutDto);
        Assert.assertEquals(5, sessionData.getCurrentBatch().getBatchMap().size());
        Assert.assertTrue(sessionData.getCurrentBatch().getBatchMap().get(1L) instanceof QuestionMCQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatchMap().get(2L) instanceof QuestionFBSQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatchMap().get(3L) instanceof QuestionFBMQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatchMap().get(4L) instanceof QuestionMQOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().getBatchMap().get(5L) instanceof QuestionSQOutDto);
        // currentBatchTimeOut
        Assert.assertEquals(LocalDateTime.MAX, sessionData.getCurrentBatchTimeOut());
        // currentBatchIssued
        Assert.assertEquals(LocalDateTime.parse("2018-03-22T11:30:40"), sessionData.getCurrentBatchIssued());

        // progressData
        Assert.assertNotNull(sessionData.getProgressData());
        Assert.assertEquals(0.0, sessionData.getProgressData().getScore(), 0.1);
        Assert.assertEquals(0.0, sessionData.getProgressData().getTimeSpent(), 0.1);
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().isEmpty());

        // metaData
        Assert.assertTrue(sessionData.getMetaData().isEmpty());
        //log.debug("sessionData :: {}", sessionData);
    }


    @Test
    public void deserializeIntermediateStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_INTERMEDIATE);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());
        SessionData sessionData = serializer.deserialize(expected);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // gradingDomain
        Assert.assertNotNull(sessionData.getSchemeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getModeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getSettingsDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getGradingDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getStrategyDomain());
        // questions
        Assert.assertEquals(5, sessionData.getQuestionDomains().size());
        Assert.assertTrue(sessionData.getQuestionDomains().get(0) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(1) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(2) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(3) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(4) instanceof QuestionSQDomain);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQDomain);
        // sessionTimeOut
        Assert.assertEquals(LocalDateTime.parse("2018-11-26T09:33:45"), sessionData.getSessionTimeout());
        // perQuestionTimeLimit
        Assert.assertEquals(-1L, sessionData.getPerQuestionTimeLimit());
        // questionsPerBatch
        Assert.assertEquals(1, sessionData.getQuestionsPerBatch());

        // setters
        Assert.assertEquals(4, sessionData.getCurrentIndex());

        // currentBatch
        Assert.assertNotNull(sessionData.getCurrentBatch());
        // currentBatchTimeOut default value
        Assert.assertEquals(1, sessionData.getCurrentBatch().getBatch().size());
        Assert.assertEquals(1, sessionData.getCurrentBatch().getBatchMap().size());
        Assert.assertEquals(1, sessionData.getCurrentBatch().getBatchesLeft());
        Assert.assertEquals(1, sessionData.getCurrentBatch().getQuestionsLeft());
        Assert.assertEquals(-1, sessionData.getCurrentBatch().getBatchTimeLimit());
        Assert.assertEquals(250, sessionData.getCurrentBatch().getTimeLeft());
        Assert.assertNotNull(sessionData.getCurrentBatch().getModeDomain());

        // currentBatch timings
        Assert.assertNotNull(sessionData.getCurrentBatchIssued());
        Assert.assertEquals(LocalDateTime.parse("2018-11-26T09:25:20"), sessionData.getCurrentBatchIssued());
        Assert.assertNotNull(sessionData.getCurrentBatchTimeOut());
        Assert.assertEquals(LocalDateTime.MAX, sessionData.getCurrentBatchTimeOut());

        // progressData
        Assert.assertNotNull(sessionData.getProgressData());
        Assert.assertEquals(75.63, sessionData.getProgressData().getScore(), 0.01);
        Assert.assertEquals(330.0, sessionData.getProgressData().getTimeSpent(), 0.1);
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().isEmpty());

        // batches
        Assert.assertEquals(3, sessionData.getProgressData().getBatchesEvaluated().size());
        // 1st batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(30L, sessionData.getProgressData().getBatchesEvaluated().get(0).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().get(1L).getResponse() instanceof ResponseMCQ);
        // 2d batch
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().get(1).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(50L, sessionData.getProgressData().getBatchesEvaluated().get(1).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().get(2L).getResponse() instanceof ResponseFBSQ);
        // 3d batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(25L, sessionData.getProgressData().getBatchesEvaluated().get(2).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().get(3L).getResponse() instanceof ResponseFBMQ);
        // 4th batch not present
        // 5th batch not present

        // metaData
        Assert.assertFalse(sessionData.getMetaData().isEmpty());
        Assert.assertEquals(3, sessionData.getMetaData().size());
        //log.debug("sessionData :: {}", sessionData);
    }

    @Test
    public void deserializeTerminalStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_TERMINAL);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());
        SessionData sessionData = serializer.deserialize(expected);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // gradingDomain
        Assert.assertNotNull(sessionData.getSchemeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getModeDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getSettingsDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getGradingDomain());
        Assert.assertNotNull(sessionData.getSchemeDomain().getStrategyDomain());
        // questions
        Assert.assertEquals(5, sessionData.getQuestionDomains().size());
        Assert.assertTrue(sessionData.getQuestionDomains().get(0) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(1) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(2) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(3) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionDomains().get(4) instanceof QuestionSQDomain);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQDomain);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQDomain);
        // sessionTimeOut
        Assert.assertEquals(LocalDateTime.MAX, sessionData.getSessionTimeout());
        // perQuestionTimeLimit
        Assert.assertEquals(-1L, sessionData.getPerQuestionTimeLimit());
        // questionsPerBatch
        Assert.assertEquals(1, sessionData.getQuestionsPerBatch());

        // setters
        Assert.assertEquals(5, sessionData.getCurrentIndex());
        // currentBatch
        Assert.assertNull(sessionData.getCurrentBatch());
        // currentBatchTimeOut default value
        Assert.assertNotNull(sessionData.getCurrentBatchTimeOut());
        // currentBatchIssued default value
        Assert.assertNotNull(sessionData.getCurrentBatchIssued());

        // progressData
        Assert.assertNotNull(sessionData.getProgressData());
        Assert.assertEquals(88.5, sessionData.getProgressData().getScore(), 0.1);
        Assert.assertEquals(250.0, sessionData.getProgressData().getTimeSpent(), 0.1);
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().isEmpty());
        // 1st batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(30L, sessionData.getProgressData().getBatchesEvaluated().get(0).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().get(1L).getResponse() instanceof ResponseMCQ);
        // 2d batch
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().get(1).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(50L, sessionData.getProgressData().getBatchesEvaluated().get(1).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().get(2L).getResponse() instanceof ResponseFBSQ);
        // 3d batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(25L, sessionData.getProgressData().getBatchesEvaluated().get(2).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().get(3L).getResponse() instanceof ResponseFBMQ);
        // 4th batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(3).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(30L, sessionData.getProgressData().getBatchesEvaluated().get(3).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(3).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(3).getResponsesEvaluated().get(4L).getResponse() instanceof ResponseMQ);
        // 5th batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(4).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(15L, sessionData.getProgressData().getBatchesEvaluated().get(4).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(4).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(4).getResponsesEvaluated().get(5L).getResponse() instanceof ResponseSQ);


        // metaData
        Assert.assertFalse(sessionData.getMetaData().isEmpty());
        Assert.assertEquals(5, sessionData.getMetaData().size());
        //log.debug("sessionData :: {}", sessionData);
    }


    //-----------questions-----------


    private QuestionMCQDomain createMCQ(Long questionId, String question) {
        QuestionMCQDomain questionMCQ = new QuestionMCQDomain();
        questionMCQ.setQuestionId(questionId);
        questionMCQ.setQuestion(question);
        questionMCQ.setType(1L);
        questionMCQ.setLang("EN");
        questionMCQ.setLevel((byte) 1);
        questionMCQ.setSingle(true);
        questionMCQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionMCQ.setHelpDomain(new HelpDomain().setHelpId(1L).setName("HelpDomain MCQ#1").setHelp("See HelpDomain MCQ#1")
                   .setResourceDomain(new ResourceDomain().setResourceId(1L).setDescription("ResourceDomain helpDomain MCQ").setLink("https://resourceDomains.com/#1")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(2L).setDescription("ResourceDomain question #2").setLink("https://resourceDomains.com/#2");
        questionMCQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));
        // ---answers---
        AnswerMCQDomain answer0 = new AnswerMCQDomain().setAnswerId(1L).setAnswer("Answer#1").setPercent((short)100).setRequired(true)
                .setResourceDomain(new ResourceDomain().setResourceId(3L).setDescription("ResourceDomain answer #1").setLink("https://resourceDomains.com/#3"));
        AnswerMCQDomain answer1 = new AnswerMCQDomain().setAnswerId(2L).setAnswer("Answer#2").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(4L).setDescription("ResourceDomain answer #2").setLink("https://resourceDomains.com/#4"));
        AnswerMCQDomain answer2 = new AnswerMCQDomain().setAnswerId(3L).setAnswer("Answer#3").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(5L).setDescription("ResourceDomain answer #3").setLink("https://resourceDomains.com/#5"));
        AnswerMCQDomain answer3 = new AnswerMCQDomain().setAnswerId(4L).setAnswer("Answer#4").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(6L).setDescription("ResourceDomain answer #4").setLink("https://resourceDomains.com/#6"));

        questionMCQ.setAnswers(new HashSet<>(Arrays.asList(answer0, answer1, answer2, answer3)));
        return questionMCQ;
    }

    private QuestionFBSQDomain createFBSQ(Long questionId, String question) {
        QuestionFBSQDomain questionFBSQ = new QuestionFBSQDomain();
        questionFBSQ.setQuestionId(questionId);
        questionFBSQ.setQuestion(question);
        questionFBSQ.setType(2L);
        questionFBSQ.setLang("EN");
        questionFBSQ.setLevel((byte) 1);
        questionFBSQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionFBSQ.setHelpDomain(new HelpDomain().setHelpId(2L).setName("HelpDomain FBSQ #1").setHelp("See HelpDomain FBSQ #1")
                .setResourceDomain(new ResourceDomain().setResourceId(7L).setDescription("ResourceDomain helpDomain FBSQ").setLink("https://resourceDomains.com/#7")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(8L).setDescription("ResourceDomain question #2").setLink("https://resourceDomains.com/#8");
        questionFBSQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // -----answer-----// phrases with no resourceDomains here
        PhraseDomain p0 = new PhraseDomain().setPhraseId(1L).setPhrase("PhraseDomain #1");
        PhraseDomain p1 = new PhraseDomain().setPhraseId(2L).setPhrase("PhraseDomain #2");
        PhraseDomain p2 = new PhraseDomain().setPhraseId(3L).setPhrase("PhraseDomain #3");
        PhraseDomain p3 = new PhraseDomain().setPhraseId(4L).setPhrase("PhraseDomain #4");

        questionFBSQ.setAnswer(new AnswerFBSQDomain().setAnswerId(1L).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p0, p1, p2, p3))));

        return questionFBSQ;
    }

    private SettingsFBDomain createSettingsFB() {
        return new SettingsFBDomain()
                .setSettingsId(1L)
                .setName("SettingsDomain #1")
                .setTypoAllowed(false)
                .setCaseSensitive(true)
                .setLang("EN")
                .setNumeric(false)
                .setSymbolsLimit((short)10)
                .setWordsLimit((short)1);
    }

    private QuestionFBMQDomain createFBMQ(Long questionId, String question) {
        QuestionFBMQDomain questionFBMQ = new QuestionFBMQDomain();
        questionFBMQ.setQuestionId(questionId);
        questionFBMQ.setQuestion(question);
        questionFBMQ.setType(3L);
        questionFBMQ.setLang("EN");
        questionFBMQ.setLevel((byte) 1);
        questionFBMQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionFBMQ.setHelpDomain(new HelpDomain().setHelpId(3L).setName("HelpDomain FBMQ #3").setHelp("See HelpDomain FBMQ #3")
                .setResourceDomain(new ResourceDomain().setResourceId(9L).setDescription("ResourceDomain helpDomain FBMQ").setLink("https://resourceDomains.com/#9")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(10L).setDescription("ResourceDomain question #3").setLink("https://resourceDomains.com/#10");
        questionFBMQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // -----answers----- //phrases with no resourceDomains here too

        PhraseDomain p10 = new PhraseDomain().setPhraseId(5L).setPhrase("PhraseDomain #1 for FBMQ answer #1");
        PhraseDomain p11 = new PhraseDomain().setPhraseId(6L).setPhrase("PhraseDomain #2 for FBMQ answer #1");
        PhraseDomain p12 = new PhraseDomain().setPhraseId(7L).setPhrase("PhraseDomain #3 for FBMQ answer #1");
        PhraseDomain p13 = new PhraseDomain().setPhraseId(8L).setPhrase("PhraseDomain #4 for FBMQ answer #1");

        AnswerFBMQDomain a0 = new AnswerFBMQDomain().setAnswerId(1L).setPhrase("Target phraseDomain for FBMQ answer #1").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p10, p11, p12, p13)));

        PhraseDomain p20 = new PhraseDomain().setPhraseId(9L).setPhrase("PhraseDomain #1 for FBMQ answer #2");
        PhraseDomain p21 = new PhraseDomain().setPhraseId(10L).setPhrase("PhraseDomain #2 for FBMQ answer #2");
        PhraseDomain p22 = new PhraseDomain().setPhraseId(11L).setPhrase("PhraseDomain #3 for FBMQ answer #2");
        PhraseDomain p23 = new PhraseDomain().setPhraseId(12L).setPhrase("PhraseDomain #4 for FBMQ answer #2");

        AnswerFBMQDomain a1 = new AnswerFBMQDomain().setAnswerId(2L).setPhrase("Target phraseDomain for FBMQ answer #2").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p20, p21, p22, p23)));

        PhraseDomain p30 = new PhraseDomain().setPhraseId(13L).setPhrase("PhraseDomain #1 for FBMQ answer #3");
        PhraseDomain p31 = new PhraseDomain().setPhraseId(14L).setPhrase("PhraseDomain #2 for FBMQ answer #3");
        PhraseDomain p32 = new PhraseDomain().setPhraseId(15L).setPhrase("PhraseDomain #3 for FBMQ answer #3");
        PhraseDomain p33 = new PhraseDomain().setPhraseId(16L).setPhrase("PhraseDomain #4 for FBMQ answer #3");

        AnswerFBMQDomain a2 = new AnswerFBMQDomain().setAnswerId(3L).setPhrase("Target phraseDomain for FBMQ answer #3").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p30, p31, p32, p33)));

        PhraseDomain p40 = new PhraseDomain().setPhraseId(17L).setPhrase("PhraseDomain #1 for FBMQ answer #4");
        PhraseDomain p41 = new PhraseDomain().setPhraseId(18L).setPhrase("PhraseDomain #2 for FBMQ answer #4");
        PhraseDomain p42 = new PhraseDomain().setPhraseId(19L).setPhrase("PhraseDomain #3 for FBMQ answer #4");
        PhraseDomain p43 = new PhraseDomain().setPhraseId(20L).setPhrase("PhraseDomain #4 for FBMQ answer #4");

        AnswerFBMQDomain a3 = new AnswerFBMQDomain().setAnswerId(4L).setPhrase("Target phraseDomain for FBMQ answer #4").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p40, p41, p42, p43)));

        questionFBMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));

        return questionFBMQ;
    }


    private QuestionMQDomain createMQ(Long questionId, String question) {
        QuestionMQDomain questionMQ = new QuestionMQDomain();
        questionMQ.setQuestionId(questionId);
        questionMQ.setQuestion(question);
        questionMQ.setType(4L);
        questionMQ.setLang("EN");
        questionMQ.setLevel((byte) 1);
        questionMQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionMQ.setHelpDomain(new HelpDomain().setHelpId(4L).setName("HelpDomain MQ#4").setHelp("See HelpDomain MQ#4")
                .setResourceDomain(new ResourceDomain().setResourceId(11L).setDescription("ResourceDomain helpDomain MQ").setLink("https://resourceDomains.com/#11")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(12L).setDescription("ResourceDomain question #4").setLink("https://resourceDomains.com/#12");
        questionMQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // ---answers---

        PhraseDomain p10 = new PhraseDomain().setPhraseId(21L).setPhrase("Left PhraseDomain for MQ answer #1")
                .setResourceDomain(new ResourceDomain().setResourceId(13L).setDescription("Left phraseDomain resourceDomain for answer #1").setLink("https://resourceDomains.com/#13"));
        PhraseDomain p11 = new PhraseDomain().setPhraseId(22L).setPhrase("Right PhraseDomain for MQ answer #1");
        AnswerMQDomain a0 = new AnswerMQDomain().setAnswerId(1L).setLeftPhraseDomain(p10).setRightPhraseDomain(p11);

        PhraseDomain p20 = new PhraseDomain().setPhraseId(23L).setPhrase("Left PhraseDomain for MQ answer #2")
                .setResourceDomain(new ResourceDomain().setResourceId(14L).setDescription("Left phraseDomain resourceDomain for answer #2").setLink("https://resourceDomains.com/#14"));
        PhraseDomain p21 = new PhraseDomain().setPhraseId(24L).setPhrase("Right PhraseDomain for MQ answer #2");
        AnswerMQDomain a1 = new AnswerMQDomain().setAnswerId(2L).setLeftPhraseDomain(p20).setRightPhraseDomain(p21);

        PhraseDomain p30 = new PhraseDomain().setPhraseId(25L).setPhrase("Left PhraseDomain for MQ answer #3")
                .setResourceDomain(new ResourceDomain().setResourceId(15L).setDescription("Left phraseDomain resourceDomain for answer #3").setLink("https://resourceDomains.com/#15"));
        PhraseDomain p31 = new PhraseDomain().setPhraseId(26L).setPhrase("Right PhraseDomain for MQ answer #3");
        AnswerMQDomain a2 = new AnswerMQDomain().setAnswerId(3L).setLeftPhraseDomain(p30).setRightPhraseDomain(p31);

        PhraseDomain p40 = new PhraseDomain().setPhraseId(27L).setPhrase("Left PhraseDomain for MQ answer #4")
                .setResourceDomain(new ResourceDomain().setResourceId(16L).setDescription("Left phraseDomain resourceDomain for answer #4").setLink("https://resourceDomains.com/#16"));
        PhraseDomain p41 = new PhraseDomain().setPhraseId(28L).setPhrase("Right PhraseDomain for MQ answer #4");
        AnswerMQDomain a3 = new AnswerMQDomain().setAnswerId(4L).setLeftPhraseDomain(p40).setRightPhraseDomain(p41);

        questionMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));

        return questionMQ;
    }

    private QuestionSQDomain createSQ(Long questionId, String question) {
        QuestionSQDomain questionSQ = new QuestionSQDomain();
        questionSQ.setQuestionId(questionId);
        questionSQ.setQuestion(question);
        questionSQ.setType(5L);
        questionSQ.setLang("EN");
        questionSQ.setLevel((byte) 1);
        questionSQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionSQ.setHelpDomain(new HelpDomain().setHelpId(5L).setName("HelpDomain SQ#5").setHelp("See HelpDomain SQ#5")
                .setResourceDomain(new ResourceDomain().setResourceId(17L).setDescription("ResourceDomain helpDomain SQ").setLink("https://resourceDomains.com/#17")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(18L).setDescription("ResourceDomain question #4").setLink("https://resourceDomains.com/#18");
        questionSQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // ---answers---
        PhraseDomain p10 = new PhraseDomain().setPhraseId(29L).setPhrase("PhraseDomain #1 for SQ answer #1")
                .setResourceDomain(new ResourceDomain().setResourceId(19L).setDescription("ResourceDomain for SQ answer #1").setLink("https://resourceDomains.com/#19"));
        AnswerSQDomain a0 = new AnswerSQDomain().setAnswerId(1L).setPhraseDomain(p10).setOrder((short)0);

        PhraseDomain p20 = new PhraseDomain().setPhraseId(30L).setPhrase("PhraseDomain #2 for SQ answer #2")
                .setResourceDomain(new ResourceDomain().setResourceId(20L).setDescription("ResourceDomain for SQ answer #2").setLink("https://resourceDomains.com/#20"));
        AnswerSQDomain a1 = new AnswerSQDomain().setAnswerId(2L).setPhraseDomain(p20).setOrder((short)1);

        PhraseDomain p30 = new PhraseDomain().setPhraseId(31L).setPhrase("PhraseDomain #3 for SQ answer #3")
                .setResourceDomain(new ResourceDomain().setResourceId(21L).setDescription("ResourceDomain for SQ answer #3").setLink("https://resourceDomains.com/#21"));
        AnswerSQDomain a2 = new AnswerSQDomain().setAnswerId(3L).setPhraseDomain(p30).setOrder((short)2);

        PhraseDomain p40 = new PhraseDomain().setPhraseId(32L).setPhrase("PhraseDomain #4 for SQ answer #4")
                .setResourceDomain(new ResourceDomain().setResourceId(22L).setDescription("ResourceDomain for SQ answer #4").setLink("https://resourceDomains.com/#22"));
        AnswerSQDomain a3 = new AnswerSQDomain().setAnswerId(4L).setPhraseDomain(p40).setOrder((short)3);

        PhraseDomain p50 = new PhraseDomain().setPhraseId(33L).setPhrase("PhraseDomain #5 for SQ answer #5")
                .setResourceDomain(new ResourceDomain().setResourceId(23L).setDescription("ResourceDomain for SQ answer #5").setLink("https://resourceDomains.com/#23"));
        AnswerSQDomain a4 = new AnswerSQDomain().setAnswerId(5L).setPhraseDomain(p50).setOrder((short)4);

        questionSQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3, a4)));

        return questionSQ;
    }
}
