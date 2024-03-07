package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SchemeWithCourseMinOutDto {

    private Long schemeId;

    private String name;

    private CourseMinOutDto course;
}
