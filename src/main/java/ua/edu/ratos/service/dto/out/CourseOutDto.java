package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CourseOutDto {

    private Long courseId;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime created;

    private AccessOutDto access;

    private StaffMinOutDto staff;

    private boolean active;

    private LMSMinOutDto lms; // Nullable, if null - non-LMS course
}
