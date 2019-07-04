package ua.edu.ratos.service.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseMCQ;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@RunWith(JUnit4.class)
public class BatchInDtoSerializationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private BatchInDto batchInDto;

    @Before
    public void init() {
        Map<Long, Response> map = new HashMap<>();

        Response r1 = new ResponseMCQ(1L, new HashSet<>(Arrays.asList(10L, 11L, 12L)));
        map.put(1L, r1);

        Response r2 = new ResponseMCQ(2L, new HashSet<>(Arrays.asList(20L, 21L, 22L)));
        map.put(2L, r2);

        Response r3 = new ResponseMCQ(3L, new HashSet<>(Arrays.asList(30L)));
        map.put(3L, r3);

        this.batchInDto = new BatchInDto(map);
    }

    @Test
    public void serializeTest() throws Exception {
        String result = objectMapper.writeValueAsString(batchInDto);
        log.debug("result = {}", result);
    }
}
