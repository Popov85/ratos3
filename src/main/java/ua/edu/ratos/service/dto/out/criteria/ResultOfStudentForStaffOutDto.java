package ua.edu.ratos.service.dto.out.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.CourseMinOutDto;
import ua.edu.ratos.service.dto.out.SchemeMinOutDto;
import ua.edu.ratos.service.dto.out.SchemeWithCourseMinOutDto;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentForStaffOutDto {

    private Long resultId;

    private SchemeWithCourseMinOutDto scheme; // Course included

    private StudMinOutDto student;

    private String percent;

    private String grade;

    private boolean passed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime sessionEnded;

    private long sessionLasted;

    private boolean timeouted;

    private boolean cancelled;

    private Integer points; // Nullable

    private boolean isLMS;

}
