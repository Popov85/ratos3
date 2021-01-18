package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.domain.UserDomain;
import ua.edu.ratos.service.dto.in.UserInDto;
import ua.edu.ratos.service.dto.out.UserOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {PasswordMapper.class})
public interface UserMapper {

    @Mapping(target = "role", expression = "java(entity.getRoles().iterator().next().getName())")
    UserOutDto toDto(User entity);

    @Mapping(target = "active", constant = "true")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(UserInDto dto);

    UserDomain toDomain(User entity);
}
