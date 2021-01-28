package ua.edu.ratos.service.transformer.mapper;


import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForReportOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.utils.DataFormatter;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {DepartmentMapper.class, SchemeWithCourseMinMapper.class, StudMinMapper.class})
public abstract class ResultOfStudentForReportMapper {

    @Autowired
    private GameService gameService;

    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "percent", ignore = true)
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "LMS", expression = "java(entity.getLms().isPresent())")
    public abstract ResultOfStudentForReportOutDto toDto(ResultOfStudent entity);

    @AfterMapping
    void decorate(ResultOfStudent entity, @MappingTarget ResultOfStudentForReportOutDto dto) {
        dto.setGrade(DataFormatter.getPrettyDouble(entity.getGrade()));
        dto.setPercent(DataFormatter.getPrettyDouble(entity.getPercent()));
        dto.setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()) : null);
    }
}
