package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseMinLMSOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses={LMSMinMapper.class})
public interface CourseMinLMSMapper {

    CourseMinLMSOutDto toDto(Course entity);
}
