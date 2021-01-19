package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.service.domain.ResourceDomain;

@Deprecated
@Slf4j
@Component
public class ResourceDomainTransformer {

    public ResourceDomain toDomain(@NonNull final Resource entity) {
        return new ResourceDomain()
                .setResourceId(entity.getResourceId())
                .setDescription(entity.getDescription())
                .setLink(entity.getLink())
                .setType(entity.getType())
                .setWidth(entity.getWidth())
                .setHeight(entity.getHeight());
    }
}
