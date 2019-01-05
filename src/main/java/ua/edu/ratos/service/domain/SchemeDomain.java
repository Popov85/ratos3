package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SchemeDomain {

    private Long schemeId;

    private String name;

    private StrategyDomain strategyDomain;

    private SettingsDomain settingsDomain;

    private ModeDomain modeDomain;

    private GradingDomain gradingDomain;
}
