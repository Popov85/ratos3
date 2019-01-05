package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.SchemeDomain;

@Slf4j
@Component
public class SchemeDomainTransformer {

    @Autowired
    private ModeDomainTransformer modeDomainTransformer;

    @Autowired
    private StrategyDomainTransformer strategyDomainTransformer;

    @Autowired
    private GradingDomainTransformer gradingDomainTransformer;

    @Autowired
    private SettingsDomainTransformer settingsDomainTransformer;

    public SchemeDomain toDomain(@NonNull final Scheme entity) {
        return new SchemeDomain()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setModeDomain(modeDomainTransformer.toDomain(entity.getMode()))
                .setStrategyDomain(strategyDomainTransformer.toDomain(entity.getStrategy()))
                .setGradingDomain(gradingDomainTransformer.toDomain(entity.getGrading()))
                .setSettingsDomain(settingsDomainTransformer.toDomain(entity.getSettings()));
    }
}
