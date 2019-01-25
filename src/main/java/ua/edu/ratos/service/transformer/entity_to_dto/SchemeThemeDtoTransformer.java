package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import ua.edu.ratos.service.dto.out.SchemeThemeSettingsOutDto;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SchemeThemeDtoTransformer {

    @Autowired
    private SchemeThemeSettingsDtoTransformer schemeThemeSettingsDtoTransformer;

    public SchemeThemeOutDto toDto(@NonNull final SchemeTheme entity) {
        SchemeThemeOutDto dto = new SchemeThemeOutDto().setSchemeThemeId(entity.getSchemeThemeId())
                .setThemeId(entity.getTheme().getThemeId())
                .setTheme(entity.getTheme().getName())
                .setOrder(entity.getOrder());
        Set<SchemeThemeSettingsOutDto> settings = entity.getSettings()
                .stream()
                .map(s -> schemeThemeSettingsDtoTransformer.toDto(s))
                .collect(Collectors.toSet());
        dto.setSettings(settings);
        return dto;
    }
}
