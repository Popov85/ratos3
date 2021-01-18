package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudMinOutDto;

import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {UserMinMapper.class, ClassMinMapper.class, FacultyMinMapper.class, OrganisationMinMapper.class})
public interface StudMinMapper {

    StudMinOutDto toDto(Student entity);

    Set<StudMinOutDto> toDto(Set<Student> entities);
}
