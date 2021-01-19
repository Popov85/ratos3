package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.domain.HelpDomain;
import ua.edu.ratos.service.transformer.ResourceMapper;

@Deprecated
@Slf4j
@Component
public class HelpDomainTransformer {

    private ResourceMapper resourceMapper;

    @Autowired
    public void setResourceDomainTransformer(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public HelpDomain toDomain(@NonNull final Help entity) {
       return new HelpDomain()
                .setHelpId(entity.getHelpId())
                .setName(entity.getName())
                .setHelp(entity.getHelp())
                .setResourceDomain((entity.getResource().isPresent()) ? resourceMapper.toDomain(entity.getResource().get()) : null);
    }

}
