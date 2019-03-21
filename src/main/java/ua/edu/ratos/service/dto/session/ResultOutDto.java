package ua.edu.ratos.service.dto.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.ResultPerTheme;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultOutDto {

    // Basically: name&surname
    private final String user;

    // Scheme title
    private final String scheme;

    /**
     * Whether passed or not verdict.
     * (Front-end can highlight with green/red).
     * This is the only must have parameter.
     */
    private final boolean passed;

    @JsonCreator
    public ResultOutDto(@NonNull final @JsonProperty("user") String user,
                        @NonNull final @JsonProperty("scheme") String scheme,
                        @JsonProperty("passed") boolean passed) {
        this.passed = passed;
        this.user = user;
        this.scheme = scheme;
    }

    //-----------------------------------------------May be not included------------------------------------------------

    /**
     * Scored percent. Either [0-100], fractions are allowed, e.g. 75.4%.
     * If prohibited by settings, leave it null.
     */
    private Double percent;

    /**
     * Grade. Any number according to the selected grading scale,
     * e.g. four-point [2, 3, 4, 5], e.g. 4 or e.g. two-point {0, 1}, e.g 1.
     * If prohibited by settings, leave it null.
     */
    private Double grade;

    //------------------------------------------------------Optional----------------------------------------------------

    /**
     * Gamification points. Only if game mode is on and user gained enough percents of right answers from the first attempt.
     * Stick to null otherwise.
     */
    private Integer points;

    /**
     * Results per theme. For compound schemes mostly, with results for each theme included in scheme.
     * If prohibited by settings, leave it null.
     */
    private List<ResultPerTheme> themeResults;

    /**
     * Extended result on session. For educational sessions, for student to see the correct answers afterwards.
     * For exam kind of schemes leave it null.
     */
    private List<ResultPerQuestionOutDto> questionResults;
}
