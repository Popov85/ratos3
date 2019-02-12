package ua.edu.ratos.dao.repository.criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentCriteriaInDto {

    private Long courseId;

    private Long schemeId;

    private LocalDateTime resultsFrom;

    private LocalDateTime resultsTo;

    private String name;

    private String surname;
}
