package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.SessionPreserved;
import ua.edu.ratos.service.dto.out.SessionPreservedOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SessionPreservedMapper {

    @Mapping(target = "uuid", source = "entity.key")
    @Mapping(target = "scheme", source = "entity.scheme.name")
    SessionPreservedOutDto toDto(SessionPreserved entity);
}
