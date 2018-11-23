package ua.edu.ratos.service.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.session.domain.*;
import ua.edu.ratos.service.session.domain.answer.*;
import ua.edu.ratos.service.session.domain.batch.BatchOut;
import ua.edu.ratos.service.session.domain.question.*;
import ua.edu.ratos.service.session.domain.response.*;
import ua.edu.ratos.service.session.serializer.SessionDataSerializer;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SessionDataSerializerTest {

    public static final String JSON_INIT = "classpath:json/session_data_init.json";

    public static final String JSON_INTERMEDIATE = "classpath:json/session_data_intermediate.json";

    public static final String JSON_TERMINAL = "classpath:json/session_data_terminal.json";

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private SessionDataSerializer serializer;

    private Scheme scheme;

    @Before
    public void init() {
        scheme = new Scheme();
        scheme.setSchemeId(1L);
        scheme.setName("Scheme#1");
        scheme.setMode(new Mode().setModeId(1L).setName("Mode#1"));
        scheme.setSettings(new Settings().setSetId(1L).setName("Settings#1")
                .setDaysKeepResultDetails((short)1)
                .setLevel2Coefficient(1)
                .setLevel3Coefficient(1)
                .setQuestionsPerSheet((short)5)
                .setSecondsPerQuestion(60));
        scheme.setStrategy(new Strategy().setStrId(1L).setName("Strategy#1"));
        scheme.setGrading(new Grading().setGradingId(1L).setName("Grading #1"));

        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME);
        objectMapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, localDateTimeDeserializer));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void serializeInitialStateWith5DifferentQuestionsTest() throws Exception{
        // init
        List<Question> sequence = Arrays.asList(
                createMCQ(1L, "Question Multiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3"),
                createMQ(4L, "Matcher Question #4"),
                createSQ(5L, "Sequence question #5"));

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(scheme)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(5)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();

        sessionData.setCurrentBatch(new BatchOut.Builder()
                .withQuestions(sequence.stream().map(q->q.toDto()).collect(Collectors.toList()))
                .withBatchesLeft(0)
                .withQuestionsLeft(0)
                .withTimeLeft(-1)
                .withBatchTimeLimit(-1)
                .inMode(scheme.getMode())
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
        //log.debug("result :: {}", actual);
    }

    @Test
    // Typical use case (1 question ber batch, limited in time, preserved in the middle of the test)
    public void serializeIntermediateStateWith1QuestionPerBatchTest() throws Exception {
        // init
        List<Question> sequence = Arrays.asList(
                createMCQ(1L, "Question Multiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3"),
                createMQ(4L, "Matcher Question #4"),
                createSQ(5L, "Sequence question #5"));

        scheme.getSettings().setQuestionsPerSheet((short) 1);

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(scheme)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(1)
                .withSessionTimeout(LocalDateTime.parse("2018-11-26T09:33:45"))
                .withPerQuestionTimeLimit(-1)
                .build();

        // last but one question in the current batch
        sessionData.setCurrentBatch(new BatchOut.Builder()
                .withQuestions(Arrays.asList(sequence.get(3).toDto()))
                .withBatchesLeft(1)
                .withQuestionsLeft(1)
                .withTimeLeft(250)
                .withBatchTimeLimit(-1)
                .inMode(scheme.getMode())
                .build());

        sessionData.setCurrentBatchIssued(LocalDateTime.parse("2018-11-26T09:25:20"));
        sessionData.setCurrentBatchTimeOut(LocalDateTime.MAX);

        sessionData.setCurrentIndex(4);

        ProgressData progressData = new ProgressData();
        progressData.setTimeSpent(330);
        progressData.setScore(75.63);

        ResponseEvaluated r0 = new ResponseEvaluated(1L,
                new ResponseMultipleChoice(1L, new HashSet<>(Arrays.asList(1L))), 100);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFillBlankSingle(2L, "wrong phrase"), 0);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFillBlankMultiple(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFillBlankMultiple.Pair(1L, "(correct) Target phrase for FBMQ answer #1"),
                                new ResponseFillBlankMultiple.Pair(2L, "(correct) Target phrase for FBMQ answer #2"),
                                new ResponseFillBlankMultiple.Pair(3L, "(correct) Target phrase for FBMQ answer #3"),
                                new ResponseFillBlankMultiple.Pair(4L, "(correct) Target phrase for FBMQ answer #4")
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
        List<Question> sequence = Arrays.asList(
                createMCQ(1L, "Question Multiple Choice #1"),
                createFBSQ(2L, "Question Fill Blank Single #2"),
                createFBMQ(3L, "Question Fill Blank Multiple #3"),
                createMQ(4L, "Matcher Question #4"),
                createSQ(5L, "Sequence question #5"));

        scheme.getSettings().setQuestionsPerSheet((short)1);

        SessionData sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(scheme)
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
                new ResponseMultipleChoice(1L, new HashSet<>(Arrays.asList(1L))), 100);
        BatchEvaluated b0 = new BatchEvaluated(Collections.singletonMap(1L, r0), Arrays.asList(), 30);

        ResponseEvaluated r1 = new ResponseEvaluated(2L,
                new ResponseFillBlankSingle(2L, "wrong phrase"), 0);
        BatchEvaluated b1 = new BatchEvaluated(Collections.singletonMap(2L, r1), Arrays.asList(2L), 50);

        ResponseEvaluated r2 = new ResponseEvaluated(3L,
                new ResponseFillBlankMultiple(3L,
                        new HashSet<>(Arrays.asList(
                                new ResponseFillBlankMultiple.Pair(1L, "(correct) Target phrase for FBMQ answer #1"),
                                new ResponseFillBlankMultiple.Pair(2L, "(correct) Target phrase for FBMQ answer #2"),
                                new ResponseFillBlankMultiple.Pair(3L, "(correct) Target phrase for FBMQ answer #3"),
                                new ResponseFillBlankMultiple.Pair(4L, "(correct) Target phrase for FBMQ answer #4")
                        ))), 100);
        BatchEvaluated b2 = new BatchEvaluated(Collections.singletonMap(3L, r2), Arrays.asList(), 25);

        ResponseEvaluated r3 = new ResponseEvaluated(4L,
                new ResponseMatcher(4L, new HashSet<>(Arrays.asList(
                        new ResponseMatcher.Triple(1L, 21L, 22L),
                        new ResponseMatcher.Triple(2L, 23L, 24L),
                        new ResponseMatcher.Triple(3L, 25L, 26L),
                        new ResponseMatcher.Triple(4L, 27L, 28L)
                ))), 100);
        BatchEvaluated b3 = new BatchEvaluated(Collections.singletonMap(4L, r3), Arrays.asList(), 30);

        ResponseEvaluated r4 = new ResponseEvaluated(5L,
                new ResponseSequence(5L, Arrays.asList(29L, 30L, 31L, 32L, 33L)), 100);
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


    //-----------questions-----------


    private QuestionMCQ createMCQ(Long questionId, String question) {
        QuestionMCQ questionMCQ = new QuestionMCQ();
        questionMCQ.setQuestionId(questionId);
        questionMCQ.setQuestion(question);
        questionMCQ.setType(1L);
        questionMCQ.setLang("EN");
        questionMCQ.setLevel((byte) 1);
        questionMCQ.setSingle(true);
        questionMCQ.setTheme(new Theme().setThemeId(1L).setName("Theme#1"));
        questionMCQ.setHelp(new Help().setHelpId(1L).setName("Help MCQ#1").setHelp("See Help MCQ#1")
                   .setResource(new Resource().setResourceId(1L).setDescription("Resource help MCQ").setLink("https://resources.com/#1")));
        Resource resource = new Resource().setResourceId(2L).setDescription("Resource question #2").setLink("https://resources.com/#2");
        questionMCQ.setResources(new HashSet<>(Arrays.asList(resource)));
        // ---answers---
        AnswerMCQ answer0 = new AnswerMCQ().setAnswerId(1L).setAnswer("Answer#1").setPercent((short)100).setRequired(true)
                .setResource(new Resource().setResourceId(3L).setDescription("Resource answer #1").setLink("https://resources.com/#3"));
        AnswerMCQ answer1 = new AnswerMCQ().setAnswerId(2L).setAnswer("Answer#2").setPercent((short)0).setRequired(false)
                .setResource(new Resource().setResourceId(4L).setDescription("Resource answer #2").setLink("https://resources.com/#4"));
        AnswerMCQ answer2 = new AnswerMCQ().setAnswerId(3L).setAnswer("Answer#3").setPercent((short)0).setRequired(false)
                .setResource(new Resource().setResourceId(5L).setDescription("Resource answer #3").setLink("https://resources.com/#5"));
        AnswerMCQ answer3 = new AnswerMCQ().setAnswerId(4L).setAnswer("Answer#4").setPercent((short)0).setRequired(false)
                .setResource(new Resource().setResourceId(6L).setDescription("Resource answer #4").setLink("https://resources.com/#6"));

        questionMCQ.setAnswers(new HashSet<>(Arrays.asList(answer0, answer1, answer2, answer3)));
        return questionMCQ;
    }

    private QuestionFBSQ createFBSQ(Long questionId, String question) {
        QuestionFBSQ questionFBSQ = new QuestionFBSQ();
        questionFBSQ.setQuestionId(questionId);
        questionFBSQ.setQuestion(question);
        questionFBSQ.setType(2L);
        questionFBSQ.setLang("EN");
        questionFBSQ.setLevel((byte) 1);
        questionFBSQ.setTheme(new Theme().setThemeId(1L).setName("Theme#1"));
        questionFBSQ.setHelp(new Help().setHelpId(2L).setName("Help FBSQ #1").setHelp("See Help FBSQ #1")
                .setResource(new Resource().setResourceId(7L).setDescription("Resource help FBSQ").setLink("https://resources.com/#7")));
        Resource resource = new Resource().setResourceId(8L).setDescription("Resource question #2").setLink("https://resources.com/#8");
        questionFBSQ.setResources(new HashSet<>(Arrays.asList(resource)));

        // -----answer-----// phrases with no resources here
        Phrase p0 = new Phrase().setPhraseId(1L).setPhrase("Phrase #1");
        Phrase p1 = new Phrase().setPhraseId(2L).setPhrase("Phrase #2");
        Phrase p2 = new Phrase().setPhraseId(3L).setPhrase("Phrase #3");
        Phrase p3 = new Phrase().setPhraseId(4L).setPhrase("Phrase #4");

        questionFBSQ.setAnswer(new AnswerFBSQ().setAnswerId(1L).setSettings(createSettingsFB())
                .setAcceptedPhrases(new HashSet<>(Arrays.asList(p0, p1, p2, p3))));

        return questionFBSQ;
    }

    private SettingsFB createSettingsFB() {
        return new SettingsFB()
                .setSettingsId(1L)
                .setName("Settings #1")
                .setTypoAllowed(false)
                .setCaseSensitive(true)
                .setLang("EN")
                .setNumeric(false)
                .setSymbolsLimit((short)10)
                .setWordsLimit((short)1);
    }

    private QuestionFBMQ createFBMQ(Long questionId, String question) {
        QuestionFBMQ questionFBMQ = new QuestionFBMQ();
        questionFBMQ.setQuestionId(questionId);
        questionFBMQ.setQuestion(question);
        questionFBMQ.setType(3L);
        questionFBMQ.setLang("EN");
        questionFBMQ.setLevel((byte) 1);
        questionFBMQ.setTheme(new Theme().setThemeId(1L).setName("Theme#1"));
        questionFBMQ.setHelp(new Help().setHelpId(3L).setName("Help FBMQ #3").setHelp("See Help FBMQ #3")
                .setResource(new Resource().setResourceId(9L).setDescription("Resource help FBMQ").setLink("https://resources.com/#9")));
        Resource resource = new Resource().setResourceId(10L).setDescription("Resource question #3").setLink("https://resources.com/#10");
        questionFBMQ.setResources(new HashSet<>(Arrays.asList(resource)));

        // -----answers----- //phrases with no resources here too

        Phrase p10 = new Phrase().setPhraseId(5L).setPhrase("Phrase #1 for FBMQ answer #1");
        Phrase p11 = new Phrase().setPhraseId(6L).setPhrase("Phrase #2 for FBMQ answer #1");
        Phrase p12 = new Phrase().setPhraseId(7L).setPhrase("Phrase #3 for FBMQ answer #1");
        Phrase p13 = new Phrase().setPhraseId(8L).setPhrase("Phrase #4 for FBMQ answer #1");

        AnswerFBMQ a0 = new AnswerFBMQ().setAnswerId(1L).setPhrase("Target phrase for FBMQ answer #1").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhrases(new HashSet<>(Arrays.asList(p10, p11, p12, p13)));

        Phrase p20 = new Phrase().setPhraseId(9L).setPhrase("Phrase #1 for FBMQ answer #2");
        Phrase p21 = new Phrase().setPhraseId(10L).setPhrase("Phrase #2 for FBMQ answer #2");
        Phrase p22 = new Phrase().setPhraseId(11L).setPhrase("Phrase #3 for FBMQ answer #2");
        Phrase p23 = new Phrase().setPhraseId(12L).setPhrase("Phrase #4 for FBMQ answer #2");

        AnswerFBMQ a1 = new AnswerFBMQ().setAnswerId(2L).setPhrase("Target phrase for FBMQ answer #2").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhrases(new HashSet<>(Arrays.asList(p20, p21, p22, p23)));

        Phrase p30 = new Phrase().setPhraseId(13L).setPhrase("Phrase #1 for FBMQ answer #3");
        Phrase p31 = new Phrase().setPhraseId(14L).setPhrase("Phrase #2 for FBMQ answer #3");
        Phrase p32 = new Phrase().setPhraseId(15L).setPhrase("Phrase #3 for FBMQ answer #3");
        Phrase p33 = new Phrase().setPhraseId(16L).setPhrase("Phrase #4 for FBMQ answer #3");

        AnswerFBMQ a2 = new AnswerFBMQ().setAnswerId(3L).setPhrase("Target phrase for FBMQ answer #3").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhrases(new HashSet<>(Arrays.asList(p30, p31, p32, p33)));

        Phrase p40 = new Phrase().setPhraseId(17L).setPhrase("Phrase #1 for FBMQ answer #4");
        Phrase p41 = new Phrase().setPhraseId(18L).setPhrase("Phrase #2 for FBMQ answer #4");
        Phrase p42 = new Phrase().setPhraseId(19L).setPhrase("Phrase #3 for FBMQ answer #4");
        Phrase p43 = new Phrase().setPhraseId(20L).setPhrase("Phrase #4 for FBMQ answer #4");

        AnswerFBMQ a3 = new AnswerFBMQ().setAnswerId(4L).setPhrase("Target phrase for FBMQ answer #4").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhrases(new HashSet<>(Arrays.asList(p40, p41, p42, p43)));

        questionFBMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));

        return questionFBMQ;
    }


    private QuestionMQ createMQ(Long questionId, String question) {
        QuestionMQ questionMQ = new QuestionMQ();
        questionMQ.setQuestionId(questionId);
        questionMQ.setQuestion(question);
        questionMQ.setType(4L);
        questionMQ.setLang("EN");
        questionMQ.setLevel((byte) 1);
        questionMQ.setTheme(new Theme().setThemeId(1L).setName("Theme#1"));
        questionMQ.setHelp(new Help().setHelpId(4L).setName("Help MQ#4").setHelp("See Help MQ#4")
                .setResource(new Resource().setResourceId(11L).setDescription("Resource help MQ").setLink("https://resources.com/#11")));
        Resource resource = new Resource().setResourceId(12L).setDescription("Resource question #4").setLink("https://resources.com/#12");
        questionMQ.setResources(new HashSet<>(Arrays.asList(resource)));

        // ---answers---

        Phrase p10 = new Phrase().setPhraseId(21L).setPhrase("Left Phrase for MQ answer #1")
                .setResource(new Resource().setResourceId(13L).setDescription("Left phrase resource for answer #1").setLink("https://resources.com/#13"));
        Phrase p11 = new Phrase().setPhraseId(22L).setPhrase("Right Phrase for MQ answer #1");
        AnswerMQ a0 = new AnswerMQ().setAnswerId(1L).setLeftPhrase(p10).setRightPhrase(p11);

        Phrase p20 = new Phrase().setPhraseId(23L).setPhrase("Left Phrase for MQ answer #2")
                .setResource(new Resource().setResourceId(14L).setDescription("Left phrase resource for answer #2").setLink("https://resources.com/#14"));
        Phrase p21 = new Phrase().setPhraseId(24L).setPhrase("Right Phrase for MQ answer #2");
        AnswerMQ a1 = new AnswerMQ().setAnswerId(2L).setLeftPhrase(p20).setRightPhrase(p21);

        Phrase p30 = new Phrase().setPhraseId(25L).setPhrase("Left Phrase for MQ answer #3")
                .setResource(new Resource().setResourceId(15L).setDescription("Left phrase resource for answer #3").setLink("https://resources.com/#15"));
        Phrase p31 = new Phrase().setPhraseId(26L).setPhrase("Right Phrase for MQ answer #3");
        AnswerMQ a2 = new AnswerMQ().setAnswerId(3L).setLeftPhrase(p30).setRightPhrase(p31);

        Phrase p40 = new Phrase().setPhraseId(27L).setPhrase("Left Phrase for MQ answer #4")
                .setResource(new Resource().setResourceId(16L).setDescription("Left phrase resource for answer #4").setLink("https://resources.com/#16"));
        Phrase p41 = new Phrase().setPhraseId(28L).setPhrase("Right Phrase for MQ answer #4");
        AnswerMQ a3 = new AnswerMQ().setAnswerId(4L).setLeftPhrase(p40).setRightPhrase(p41);

        questionMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));

        return questionMQ;
    }

    private QuestionSQ createSQ(Long questionId, String question) {
        QuestionSQ questionSQ = new QuestionSQ();
        questionSQ.setQuestionId(questionId);
        questionSQ.setQuestion(question);
        questionSQ.setType(5L);
        questionSQ.setLang("EN");
        questionSQ.setLevel((byte) 1);
        questionSQ.setTheme(new Theme().setThemeId(1L).setName("Theme#1"));
        questionSQ.setHelp(new Help().setHelpId(5L).setName("Help SQ#5").setHelp("See Help SQ#5")
                .setResource(new Resource().setResourceId(17L).setDescription("Resource help SQ").setLink("https://resources.com/#17")));
        Resource resource = new Resource().setResourceId(18L).setDescription("Resource question #4").setLink("https://resources.com/#18");
        questionSQ.setResources(new HashSet<>(Arrays.asList(resource)));

        // ---answers---
        Phrase p10 = new Phrase().setPhraseId(29L).setPhrase("Phrase #1 for SQ answer #1")
                .setResource(new Resource().setResourceId(19L).setDescription("Resource for SQ answer #1").setLink("https://resources.com/#19"));
        AnswerSQ a0 = new AnswerSQ().setAnswerId(1L).setPhrase(p10).setOrder((short)0);

        Phrase p20 = new Phrase().setPhraseId(30L).setPhrase("Phrase #2 for SQ answer #2")
                .setResource(new Resource().setResourceId(20L).setDescription("Resource for SQ answer #2").setLink("https://resources.com/#20"));
        AnswerSQ a1 = new AnswerSQ().setAnswerId(2L).setPhrase(p20).setOrder((short)1);

        Phrase p30 = new Phrase().setPhraseId(31L).setPhrase("Phrase #3 for SQ answer #3")
                .setResource(new Resource().setResourceId(21L).setDescription("Resource for SQ answer #3").setLink("https://resources.com/#21"));
        AnswerSQ a2 = new AnswerSQ().setAnswerId(3L).setPhrase(p30).setOrder((short)2);

        Phrase p40 = new Phrase().setPhraseId(32L).setPhrase("Phrase #4 for SQ answer #4")
                .setResource(new Resource().setResourceId(22L).setDescription("Resource for SQ answer #4").setLink("https://resources.com/#22"));
        AnswerSQ a3 = new AnswerSQ().setAnswerId(4L).setPhrase(p40).setOrder((short)3);

        Phrase p50 = new Phrase().setPhraseId(33L).setPhrase("Phrase #5 for SQ answer #5")
                .setResource(new Resource().setResourceId(23L).setDescription("Resource for SQ answer #5").setLink("https://resources.com/#23"));
        AnswerSQ a4 = new AnswerSQ().setAnswerId(5L).setPhrase(p50).setOrder((short)4);

        questionSQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3, a4)));

        return questionSQ;
    }
}
