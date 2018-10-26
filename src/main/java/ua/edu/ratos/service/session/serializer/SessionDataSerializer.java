package ua.edu.ratos.service.session.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.session.domain.SessionData;

@Component
public class SessionDataSerializer {

    @Autowired
    private ObjectMapper objectMapper;

    public String serialize(@NonNull final SessionData sessionData) {
        try {
            return objectMapper.writeValueAsString(sessionData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize SessionData", e);
        }
    }

}
