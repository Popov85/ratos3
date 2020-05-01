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
public class SchemeShortOutDto {

    private Long schemeId;

    private String name;

    private StrategyOutDto strategy;

    private SettingsOutDto settings;

    private ModeOutDto mode;

    private OptionsOutDto options;

    private GradingOutDto grading;

    private CourseMinOutDto course;

    private StaffMinOutDto staff;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime created;

    private AccessOutDto access;

    private boolean active;

    private boolean lmsOnly;

    private int themesCount;

    private int groupsCount;
}
