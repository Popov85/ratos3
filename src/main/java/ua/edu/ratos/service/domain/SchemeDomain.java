package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SchemeDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long schemeId;

    private String name;

    private StrategyDomain strategyDomain;

    private SettingsDomain settingsDomain;

    private ModeDomain modeDomain;

    private OptionsDomain optionsDomain;

    private GradingDomain gradingDomain;
}
