package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.transformer.*;

@Deprecated
@Slf4j
@Component
public class SchemeDomainTransformer {

    private ModeMapper modeMapper;

    private StrategyMapper strategyMapper;

    private GradingMapper gradingMapper;

    private SettingsMapper settingsMapper;

    private OptionsMapper optionsMapper;

    @Autowired
    public void setModeDomainTransformer(ModeMapper modeMapper) {
        this.modeMapper = modeMapper;
    }

    @Autowired
    public void setStrategyDomainTransformer(StrategyMapper strategyMapper) {
        this.strategyMapper = strategyMapper;
    }

    @Autowired
    public void setGradingDomainTransformer(GradingMapper gradingMapper) {
        this.gradingMapper = gradingMapper;
    }

    @Autowired
    public void setSettingsDomainTransformer(SettingsMapper settingsMapper) {
        this.settingsMapper = settingsMapper;
    }

    @Autowired
    public void setOptionsDomainTransformer(OptionsMapper optionsMapper) {
        this.optionsMapper = optionsMapper;
    }

    public SchemeDomain toDomain(@NonNull final Scheme entity) {
        return new SchemeDomain()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setModeDomain(modeMapper.toDomain(entity.getMode()))
                .setStrategyDomain(strategyMapper.toDomain(entity.getStrategy()))
                .setGradingDomain(gradingMapper.toDomain(entity.getGrading()))
                .setSettingsDomain(settingsMapper.toDomain(entity.getSettings()))
                .setOptionsDomain(optionsMapper.toDomain(entity.getOptions()));
    }
}
