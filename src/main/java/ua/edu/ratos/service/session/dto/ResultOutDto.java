package ua.edu.ratos.service.session.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.ResultPerTheme;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOutDto {
    /**
     * Some key for edX integration score system purpose
     */
    private final String key;

    /**
     * Whether pass or not verdict, for edX scoring system
     */
    private final boolean passed;
    /**
     * Either [0-100], fractions are allowed
     * E.g. 75.4
     */
    private double percent;
    /**
     * Any number according to the selected grading scale, e.g. four-point [2, 3, 4, 5]
     * E.g. 4
     * Or e.g. two-point {0, 1}
     * E.g 1
     */
    private Number grade;

    /**
     * For compound schemes only, with themes > 1, irrespective to any settings
     */
    private List<ResultPerTheme> themeResults;

    /**
     * EXTENDED result on dto
     * For educational sessions , for student to see the correct answers afterwards;
     * For exam sessions leave it EMPTY;
     */
    private List<ResultPerQuestionOutDto> questionResults;

    public ResultOutDto(String key, boolean passed) {
        this.key = key;
        this.passed = passed;
    }
}
