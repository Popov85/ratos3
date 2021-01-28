package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.dto.out.ThemeMinOutDto;
import ua.edu.ratos.service.dto.out.ThemeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses={AccessMapper.class, CourseMinLMSMapper.class, StaffMinMapper.class})
public interface ThemeMapper {

    ThemeOutDto toDto(Theme entity);

    ThemeMinOutDto toDto(ThemeDomain domain);
}
