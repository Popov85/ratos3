package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.question.QuestionDomain;

import java.util.*;

@Getter
@Setter
@ToString(exclude = {"themeResults", "questionsMap", "responsesEvaluated"})
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultDomain {

    private UserDomain user;

    private SchemeDomain scheme;

    /**
     * Either [0-100], fractions are allowed, e.g. 75.4
     */
    private double percent;
    /**
     * Whether or not the test is passed:
     * scored the min according to scheme's grading system
     */
    private boolean passed;

    /**
     * Any number according to the selected grading scale,
     * e.g. four-point [2, 3, 4, 5], e.g. 4, or e.g. two-point {0, 1}, e.g 1
     */
    private double grade;

    private boolean timeOuted;

    private boolean cancelled;

    private long timeSpent;

    private List<ResultPerTheme> themeResults = new ArrayList<>();

    // for questionResult optional calculations (for DTO)
    private Map<Long, QuestionDomain> questionsMap = new HashMap<>();

    private List<ResponseEvaluated> responsesEvaluated = new ArrayList<>();

    //----------------------------------------------------Optional------------------------------------------------------

    /**
     * For LMS sessions only
     */
    private Long lmsId;

    public Optional<Long> getLmsId() {
        return Optional.ofNullable(lmsId);
    }

    /**
     * True, if gamification points are gained
     */
    private Integer points;

    public Optional<Integer> getPoints() {
        return Optional.ofNullable(points);
    }

    /**
     * Decide if user was smart enough to gain any non-zero points for this learning session
     * @see ua.edu.ratos.service.ResultService#save(ResultDomain result)
     * @return true, for this result a user was awarded non-zero points, false otherwise
     */
    public boolean hasPoints() {
        return (getPoints().isPresent() && getPoints().get() > 0);
    }
}
