package ua.edu.ratos.service.session.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.edu.ratos.service.domain.ModeDomain;
import ua.edu.ratos.service.domain.PreviousBatchResult;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.io.IOException;
import java.util.List;

public class BatchOutDtoDeserializer extends JsonDeserializer<BatchOutDto> {

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public BatchOutDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        List<QuestionSessionOutDto> questions = objectMapper.readerFor(new TypeReference<List<QuestionSessionOutDto>>() {})
                .readValue(root.path("batch"));
        ModeDomain modeDomain = objectMapper.treeToValue(root.path("modeDomain"), ModeDomain.class);
        long timeLeft = root.path("timeLeft").asLong();
        int questionsLeft = root.path("questionsLeft").asInt();
        long batchTimeLimit = root.path("batchTimeLimit").asLong();
        int batchesLeft = root.path("batchesLeft").asInt();

        BatchOutDto batchOutDto = new BatchOutDto.Builder()
                .withQuestions(questions)
                .inMode(modeDomain)
                .withTimeLeft(timeLeft)
                .withQuestionsLeft(questionsLeft)
                .withBatchTimeLimit(batchTimeLimit)
                .withBatchesLeft(batchesLeft)
                .build();

        // add optional previous result with setter
        JsonNode previousBatchResult = root.path("previousBatchResult");
        if (!previousBatchResult.isMissingNode()) {
            PreviousBatchResult pbr = objectMapper.treeToValue(root.path("previousBatchResult"), PreviousBatchResult.class);
            batchOutDto.setPreviousBatchResult(pbr);
        }
        return batchOutDto;
    }
}
