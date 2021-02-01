package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.dto.out.ClassOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {FacultyMapper.class})
public interface ClassMapper {

    ClassOutDto toDto(Clazz entity);
}
