package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.ResourceDomain;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;

@Deprecated
@Component
public class ResourceDomainDtoTransformer {

    public ResourceMinOutDto toDto(@NonNull final ResourceDomain domain) {
        return new ResourceMinOutDto()
                .setResourceId(domain.getResourceId())
                .setLink(domain.getLink());
    }

}
