package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.dto.out.SchemeThemeSettingsOutDto;

@Deprecated
@Component
public class SchemeThemeSettingsDtoTransformer {

    public SchemeThemeSettingsOutDto toDto(@NonNull final SchemeThemeSettings entity) {
        return new SchemeThemeSettingsOutDto()
                .setSchemeThemeSettingsId(entity.getSchemeThemeSettingsId())
                .setSchemeThemeId(entity.getSchemeTheme().getSchemeThemeId())
                .setQuestionTypeId(entity.getType().getTypeId())
                .setType(entity.getType().getAbbreviation())
                .setLevel1(entity.getLevel1())
                .setLevel2(entity.getLevel2())
                .setLevel3(entity.getLevel3());
    }
}
