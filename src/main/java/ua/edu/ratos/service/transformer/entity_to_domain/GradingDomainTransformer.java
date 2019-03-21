package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.service.domain.GradingDomain;

@Slf4j
@Component
public class GradingDomainTransformer {

    public GradingDomain toDomain(@NonNull final Grading entity) {
        return new GradingDomain().setGradingId(entity.getGradingId()).setName(entity.getName());
    }

}
