package ua.edu.ratos.service.dto.session;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.response.Response;
import ua.edu.ratos.web.converter.BatchInDeserializer;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@JsonDeserialize(using = BatchInDeserializer.class)
public class BatchIn {

    /**
     * Key - questionId, Response - the response provided by a user
     */
    private final Map<Long, Response> responses;

    /**
     * Key - questionId, OptionsDto - the optional attributes about this question provided by a user
     */
    private final Map<Long, OptionsDto> options;
}
