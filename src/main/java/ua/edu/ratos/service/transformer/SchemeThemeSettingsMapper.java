package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import ua.edu.ratos.service.dto.out.SchemeThemeSettingsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {QuestionTypeMapper.class, SchemeThemeMapper.class})
public interface SchemeThemeSettingsMapper {

    @Mapping(target = "schemeThemeId", source = "entity.schemeTheme.schemeThemeId")
    @Mapping(target = "questionTypeId", source = "entity.type.typeId")
    @Mapping(target = "type", source = "entity.type.abbreviation")
    SchemeThemeSettingsOutDto toDto(SchemeThemeSettings entity);

    @Mapping(target = "schemeTheme", source = "schemeThemeId")
    @Mapping(target = "type", source = "questionTypeId")
    SchemeThemeSettings toEntity(SchemeThemeSettingsInDto dto);
}
