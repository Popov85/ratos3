package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.ResultOfStudentTheme;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses={ThemeMinMapper.class})
public interface ResultOfStudentPerThemeMapper {

    ResultPerThemeOutDto toDto(ResultOfStudentTheme entity);
}
