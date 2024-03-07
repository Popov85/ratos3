package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {SchemeThemeSettingsMapper.class, ReferenceMapper.class})
public interface SchemeThemeMapper {

    @Mapping(target = "theme", source = "entity.theme.name")
    SchemeThemeOutDto toDto(SchemeTheme entity);

    SchemeTheme toEntity(Long id);
}
