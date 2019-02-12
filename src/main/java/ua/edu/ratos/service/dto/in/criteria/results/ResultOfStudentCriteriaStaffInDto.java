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
public class ResultOfStudentCriteriaStaffInDto {

    private Long courseId;

    // Specify to fetch only LMS-courses
    private boolean lms;

    private Long schemeId;

    private LocalDateTime resultsFrom;

    private LocalDateTime resultsTo;

    // Default for strings is: "starts from"
    private String surname;

    // If true: surname "contains"
    // Much slower option, use carefully
    private boolean contains;

}
