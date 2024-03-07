package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SchemeThemeOutDto {

    private Long schemeThemeId;

    private Long themeId;

    private String theme;

    private int order;

    private Set<SchemeThemeSettingsOutDto> settings;

}
