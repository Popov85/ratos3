package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.utils.DataFormatter;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StudMinMapper.class, ResultOfStudentPerThemeMapper.class, SchemeWithCourseMinMapper.class})
public abstract class ResultOfStudentForStaffMapper {

    @Autowired
    private GameService gameService;

    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "percent", ignore = true)
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "themeResults", source = "entity.resultTheme")
    @Mapping(target = "LMS", expression = "java(entity.getLms().isPresent())")
    @Mapping(target = "details", expression = "java(entity.getResultDetails()!=null)")
    public abstract ResultOfStudentForStaffOutDto toDto(ResultOfStudent entity);

    @AfterMapping
    void decorate(ResultOfStudent entity, @MappingTarget ResultOfStudentForStaffOutDto dto) {
        dto.setGrade(DataFormatter.getPrettyDouble(entity.getGrade()));
        dto.setPercent(DataFormatter.getPrettyDouble(entity.getPercent()));
        dto.setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()) : null);
    }
}
