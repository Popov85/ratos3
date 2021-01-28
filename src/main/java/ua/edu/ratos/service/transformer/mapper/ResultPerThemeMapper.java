package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.service.domain.ResultPerTheme;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {ThemeMapper.class})
public interface ResultPerThemeMapper {

    ResultPerThemeOutDto toDto(ResultPerTheme domain);
}
