package ua.edu.ratos.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.domain.response.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

// No such class in reality as Spring does this conversion automatically
@RunWith(JUnit4.class)
public class BatchInDtoDeserializerTest {

    private static final String JSON_BATCH_IN = "classpath:json/batch_in.json";

    @Test
    public void deserializeBatchInTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_BATCH_IN);
        byte[] encoded = Files.readAllBytes(Paths.get(json.getPath()));
        String string = new String(encoded, Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        final BatchInDto batchInDto = objectMapper.readValue(string, BatchInDto.class);
        Assert.assertEquals(10, batchInDto.getResponses().size());

        Assert.assertTrue(batchInDto.getResponses().get(101L) instanceof ResponseMCQ);
        Assert.assertTrue(batchInDto.getResponses().get(102L) instanceof ResponseMCQ);

        Assert.assertTrue(batchInDto.getResponses().get(103L) instanceof ResponseFBSQ);
        Assert.assertTrue(batchInDto.getResponses().get(104L) instanceof ResponseFBSQ);

        Assert.assertTrue(batchInDto.getResponses().get(105L) instanceof ResponseFBMQ);
        Assert.assertTrue(batchInDto.getResponses().get(106L) instanceof ResponseFBMQ);

        Assert.assertTrue(batchInDto.getResponses().get(107L) instanceof ResponseMQ);
        Assert.assertTrue(batchInDto.getResponses().get(108L) instanceof ResponseMQ);

        Assert.assertTrue(batchInDto.getResponses().get(109L) instanceof ResponseSQ);
        Assert.assertTrue(batchInDto.getResponses().get(110L) instanceof ResponseSQ);
    }

}
