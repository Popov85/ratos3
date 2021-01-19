package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.HelpDomain;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;
import ua.edu.ratos.service.transformer.ResourceMapper;

@Deprecated
@Component
public class HelpDomainDtoTransformer {

    private ResourceMapper resourceMapper;

    @Autowired
    public void setResourceDomainDtoTransformer(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public HelpMinOutDto toDto(@NonNull final HelpDomain domain) {
        return new HelpMinOutDto()
                .setHelpId(domain.getHelpId())
                .setName(domain.getName())
                .setHelp(domain.getHelp())
                .setResource(((domain.getResourceDomain().isPresent()) ?
                        resourceMapper.toDto(domain.getResourceDomain().get()) : null));
    }
}
