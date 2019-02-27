package ua.edu.ratos.service.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class LMSCourseInDto extends CourseInDto {

    @Positive(message = "{dto.fk.required}")
    private long lmsId;
}
