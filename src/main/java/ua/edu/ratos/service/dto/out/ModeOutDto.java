package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuppressWarnings("SpellCheckingInspection")
public class ModeOutDto {

    private Long modeId;

    private String name;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean preservable;

    private boolean reportable;

    private boolean pauseable;

    private boolean starrable;

    private boolean isDefault;

    private StaffMinOutDto staff;

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return isDefault;
    }
}
