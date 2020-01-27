package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CourseOutDto {

    private Long courseId;

    private String name;

    private AccessOutDto access;

    private StaffMinOutDto staff;

    // Nullable, if null - non-LMS course
    private LMSMinOutDto lms;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime created;
}
