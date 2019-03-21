package ua.edu.ratos.service.session.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SessionDataDeserializer extends JsonDeserializer<SessionData> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SessionData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        String key = root.path("key").asText();
        long userId = root.path("userId").asLong();
        SchemeDomain schemeDomain = objectMapper.treeToValue(root.path("schemeDomain"), SchemeDomain.class);
        List<QuestionDomain> questionDomains = objectMapper.readerFor(new TypeReference<List<QuestionDomain>>(){}).readValue(root.path("questionDomains"));
        String sessionTimeOut = root.path("sessionTimeout").asText();
        long perQuestionTimeLimit = root.path("perQuestionTimeLimit").asLong();
        int questionPerBatch = root.path("questionsPerBatch").asInt();
        SessionData sessionData = new SessionData.Builder()
                .withKey(key)
                .forUser(userId)
                .takingScheme(schemeDomain)
                .withIndividualSequence(questionDomains)
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
            // uses custom BatchOutDtoDeserializer
            sessionData.setCurrentBatch(objectMapper.readValue(root.get("currentBatch").toString(), BatchOutDto.class));
            sessionData.setCurrentBatchTimeOut(LocalDateTime.parse(currentBatchTimeOut));
            sessionData.setCurrentBatchIssued(LocalDateTime.parse(currentBatchIssued));
        }

        ProgressData progressData = objectMapper.treeToValue(root.path("progressData"), ProgressData.class);

        Map<Long, MetaData> metaData = objectMapper.convertValue(root.path("metaData"), new TypeReference<HashMap<Long,MetaData>>() {});

        sessionData.setProgressData(progressData);
        sessionData.setMetaData(metaData);
        return sessionData;
    }

}
