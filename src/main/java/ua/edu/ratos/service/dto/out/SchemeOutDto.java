package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SchemeOutDto {

    private Long schemeId;

    private String name;

    private StrategyOutDto strategy;

    private SettingsOutDto settings;

    private ModeOutDto mode;

    private GradingOutDto grading;

    private Object gradingDetails;

    private CourseOutDto course;

    private StaffMinOutDto staff;

    private LocalDateTime created;

    private boolean active;

    private boolean lmsOnly;

    private AccessOutDto access;

    private Set<GroupOutDto> groups = new HashSet<>();

    private List<SchemeThemeOutDto> themes = new ArrayList<>();
}
