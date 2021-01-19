package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.service.domain.ResourceDomain;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;
import ua.edu.ratos.service.dto.out.ResourceOutDto;

import java.util.Optional;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface ResourceMapper {

    ResourceOutDto toDto(Resource entity);

    ResourceDomain toDomain(Resource entity);

    ResourceMinOutDto toDto(ResourceDomain domain);

    default ResourceOutDto toDto(Optional<Resource> resource) {
        if (!resource.isPresent()) return null;
        return toDto(resource.get());
    }

    default ResourceMinOutDto toMinDto(Optional<ResourceDomain> resource) {
        if (!resource.isPresent()) return null;
        return toDto(resource.get());
    }

    default ResourceDomain toDomain(Optional<Resource> resource) {
        if (!resource.isPresent()) return null;
        return toDomain(resource.get());
    }
}
