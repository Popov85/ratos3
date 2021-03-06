package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuppressWarnings("SpellCheckingInspection")
public class ModeMinOutDto {

    private Long modeId;

    private String name;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean resultDetails;

    private boolean preservable;

    private boolean pauseable;

    private boolean reportable;

    private boolean starrable;
}
