package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.service.dto.out.StudOutDto;

import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {UserMapper.class, ClassMinMapper.class, FacultyMinMapper.class, OrganisationMinMapper.class})
public interface StudMapper {

    StudOutDto toDto(Student entity);

    Set<StudOutDto> toDto(Set<Student> entities);
}
