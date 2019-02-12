package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SettingsFBOutDto {

    private Long settingsId;

    private String name;

    private short wordsLimit;

    private short symbolsLimit;

    private boolean isNumeric;

    private boolean isTypoAllowed;

    private boolean isCaseSensitive;

    private LanguageOutDto lang;

    private StaffMinOutDto staff;
}
