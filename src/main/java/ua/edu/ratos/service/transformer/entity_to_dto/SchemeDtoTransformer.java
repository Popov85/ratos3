package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.GroupMinOutDto;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;
import ua.edu.ratos.service.transformer.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@Slf4j
@Component
@AllArgsConstructor
public class SchemeDtoTransformer {

    private final StrategyMapper strategyMapper;

    private final SettingsMapper settingsMapper;

    private final ModeMapper modeMapper;

    private final GradingMapper gradingMapper;

    private final CourseMinMapper courseMinMapper;

    private final StaffMinMapper staffMinMapper;

    private final AccessMapper accessMapper;

    private final OptionsMapper optionsMapper;

    private final GroupMinMapper groupMinMapper;

    private final SchemeThemeMapper schemeThemeMapper;

    private final SchemeGradingManagerService schemeGradingManagerService;

    @Transactional(propagation = Propagation.MANDATORY)
    public SchemeOutDto toDto(@NonNull final Scheme entity) {
        SchemeOutDto schemeOutDto = new SchemeOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setLmsOnly(entity.isLmsOnly())
                .setActive(entity.isActive())
                .setCreated(entity.getCreated())
                .setStrategy(strategyMapper.toDto(entity.getStrategy()))
                .setSettings(settingsMapper.toDto(entity.getSettings()))
                .setOptions(optionsMapper.toDto(entity.getOptions()))
                .setMode(modeMapper.toDto(entity.getMode()))
                .setGrading(gradingMapper.toDto(entity.getGrading()))
                .setGradingDetails(schemeGradingManagerService
                        .findDetails(entity.getSchemeId(), entity.getGrading().getGradingId()))
                .setCourse(courseMinMapper.toDto(entity.getCourse()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setAccess(accessMapper.toDto(entity.getAccess()));

        List<SchemeThemeOutDto> themes = entity.getThemes().stream().map(t -> schemeThemeMapper.toDto(t)).collect(Collectors.toList());
        schemeOutDto.setThemes(themes);
        Set<GroupMinOutDto> groups = entity.getGroups().stream().map(g -> groupMinMapper.toDto(g)).collect(Collectors.toSet());
        schemeOutDto.setGroups(groups);
        return schemeOutDto;
    }
}
