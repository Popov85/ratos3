package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.in.ThemeInDto;

public interface ThemeTransformer {

    Theme toEntity(ThemeInDto dto);

    Theme toEntity(Theme entity, ThemeInDto dto);
}
