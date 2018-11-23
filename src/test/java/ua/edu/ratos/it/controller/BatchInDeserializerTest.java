package ua.edu.ratos.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.session.domain.batch.BatchIn;
import ua.edu.ratos.service.session.domain.response.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

// No such class in reality as Spring does this conversion automatically
@RunWith(JUnit4.class)
public class BatchInDeserializerTest {

    private static final String JSON_BATCH_IN = "classpath:json/batch_in.json";

    @Test
    public void deserializeBatchInTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_BATCH_IN);
        byte[] encoded = Files.readAllBytes(Paths.get(json.getPath()));
        String string = new String(encoded, Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        final BatchIn batchIn = objectMapper.readValue(string, BatchIn.class);
        Assert.assertEquals(10, batchIn.getResponses().size());

        Assert.assertTrue(batchIn.getResponses().get(101L) instanceof ResponseMultipleChoice);
        Assert.assertTrue(batchIn.getResponses().get(102L) instanceof ResponseMultipleChoice);

        Assert.assertTrue(batchIn.getResponses().get(103L) instanceof ResponseFillBlankSingle);
        Assert.assertTrue(batchIn.getResponses().get(104L) instanceof ResponseFillBlankSingle);

        Assert.assertTrue(batchIn.getResponses().get(105L) instanceof ResponseFillBlankMultiple);
        Assert.assertTrue(batchIn.getResponses().get(106L) instanceof ResponseFillBlankMultiple);

        Assert.assertTrue(batchIn.getResponses().get(107L) instanceof ResponseMatcher);
        Assert.assertTrue(batchIn.getResponses().get(108L) instanceof ResponseMatcher);

        Assert.assertTrue(batchIn.getResponses().get(109L) instanceof ResponseSequence);
        Assert.assertTrue(batchIn.getResponses().get(110L) instanceof ResponseSequence);
    }

}
