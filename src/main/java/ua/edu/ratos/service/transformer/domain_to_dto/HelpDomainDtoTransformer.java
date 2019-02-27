package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.HelpDomain;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;

@Component
public class HelpDomainDtoTransformer {

    private ResourceDomainDtoTransformer resourceDomainDtoTransformer;

    @Autowired
    public void setResourceDomainDtoTransformer(ResourceDomainDtoTransformer resourceDomainDtoTransformer) {
        this.resourceDomainDtoTransformer = resourceDomainDtoTransformer;
    }

    public HelpMinOutDto toDto(@NonNull final HelpDomain domain) {
        return new HelpMinOutDto()
                .setHelpId(domain.getHelpId())
                .setName(domain.getName())
                .setHelp(domain.getHelp())
                .setResource(((domain.getResourceDomain().isPresent()) ?
                        resourceDomainDtoTransformer.toDto(domain.getResourceDomain().get()) : null));
    }
}
