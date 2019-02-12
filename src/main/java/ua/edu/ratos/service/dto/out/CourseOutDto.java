package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CourseOutDto {

    private Long courseId;

    private String name;

    private LocalDateTime created;

    private AccessOutDto access;

    private StaffMinOutDto staff;
}
