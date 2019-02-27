package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ThemeMapOutDto {
    private long themeId;
    private int totalByTheme;
    private Map<Long, LevelsOutDto> typeLevelMap = new HashMap<>();
}
