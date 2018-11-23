package ua.edu.ratos.service.session;

import com.fasterxml.jackson.core.JsonParser;
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
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.domain.question.*;
import ua.edu.ratos.service.session.domain.response.*;
import ua.edu.ratos.service.session.dto.question.*;
import ua.edu.ratos.service.session.serializer.SessionDataDeserializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SessionDataDeserializerTest {

    public static final String JSON_INIT = "classpath:json/session_data_init.json";

    public static final String JSON_INTERMEDIATE = "classpath:json/session_data_intermediate.json";

    public static final String JSON_TERMINAL = "classpath:json/session_data_terminal.json";

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private SessionDataDeserializer deserializer;

    @Before
    public void init() {
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME);
        objectMapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, localDateTimeDeserializer));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void deserializeInitStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_INIT);
        InputStream content = new FileInputStream(json);
        JsonParser jsonParser = objectMapper.getFactory().createParser(content);
        SessionData sessionData = deserializer.deserialize(jsonParser, null);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // scheme
        Assert.assertNotNull(sessionData.getScheme());
        Assert.assertNotNull(sessionData.getScheme().getMode());
        Assert.assertNotNull(sessionData.getScheme().getSettings());
        Assert.assertNotNull(sessionData.getScheme().getGrading());
        Assert.assertNotNull(sessionData.getScheme().getStrategy());
        // questions
        Assert.assertEquals(5, sessionData.getQuestions().size());
        Assert.assertTrue(sessionData.getQuestions().get(0) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestions().get(1) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestions().get(2) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestions().get(3) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestions().get(4) instanceof QuestionSQ);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQ);
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
        Assert.assertNotNull(sessionData.getCurrentBatch().getMode());
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

        log.debug("sessionData :: {}", sessionData);
    }


    @Test
    public void deserializeIntermediateStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_INTERMEDIATE);
        InputStream content = new FileInputStream(json);
        JsonParser jsonParser = objectMapper.getFactory().createParser(content);
        SessionData sessionData = deserializer.deserialize(jsonParser, null);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // scheme
        Assert.assertNotNull(sessionData.getScheme());
        Assert.assertNotNull(sessionData.getScheme().getMode());
        Assert.assertNotNull(sessionData.getScheme().getSettings());
        Assert.assertNotNull(sessionData.getScheme().getGrading());
        Assert.assertNotNull(sessionData.getScheme().getStrategy());
        // questions
        Assert.assertEquals(5, sessionData.getQuestions().size());
        Assert.assertTrue(sessionData.getQuestions().get(0) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestions().get(1) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestions().get(2) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestions().get(3) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestions().get(4) instanceof QuestionSQ);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQ);
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
        Assert.assertNotNull(sessionData.getCurrentBatch().getMode());

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
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().get(1L).getResponse() instanceof ResponseMultipleChoice);
        // 2d batch
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().get(1).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(50L, sessionData.getProgressData().getBatchesEvaluated().get(1).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().get(2L).getResponse() instanceof ResponseFillBlankSingle);
        // 3d batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(25L, sessionData.getProgressData().getBatchesEvaluated().get(2).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().get(3L).getResponse() instanceof ResponseFillBlankMultiple);
        // 4th batch not present
        // 5th batch not present

        // metaData
        Assert.assertFalse(sessionData.getMetaData().isEmpty());
        Assert.assertEquals(3, sessionData.getMetaData().size());

        log.debug("sessionData :: {}", sessionData);
    }

    @Test
    public void deserializeTerminalStateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_TERMINAL);
        InputStream content = new FileInputStream(json);
        JsonParser jsonParser = objectMapper.getFactory().createParser(content);
        SessionData sessionData = deserializer.deserialize(jsonParser, null);
        // sessionData
        Assert.assertNotNull(sessionData);
        Assert.assertEquals("D7C5E8BED7EDA2381E69126A40B3B22C", sessionData.getKey());
        Assert.assertEquals(1L, sessionData.getUserId().longValue());
        // scheme
        Assert.assertNotNull(sessionData.getScheme());
        Assert.assertNotNull(sessionData.getScheme().getMode());
        Assert.assertNotNull(sessionData.getScheme().getSettings());
        Assert.assertNotNull(sessionData.getScheme().getGrading());
        Assert.assertNotNull(sessionData.getScheme().getStrategy());
        // questions
        Assert.assertEquals(5, sessionData.getQuestions().size());
        Assert.assertTrue(sessionData.getQuestions().get(0) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestions().get(1) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestions().get(2) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestions().get(3) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestions().get(4) instanceof QuestionSQ);
        Assert.assertEquals(5, sessionData.getQuestionsMap().size());
        Assert.assertTrue(sessionData.getQuestionsMap().get(1L) instanceof QuestionMCQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(2L) instanceof QuestionFBSQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(3L) instanceof QuestionFBMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(4L) instanceof QuestionMQ);
        Assert.assertTrue(sessionData.getQuestionsMap().get(5L) instanceof QuestionSQ);
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
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(0).getResponsesEvaluated().get(1L).getResponse() instanceof ResponseMultipleChoice);
        // 2d batch
        Assert.assertFalse(sessionData.getProgressData().getBatchesEvaluated().get(1).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(50L, sessionData.getProgressData().getBatchesEvaluated().get(1).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(1).getResponsesEvaluated().get(2L).getResponse() instanceof ResponseFillBlankSingle);
        // 3d batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(25L, sessionData.getProgressData().getBatchesEvaluated().get(2).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(2).getResponsesEvaluated().get(3L).getResponse() instanceof ResponseFillBlankMultiple);
        // 4th batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(3).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(30L, sessionData.getProgressData().getBatchesEvaluated().get(3).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(3).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(3).getResponsesEvaluated().get(4L).getResponse() instanceof ResponseMatcher);
        // 5th batch
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(4).getIncorrectResponseIds().isEmpty());
        Assert.assertEquals(15L, sessionData.getProgressData().getBatchesEvaluated().get(4).getTimeSpent());
        Assert.assertEquals(1, sessionData.getProgressData().getBatchesEvaluated().get(4).getResponsesEvaluated().size());
        Assert.assertTrue(sessionData.getProgressData().getBatchesEvaluated().get(4).getResponsesEvaluated().get(5L).getResponse() instanceof ResponseSequence);


        // metaData
        Assert.assertFalse(sessionData.getMetaData().isEmpty());
        Assert.assertEquals(5, sessionData.getMetaData().size());

        log.debug("sessionData :: {}", sessionData);
    }


}
