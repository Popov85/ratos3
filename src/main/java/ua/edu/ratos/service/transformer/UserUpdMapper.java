package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.in.UserUpdInDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdMapper {

    User toEntity(@MappingTarget User user, UserUpdInDto dto);
}
