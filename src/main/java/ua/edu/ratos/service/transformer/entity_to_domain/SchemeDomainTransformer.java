package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.transformer.StrategyMapper;

@Slf4j
@Component
public class SchemeDomainTransformer {

    private ModeDomainTransformer modeDomainTransformer;

    private StrategyMapper strategyMapper;

    private GradingDomainTransformer gradingDomainTransformer;

    private SettingsDomainTransformer settingsDomainTransformer;

    private OptionsDomainTransformer optionsDomainTransformer;

    @Autowired
    public void setModeDomainTransformer(ModeDomainTransformer modeDomainTransformer) {
        this.modeDomainTransformer = modeDomainTransformer;
    }

    @Autowired
    public void setStrategyDomainTransformer(StrategyMapper strategyMapper) {
        this.strategyMapper = strategyMapper;
    }

    @Autowired
    public void setGradingDomainTransformer(GradingDomainTransformer gradingDomainTransformer) {
        this.gradingDomainTransformer = gradingDomainTransformer;
    }

    @Autowired
    public void setSettingsDomainTransformer(SettingsDomainTransformer settingsDomainTransformer) {
        this.settingsDomainTransformer = settingsDomainTransformer;
    }

    @Autowired
    public void setOptionsDomainTransformer(OptionsDomainTransformer optionsDomainTransformer) {
        this.optionsDomainTransformer = optionsDomainTransformer;
    }

    public SchemeDomain toDomain(@NonNull final Scheme entity) {
        return new SchemeDomain()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setModeDomain(modeDomainTransformer.toDomain(entity.getMode()))
                .setStrategyDomain(strategyMapper.toDomain(entity.getStrategy()))
                .setGradingDomain(gradingDomainTransformer.toDomain(entity.getGrading()))
                .setSettingsDomain(settingsDomainTransformer.toDomain(entity.getSettings()))
                .setOptionsDomain(optionsDomainTransformer.toDomain(entity.getOptions()));
    }
}
