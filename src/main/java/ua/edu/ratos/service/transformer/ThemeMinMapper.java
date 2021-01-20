package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ThemeMinMapper {

    ThemeMinOutDto toDto(Theme entity);
}
