package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.transformer.ResourceMapper;

@Deprecated
@Slf4j
@Component
public class PhraseDomainTransformer {

    private ResourceMapper resourceMapper;

    @Autowired
    public void setResourceDomainTransformer(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public PhraseDomain toDomain(@NonNull final Phrase p) {
        return new PhraseDomain()
                .setPhraseId(p.getPhraseId())
                .setPhrase(p.getPhrase())
                .setResourceDomain((p.getResource().isPresent()) ? resourceMapper.toDomain(p.getResource().get()) : null);
    }
}
