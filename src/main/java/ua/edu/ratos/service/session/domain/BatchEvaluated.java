package ua.edu.ratos.service.session.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.session.OptionsDto;

import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class BatchEvaluated {

    private final Map<Long, ResponseEvaluated> responsesEvaluated;

    private final Map<Long, OptionsDto> options;

    /**
     * TimeSpent is calculated approximately based on time between
     * a batch network round trip
     */
    private final long timeSpent;
}
