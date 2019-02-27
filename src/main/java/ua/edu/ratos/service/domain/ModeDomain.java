package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@SuppressWarnings("SpellCheckingInspection")
public class ModeDomain {
    private Long modeId;

    private String name;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean pauseable;

    private boolean preservable;

    private boolean reportable;

    private boolean starrable;
}
