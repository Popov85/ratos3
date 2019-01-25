package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SchemeThemeSettingsOutDto {

    private Long schemeThemeSettingsId;

    private Long schemeThemeId;

    private Long typeId;

    private String type;

    private short level1;

    private short level2;

    private short level3;
}
