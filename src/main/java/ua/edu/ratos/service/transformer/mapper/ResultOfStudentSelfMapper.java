package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentSelfOutDto;
import ua.edu.ratos.service.session.GameService;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {DepartmentMinMapper.class, SchemeWithCourseMinMapper.class})
public abstract class ResultOfStudentSelfMapper {

    @Autowired
    private GameService gameService;

    @Mapping(target = "LMS", expression = "java(entity.getLms().isPresent())")
    @Mapping(target = "points", ignore = true)
    public abstract ResultOfStudentSelfOutDto toDto(ResultOfStudent entity);

    @AfterMapping
    void decorate(ResultOfStudent entity, @MappingTarget ResultOfStudentSelfOutDto dto) {
        dto.setPoints(gameService.getPoints(entity.getPercent()));
    }
}
