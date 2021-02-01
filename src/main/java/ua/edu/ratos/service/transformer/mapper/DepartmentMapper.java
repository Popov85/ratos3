package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.service.dto.in.DepartmentInDto;
import ua.edu.ratos.service.dto.out.DepartmentOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {FacultyMapper.class, ReferenceMapper.class})
public interface DepartmentMapper {

    DepartmentOutDto toDto(Department entity);

    @Mapping(target = "faculty", source = "facId")
    Department toEntity(DepartmentInDto dto);

    Department toEntity(Long id);
}
