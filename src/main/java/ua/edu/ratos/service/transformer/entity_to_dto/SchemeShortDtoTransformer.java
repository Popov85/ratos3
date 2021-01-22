package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;
import ua.edu.ratos.service.transformer.*;

@Deprecated
@Component
@AllArgsConstructor
public class SchemeShortDtoTransformer {

    private final StrategyMapper strategyMapper;

    private final SettingsMapper settingsMapper;

    private final ModeMapper modeMapper;

    private final OptionsMapper optionsMapper;

    private final GradingMapper gradingMapper;

    private final CourseMinMapper courseMinMapper;

    private final StaffMinMapper staffMinMapper;

    private final AccessMapper accessMapper;


    public SchemeShortOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeShortOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setLmsOnly(entity.isLmsOnly())
                .setActive(entity.isActive())
                .setCreated(entity.getCreated())
                .setStrategy(strategyMapper.toDto(entity.getStrategy()))
                .setSettings(settingsMapper.toDto(entity.getSettings()))
                .setMode(modeMapper.toDto(entity.getMode()))
                .setOptions(optionsMapper.toDto(entity.getOptions()))
                .setGrading(gradingMapper.toDto(entity.getGrading()))
                .setCourse(courseMinMapper.toDto(entity.getCourse()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setAccess(accessMapper.toDto(entity.getAccess()))
                .setThemesCount(entity.getThemes().size())
                .setGroupsCount(entity.getGroups().size());
    }
}
