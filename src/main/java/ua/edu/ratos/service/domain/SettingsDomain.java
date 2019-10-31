package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SettingsDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long setId;

    private String name;

    private int secondsPerQuestion;

    // Specifies, whether or not to limit a single question in time, default - do not limit
    private boolean strictControlTimePerQuestion;

    // Negative short is used for "all questions per batch" option
    private short questionsPerSheet;

    private short daysKeepResultDetails;

    private float level2Coefficient;

    private float level3Coefficient;

}
