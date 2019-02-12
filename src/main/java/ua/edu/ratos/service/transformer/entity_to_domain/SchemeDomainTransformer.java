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

    private ModeDomainTransformer modeDomainTransformer;

    private StrategyDomainTransformer strategyDomainTransformer;

    private GradingDomainTransformer gradingDomainTransformer;

    private SettingsDomainTransformer settingsDomainTransformer;

    @Autowired
    public void setModeDomainTransformer(ModeDomainTransformer modeDomainTransformer) {
        this.modeDomainTransformer = modeDomainTransformer;
    }

    @Autowired
    public void setStrategyDomainTransformer(StrategyDomainTransformer strategyDomainTransformer) {
        this.strategyDomainTransformer = strategyDomainTransformer;
    }

    @Autowired
    public void setGradingDomainTransformer(GradingDomainTransformer gradingDomainTransformer) {
        this.gradingDomainTransformer = gradingDomainTransformer;
    }

    @Autowired
    public void setSettingsDomainTransformer(SettingsDomainTransformer settingsDomainTransformer) {
        this.settingsDomainTransformer = settingsDomainTransformer;
    }

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
