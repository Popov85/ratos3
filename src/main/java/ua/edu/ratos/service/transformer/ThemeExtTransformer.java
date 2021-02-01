package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeExtOutDto;

public interface ThemeExtTransformer {

    ThemeExtOutDto toDto(Theme entity);
}
