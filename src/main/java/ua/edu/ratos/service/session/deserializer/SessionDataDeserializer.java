package ua.edu.ratos.service.session.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.edu.ratos.service.session.domain.*;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.dto.question.QuestionOutDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionDataDeserializer extends JsonDeserializer<SessionData> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SessionData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        String key = root.path("key").asText();
        long userId = root.path("userId").asLong();
        Scheme scheme = objectMapper.treeToValue(root.path("scheme"), Scheme.class);
        List<Question> questions = objectMapper.readerFor(new TypeReference<List<Question>>(){}).readValue(root.path("questions"));
        String sessionTimeOut = root.path("sessionTimeout").asText();
        long perQuestionTimeLimit = root.path("perQuestionTimeLimit").asLong();
        int questionPerBatch = root.path("questionsPerBatch").asInt();
        SessionData sessionData = new SessionData.Builder()
                .withKey(key)
                .forUser(userId)
                .takingScheme(scheme)
                .withIndividualSequence(questions)
                .withSessionTimeout(LocalDateTime.parse(sessionTimeOut))
                .withPerQuestionTimeLimit(perQuestionTimeLimit)
                .withQuestionsPerBatch(questionPerBatch)
                .build();

        // add non-final fields here
        int currentIndex = root.path("currentIndex").asInt();
        sessionData.setCurrentIndex(currentIndex);

        // currentBatch is erased when session is successfully completed
        JsonNode currentBatch = root.path("currentBatch");
        if (!currentBatch.isMissingNode()) {
            String currentBatchTimeOut = root.path("currentBatchTimeOut").asText();
            String currentBatchIssued = root.path("currentBatchIssued").asText();
            sessionData.setCurrentBatch(getCurrentBatch(currentBatch));
            sessionData.setCurrentBatchTimeOut(LocalDateTime.parse(currentBatchTimeOut));
            sessionData.setCurrentBatchIssued(LocalDateTime.parse(currentBatchIssued));
        }

        ProgressData progressData = objectMapper.treeToValue(root.path("progressData"), ProgressData.class);

        Map<Long, MetaData> metaData = objectMapper.convertValue(root.path("metaData"), new TypeReference<HashMap<Long,MetaData>>() {});

        sessionData.setProgressData(progressData);
        sessionData.setMetaData(metaData);
        return sessionData;
    }

    private BatchOutDto getCurrentBatch(JsonNode node) throws IOException {
        List<QuestionOutDto> questions = objectMapper.readerFor(new TypeReference<List<QuestionOutDto>>() {
        }).readValue(node.path("batch"));
        Mode mode = objectMapper.treeToValue(node.path("mode"), Mode.class);
        long timeLeft = node.path("timeLeft").asLong();
        int questionsLeft = node.path("questionsLeft").asInt();
        long batchTimeLimit = node.path("batchTimeLimit").asLong();
        int batchesLeft = node.path("batchesLeft").asInt();

        BatchOutDto batchOutDto = new BatchOutDto.Builder()
                .withQuestions(questions)
                .inMode(mode)
                .withTimeLeft(timeLeft)
                .withQuestionsLeft(questionsLeft)
                .withBatchTimeLimit(batchTimeLimit)
                .withBatchesLeft(batchesLeft)
                .build();

        // add optional previous result with setter
        JsonNode previousBatchResult = node.path("previousBatchResult");
        if (!previousBatchResult.isMissingNode()) {
            PreviousBatchResult pbr = objectMapper.treeToValue(node.path("previousBatchResult"), PreviousBatchResult.class);
            batchOutDto.setPreviousBatchResult(pbr);
        }
        return batchOutDto;
    }

}
