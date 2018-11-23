package ua.edu.ratos.service.session.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Mode {
    private Long modeId;

    private String name;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean resultDetails;

    private boolean pauseable;

    private boolean preservable;

    private boolean reportable;

    private boolean starrable;
}
