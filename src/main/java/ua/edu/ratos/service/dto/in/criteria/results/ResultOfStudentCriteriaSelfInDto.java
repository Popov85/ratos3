package ua.edu.ratos.service.dto.in.criteria.results;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentCriteriaSelfInDto {

    private Long depId;

    private Long courseId;

    private boolean lms;

    private Long schemeId;

    private LocalDateTime resultsFrom;

    private LocalDateTime resultsTo;

}
