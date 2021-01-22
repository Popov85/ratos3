package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {StrategyMapper.class, SettingsMapper.class, ModeMapper.class, OptionsMapper.class,
                GradingMapper.class, CourseMinMapper.class, StaffMinMapper.class, AccessMapper.class})
public interface SchemeShortMapper {

    @Mapping(target = "themesCount", expression = "java(entity.getThemes() !=null ? entity.getThemes().size() : 0)")
    @Mapping(target = "groupsCount", expression = "java(entity.getGroups() !=null ? entity.getGroups().size() : 0)")
    SchemeShortOutDto toDto(Scheme entity);
}
