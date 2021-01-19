package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;

import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResourceMinMapper {

    ResourceMinOutDto toDto(Resource entity);

    Set<ResourceMinOutDto> toDto(Set<Resource> entities);

    default ResourceMinOutDto toDto(Optional<Resource> resource) {
        if (!resource.isPresent()) return null;
        return toDto(resource.get());
    }
}
