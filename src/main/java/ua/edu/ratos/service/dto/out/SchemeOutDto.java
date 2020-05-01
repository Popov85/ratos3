package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO of Scheme entity for single-object usage only!
 * See also (for shortened version of DTO for usage in collections!)
 * {@link ua.edu.ratos.service.dto.out.SchemeShortOutDto SchemeShortOutDto}
 */
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

    private OptionsOutDto options;

    private GradingOutDto grading;

    private Object gradingDetails;

    private CourseMinOutDto course;

    private StaffMinOutDto staff;

    private AccessOutDto access;

    private boolean active;

    private boolean lmsOnly;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime created;

    @JsonProperty("themesCount")
    private int countThemes() {
        return themes.size();
    }

    @JsonProperty("groupsCount")
    private int countGroups() {
        return groups.size();
    }

    private Set<GroupMinOutDto> groups = new HashSet<>();

    private List<SchemeThemeOutDto> themes = new ArrayList<>();
}
