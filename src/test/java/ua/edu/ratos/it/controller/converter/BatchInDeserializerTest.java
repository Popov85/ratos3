package ua.edu.ratos.it.controller.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.web.converter.BatchInDeserializer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RunWith(JUnit4.class)
public class BatchInDeserializerTest {

    private static final String JSON_MCQ_ONLY = "classpath:json/converter/batch_in_mcq_single.json";

    private static final String JSON_MCQ_EMPTY = "{\n \"mcq\": [] \n}";

    private static final String JSON_MCQ_ABSENT = "{}";

    private static final String
            JSON_MCQ_NO_ANSWER =
                "{\n" +
                "\"mcq\": [{\n" +
                    " \"questionId\": 101,\n" +
                    " \"answersIds\": []\n" +
                "}]\n" +
                "}";

    private static final String JSON_MCQ_ALL = "classpath:json/converter/batch_in_all.json";

    private BatchInDeserializer batchInDeserializer;

    @Before
    public void init() {
        batchInDeserializer = new BatchInDeserializer();
    }

    //--------------MCQ---------------

    @Test
    public void deserializeMCQCorrectTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File json = ResourceUtils.getFile(JSON_MCQ_ONLY);
        InputStream content = new FileInputStream(json);
        JsonParser jsonParser = mapper.getFactory().createParser(content);
        final BatchIn batchIn = batchInDeserializer.deserialize(jsonParser, null);
        Assert.assertEquals(1, batchIn.getResponses().size());
    }

    @Test
    public void deserializeMCQEmptyTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream content = new ByteArrayInputStream(JSON_MCQ_EMPTY.getBytes(StandardCharsets.UTF_8));
        JsonParser jsonParser = mapper.getFactory().createParser(content);
        final BatchIn batchIn = batchInDeserializer.deserialize(jsonParser, null);
        Assert.assertEquals(0, batchIn.getResponses().size());
    }

    @Test
    public void deserializeMCQAbsentNodeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream content = new ByteArrayInputStream(JSON_MCQ_ABSENT.getBytes(StandardCharsets.UTF_8));
        JsonParser jsonParser = mapper.getFactory().createParser(content);
        final BatchIn batchIn = batchInDeserializer.deserialize(jsonParser, null);
        Assert.assertEquals(0, batchIn.getResponses().size());
    }

    @Test
    public void deserializeMCQNoAnswerTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream content = new ByteArrayInputStream(JSON_MCQ_NO_ANSWER.getBytes(StandardCharsets.UTF_8));
        JsonParser jsonParser = mapper.getFactory().createParser(content);
        final BatchIn batchIn = batchInDeserializer.deserialize(jsonParser, null);
        Assert.assertEquals(1, batchIn.getResponses().size());
    }

    //---------------ALL-----------------

    @Test
    public void deserializeAllCorrectTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File json = ResourceUtils.getFile(JSON_MCQ_ALL);
        InputStream content = new FileInputStream(json);
        JsonParser jsonParser = mapper.getFactory().createParser(content);
        final BatchIn batchIn = batchInDeserializer.deserialize(jsonParser, null);
        Assert.assertEquals(10, batchIn.getResponses().size());

        Assert.assertEquals(2, batchIn.getOptions().size());
    }

}
