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
import ua.edu.ratos.QuestionGenerator;
import ua.edu.ratos.service.domain.*;
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
public class SessionDataSerializerServiceTest extends QuestionGenerator {

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
                .setDaysKeepResultDetails((short) 1)
                .setLevel2Coefficient(1)
                .setLevel3Coefficient(1)
                .setQuestionsPerSheet((short) 5)
                .setSecondsPerQuestion(60));
        schemeDomain.setStrategyDomain(new StrategyDomain().setStrId(1L).setName("StrategyDomain#1"));
        schemeDomain.setGradingDomain(new GradingDomain().setGradingId(1L).setName("GradingDomain #1"));

        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME);
        objectMapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, localDateTimeDeserializer));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void serializeInitialStateWith5DifferentQuestionsTest() throws Exception {
        // init
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionMultiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3", false),
                createMQ(4L, "Matcher Question #4", false),
                createSQ(5L, "Sequence Question #5"));

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
                .withQuestions(sequence.stream().map(q -> q.toDto()).collect(Collectors.toList()))
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
        //log.debug("Serialised sessionData object (init) = {}", actual);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        //log.debug("Serialised sessionData object (init) = {}", actual);
    }



    @Test
    // Typical use case (1 question ber batch, limited in time, preserved in the middle of the test)
    public void serializeIntermediateStateWith1QuestionPerBatchTest() throws Exception {
        // init
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "Question Multiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3", false),
                createMQ(4L, "Matcher Question #4", false),
                createSQ(5L, "Sequence Question #5"));

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
                new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L))), 100, (byte)1,false);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30, false);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFBSQ(2L, "wrong phraseDomain"), 0, (byte)1,false);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50, false);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFBMQ(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFBMQ.Pair(1L, "(correct) Target phraseDomain for FBMQ answer #1"),
                                new ResponseFBMQ.Pair(2L, "(correct) Target phraseDomain for FBMQ answer #2"),
                                new ResponseFBMQ.Pair(3L, "(correct) Target phraseDomain for FBMQ answer #3"),
                                new ResponseFBMQ.Pair(4L, "(correct) Target phraseDomain for FBMQ answer #4")
                        ))), 100, (byte)1,false);
        BatchEvaluated b2 = new BatchEvaluated(Collections.singletonMap(3L, r2), Arrays.asList(), 25, false);

        progressData.setBatchesEvaluated(Arrays.asList(b0, b1, b2));

        sessionData.setProgressData(progressData);

        Map<Long, MetaData> metaDataMap = new HashMap<>();
        MetaData metaL1 = new MetaData();
        metaL1.setIncorrect((short) 0).setSkipped((short) 0).setHelp((short) 0);
        metaDataMap.put(1L, metaL1);

        MetaData metaL2 = new MetaData();
        metaL2.setIncorrect((short) 1).setSkipped((short) 0).setHelp((short) 1);
        metaDataMap.put(2L, metaL2);

        MetaData metaL3 = new MetaData();
        metaL3.setIncorrect((short) 0).setSkipped((short) 1).setHelp((short) 1);
        metaDataMap.put(3L, metaL3);

        sessionData.setMetaData(metaDataMap);

        // actual test begins

        File json = ResourceUtils.getFile(JSON_INTERMEDIATE);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());

        String actual = serializer.serialize(sessionData);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        //log.debug("Serialised sessionData (middle) object = {}", actual);
    }


    @Test
    public void serializeTerminalStateWith1QuestionPerBatchTest() throws Exception {
        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "Question Multiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3", false),
                createMQ(4L, "Matcher Question #4", false),
                createSQ(5L, "Sequence Question #5"));

        schemeDomain.getSettingsDomain().setQuestionsPerSheet((short) 1);

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
                new ResponseMCQ(1L, new HashSet<>(Arrays.asList(1L))), 100, (byte)1, false);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30, false);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFBSQ(2L, "wrong phraseDomain"), 0, (byte)1,false);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50, false);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFBMQ(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFBMQ.Pair(1L, "(correct) Target phraseDomain for FBMQ answer #1"),
                                new ResponseFBMQ.Pair(2L, "(correct) Target phraseDomain for FBMQ answer #2"),
                                new ResponseFBMQ.Pair(3L, "(correct) Target phraseDomain for FBMQ answer #3"),
                                new ResponseFBMQ.Pair(4L, "(correct) Target phraseDomain for FBMQ answer #4")
                        ))), 100, (byte)1, false);
        BatchEvaluated b2 = new BatchEvaluated(Collections.singletonMap(3L, r2), Arrays.asList(), 25, false);

        ResponseEvaluated r3 = new ResponseEvaluated(4L,
                new ResponseMQ(4L, new HashSet<>(Arrays.asList(
                        new ResponseMQ.Triple(1L, 21L, 22L),
                        new ResponseMQ.Triple(2L, 23L, 24L),
                        new ResponseMQ.Triple(3L, 25L, 26L),
                        new ResponseMQ.Triple(4L, 27L, 28L)
                ))), 100, (byte)1,false);
        BatchEvaluated b3 = new BatchEvaluated(Collections.singletonMap(4L, r3), Arrays.asList(), 30, false);

        ResponseEvaluated r4 = new ResponseEvaluated(5L,
                new ResponseSQ(5L, Arrays.asList(29L, 30L, 31L, 32L, 33L)), 100, (byte)1,false);
        BatchEvaluated b4 = new BatchEvaluated(Collections.singletonMap(5L, r4), Arrays.asList(), 15, false);

        progressData.setBatchesEvaluated(Arrays.asList(b0, b1, b2, b3, b4));

        sessionData.setProgressData(progressData);

        Map<Long, MetaData> metaDataMap = new HashMap<>();
        MetaData metaL1 = new MetaData();
        metaL1.setIncorrect((short) 0).setSkipped((short) 0).setHelp((short) 0);
        metaDataMap.put(1L, metaL1);

        MetaData metaL2 = new MetaData();
        metaL2.setIncorrect((short) 1).setSkipped((short) 0).setHelp((short) 1);
        metaDataMap.put(2L, metaL2);

        MetaData metaL3 = new MetaData();
        metaL3.setIncorrect((short) 0).setSkipped((short) 0).setHelp((short) 0);
        metaDataMap.put(3L, metaL3);

        MetaData metaL4 = new MetaData();
        metaL4.setIncorrect((short) 0).setSkipped((short) 0).setHelp((short) 1);
        metaDataMap.put(4L, metaL4);

        MetaData metaL5 = new MetaData();
        metaL5.setIncorrect((short) 0).setSkipped((short) 0).setHelp((short) 1);
        metaDataMap.put(5L, metaL5);

        sessionData.setMetaData(metaDataMap);

        // actual test begins

        File json = ResourceUtils.getFile(JSON_TERMINAL);
        byte[] encoded = Files.readAllBytes(json.toPath());
        String expected = new String(encoded, Charset.defaultCharset());

        String actual = serializer.serialize(sessionData);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        //log.debug("Serialised sessionData (terminal) object = {}", actual);
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
        Assert.assertNotNull(sessionData.getCurrentBatch().get().getBatch());
        Assert.assertNotNull(sessionData.getCurrentBatch().get().getBatchMap());
        Assert.assertNotNull(sessionData.getCurrentBatch().get().getModeDomain());
        Assert.assertNull(sessionData.getCurrentBatch().get().getPreviousBatchResult());

        // currentBatch's questions
        Assert.assertEquals(5, sessionData.getCurrentBatch().get().getBatch().size());
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatch().get(0) instanceof QuestionMCQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatch().get(1) instanceof QuestionFBSQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatch().get(2) instanceof QuestionFBMQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatch().get(3) instanceof QuestionMQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatch().get(4) instanceof QuestionSQSessionOutDto);
        Assert.assertEquals(5, sessionData.getCurrentBatch().get().getBatchMap().size());
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatchMap().get(1L) instanceof QuestionMCQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatchMap().get(2L) instanceof QuestionFBSQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatchMap().get(3L) instanceof QuestionFBMQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatchMap().get(4L) instanceof QuestionMQSessionOutDto);
        Assert.assertTrue(sessionData.getCurrentBatch().get().getBatchMap().get(5L) instanceof QuestionSQSessionOutDto);
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
        Assert.assertEquals(1, sessionData.getCurrentBatch().get().getBatch().size());
        Assert.assertEquals(1, sessionData.getCurrentBatch().get().getBatchMap().size());
        Assert.assertEquals(1, sessionData.getCurrentBatch().get().getBatchesLeft());
        Assert.assertEquals(1, sessionData.getCurrentBatch().get().getQuestionsLeft());
        Assert.assertEquals(-1, sessionData.getCurrentBatch().get().getBatchTimeLimit());
        Assert.assertEquals(250, sessionData.getCurrentBatch().get().getTimeLeft());
        Assert.assertNotNull(sessionData.getCurrentBatch().get().getModeDomain());

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
        Assert.assertFalse(sessionData.getCurrentBatch().isPresent());
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
        log.debug("De-serialised sessionData (terminal) = {}", sessionData);
    }
}
