package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Role;
import ua.edu.ratos.service.dto.out.RoleOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleOutDto toDto(Role role);
}
