package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;

import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@Slf4j
@Component
public class ResourceMinDtoTransformer {

    public ResourceMinOutDto toDto(@NonNull final Resource entity) {
        return new ResourceMinOutDto()
                .setResourceId(entity.getResourceId())
                .setLink(entity.getLink());
    }

    public Set<ResourceMinOutDto> toDto(@NonNull final Set<Resource> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
