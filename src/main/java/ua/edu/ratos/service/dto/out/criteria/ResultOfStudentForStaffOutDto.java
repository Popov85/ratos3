package ua.edu.ratos.service.dto.out.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.SchemeWithCourseMinOutDto;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

import java.time.OffsetDateTime;
import java.util.List;

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

    private boolean details;

    private List<ResultPerThemeOutDto> themeResults;
}
