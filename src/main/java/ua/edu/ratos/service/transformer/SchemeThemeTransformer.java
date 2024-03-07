package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;

public interface SchemeThemeTransformer {

    SchemeTheme toEntity(Long schemeId, SchemeThemeInDto dto);

    SchemeTheme toEntity(SchemeThemeInDto dto);
}
