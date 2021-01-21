package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {AccessMapper.class, StaffMinMapper.class, LMSMinMapper.class})
public interface CourseMapper {

    CourseOutDto toDto(Course entity);
}
