package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Strategy;
import ua.edu.ratos.service.domain.StrategyDomain;

@Deprecated
@Slf4j
@Component
public class StrategyDomainTransformer {

    public StrategyDomain toDomain(@NonNull final Strategy entity) {
        return new StrategyDomain().setStrId(entity.getStrId()).setName(entity.getName());
    }
}
