package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CourseMinLMSOutDto {

    private Long courseId;

    private String name;

    // Nullable, if null - non-LMS course
    private LMSMinOutDto lms;
}
