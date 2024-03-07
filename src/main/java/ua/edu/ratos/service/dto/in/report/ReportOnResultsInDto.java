package ua.edu.ratos.service.dto.in.report;

import lombok.Data;
import ua.edu.ratos.dao.repository.specs.SpecsFilter;

import java.util.HashMap;
import java.util.Map;

@Data
public class ReportOnResultsInDto {
    private Long organisation;
    private Long faculty;
    private Long department;
    /**
     * Keys: course, scheme, sessionEndedFrom, sessionEndedTo
     */
    Map<String, SpecsFilter> specs = new HashMap<>();
}
