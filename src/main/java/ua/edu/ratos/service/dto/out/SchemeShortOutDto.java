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
public class SchemeShortOutDto {

    private Long schemeId;

    private String name;

    private StrategyOutDto strategy;

    private SettingsOutDto settings;

    private ModeOutDto mode;

    private GradingOutDto grading;

    private CourseOutDto course;

    private StaffMinOutDto staff;

    private LocalDateTime created;

    private AccessOutDto access;

    private boolean active;

    private boolean lmsOnly;

    private int themes;

    private int groups;
}
