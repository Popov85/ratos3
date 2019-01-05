package ua.edu.ratos.service.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SettingsDomain {
    private Long setId;

    private String name;

    private int secondsPerQuestion;

    // Specifies, whether or not to limit a single question in time, default - do not limit
    private boolean strictControlTimePerQuestion;

    // Negative short is used for "all questions per sheet" option
    private short questionsPerSheet;

    private short daysKeepResultDetails;

    private float level2Coefficient;

    private float level3Coefficient;

    private boolean displayPercent;

    private boolean displayMark;

    private boolean displayThemeResults;

}