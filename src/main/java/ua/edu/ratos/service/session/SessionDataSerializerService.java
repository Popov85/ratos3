package ua.edu.ratos.service.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;
import java.io.IOException;

@Service
public class SessionDataSerializerService {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Serialize SessionData obj into JSON so that to store it in DB
     * Usage:
     *   1) Standard store of each session data after save during some time (1 day by default);
     *   2) Preservation mechanism (for educational session only).
     *
     * @param sessionData
     * @return JSON representation of SessionData obj
     */
    public String serialize(@NonNull final SessionData sessionData) {
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(sessionData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize SessionData", e);
        }
    }

    /**
     * Creates SessionData obj out of its JSON representation
     * @param sessionData
     * @return deserialized SessionData obj
     */
    public SessionData deserialize(@NonNull final String sessionData) {
        try {
            return new ObjectMapper().readValue(sessionData, SessionData.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize SessionData", e);
        }
    }
}
