package ua.edu.ratos.service.dto.session.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.domain.response.Response;
import java.util.Map;

@Getter
@ToString
public class BatchInDto {

    // Key - questionId, Response - the corresponding response provided by a user
    private final Map<Long, Response> responses;

    @JsonCreator
    public BatchInDto(@JsonProperty("responses") Map<Long, Response> responses) {
        this.responses = responses;
    }
}
