package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.StaffOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {UserMapper.class, PositionMapper.class, DepartmentMapper.class})
public interface StaffMapper {

    StaffOutDto toDto(Staff entity);
}
