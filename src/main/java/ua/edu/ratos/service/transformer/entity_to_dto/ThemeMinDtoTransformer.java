package ua.edu.ratos.service.transformer.entity_to_dto;

import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeMinOutDto;

@Deprecated
@Component
public class ThemeMinDtoTransformer {

    public ThemeMinOutDto toDto(Theme entity) {
        return new ThemeMinOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName());
    }
}
