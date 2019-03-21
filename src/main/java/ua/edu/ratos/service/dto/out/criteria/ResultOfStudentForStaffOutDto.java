package ua.edu.ratos.service.dto.out.criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.CourseMinOutDto;
import ua.edu.ratos.service.dto.out.SchemeMinOutDto;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentForStaffOutDto {

    private Long resultId;

    private CourseMinOutDto course;

    private SchemeMinOutDto scheme;

    private StudMinOutDto student;

    private double percent;

    private double grade;

    private boolean passed;

    private LocalDateTime sessionEnded;

    private long sessionLasted;

    private boolean timeOuted;

    private boolean cancelled;

    private boolean isLMS;

}
