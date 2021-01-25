package ua.edu.ratos.service.transformer;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {StrategyMapper.class, SettingsMapper.class, ModeMapper.class, OptionsMapper.class,
                GradingMapper.class, CourseMinMapper.class, StaffMinMapper.class, AccessMapper.class,
                GroupMinMapper.class, SchemeThemeMapper.class, SchemeGradingManagerService.class})
public abstract class SchemeMapper {

    @Autowired
    private SchemeGradingManagerService schemeGradingManagerService;

    public abstract SchemeOutDto toDto(Scheme entity);

    @Mapping(target = "strategyDomain", source = "strategy")
    @Mapping(target = "settingsDomain", source = "settings")
    @Mapping(target = "modeDomain", source = "mode")
    @Mapping(target = "optionsDomain", source = "options")
    @Mapping(target = "gradingDomain", source = "grading")
    public abstract SchemeDomain toDomain(Scheme entity);

    @AfterMapping
    void decorate(Scheme entity, @MappingTarget SchemeOutDto dto) {
        dto.setGradingDetails(schemeGradingManagerService
                .findDetails(entity.getSchemeId(), entity.getGrading().getGradingId()));
    }
}
