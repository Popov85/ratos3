package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ThemeMapper {

    @Mapping(target = "access", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "course", ignore = true)
    ThemeOutDto toDto(Theme entity);
}
