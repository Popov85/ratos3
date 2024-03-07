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

        JsonNode lmsId = root.path("lmsId");

        long userId = root.path("userId").asLong();
        SchemeDomain schemeDomain = objectMapper.treeToValue(root.path("schemeDomain"), SchemeDomain.class);
        List<QuestionDomain> sequence = objectMapper.readerFor(new TypeReference<List<QuestionDomain>>() {
        }).readValue(root.path("sequence"));
        SessionData sessionData;
        // Fixed bug (second check)
        if (!lmsId.isMissingNode() && !lmsId.isNull()) {
            log.debug("LMS session retrieved");
            sessionData = SessionData.createFromLMS(lmsId.longValue(), userId, schemeDomain, sequence);
        } else {
            log.debug("Non-LMS session retrieved");
            sessionData = SessionData.createNoLMS(userId, schemeDomain, sequence);
        }

        String sessionTimeout = root.path("sessionTimeout").asText();
        sessionData.setSessionTimeout(LocalDateTime.parse(sessionTimeout));

        JsonNode currentBatchTimeout = root.path("currentBatchTimeout");
        if (!currentBatchTimeout.isNull() && !currentBatchTimeout.isMissingNode()) {
            sessionData.setCurrentBatchTimeout(LocalDateTime.parse(currentBatchTimeout.asText()));
        } else {
            // currentBatchTimeout is erased when session is successfully completed
            sessionData.setCurrentBatchTimeout(null);
        }

        int currentIndex = root.path("currentIndex").asInt();
        sessionData.setCurrentIndex(currentIndex);

        JsonNode currentBatchIssued = root.path("currentBatchIssued");
        if (!currentBatchIssued.isNull() && !currentBatchIssued.isMissingNode()) {
            sessionData.setCurrentBatchIssued(LocalDateTime.parse(currentBatchIssued.asText()));
        } else {
            // currentBatchIssued is erased when session is successfully completed
            sessionData.setCurrentBatchIssued(null);
        }

        // currentBatch is erased when session is successfully completed
        JsonNode currentBatch = root.path("currentBatch");
        if (!currentBatch.isNull() && !currentBatch.isMissingNode()) {
            sessionData.setCurrentBatch(objectMapper.readValue(root.get("currentBatch").toString(), BatchOutDto.class));
        }

        ProgressData progressData = objectMapper.treeToValue(root.path("progressData"), ProgressData.class);
        sessionData.setProgressData(progressData);

        Map<Long, MetaData> metaData = objectMapper.convertValue(root.path("metaData"), new TypeReference<HashMap<Long, MetaData>>() {
        });
        sessionData.setMetaData(metaData);

        boolean isSuspended = root.path("suspended").asBoolean();
        sessionData.setSuspended(isSuspended);

        return sessionData;
    }

}
