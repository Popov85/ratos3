package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SettingsMinOutDto {

    private Long setId;

    private String name;

    private int secondsPerQuestion;

    private short questionsPerSheet;

    private boolean displayPercent;

    private boolean displayMark;

    private boolean displayThemeResults;

    private boolean displayQuestionResults;

    private boolean strictControlTimePerQuestion;

}
