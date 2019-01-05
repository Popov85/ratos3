package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.domain.PhraseDomain;

@Slf4j
@Component
public class PhraseDomainTransformer {

    @Autowired
    private ResourceDomainTransformer resourceDomainTransformer;

    public PhraseDomain toDomain(@NonNull final Phrase p) {
        return new PhraseDomain()
                .setPhraseId(p.getPhraseId())
                .setPhrase(p.getPhrase())
                .setResourceDomain((p.getResource().isPresent()) ? resourceDomainTransformer.toDomain(p.getResource().get()) : null);
    }
}
