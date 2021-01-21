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
import ua.edu.ratos.service.transformer.AccessMapper;
import ua.edu.ratos.service.transformer.CourseMinMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class SchemeDtoTransformer {

    private final StrategyDtoTransformer strategyDtoTransformer;

    private final SettingsDtoTransformer settingsDtoTransformer;

    private final ModeDtoTransformer modeDtoTransformer;

    private final GradingDtoTransformer gradingDtoTransformer;

    private final CourseMinMapper courseMinMapper;

    private final StaffMinMapper staffMinMapper;

    private final AccessMapper accessMapper;

    private final OptionsDtoTransformer optionsDtoTransformer;

    private final GroupMinDtoTransformer groupMinDtoTransformer;

    private final SchemeThemeDtoTransformer schemeThemeDtoTransformer;

    private final SchemeGradingManagerService schemeGradingManagerService;

    @Transactional(propagation = Propagation.MANDATORY)
    public SchemeOutDto toDto(@NonNull final Scheme entity) {
        SchemeOutDto schemeOutDto = new SchemeOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setLmsOnly(entity.isLmsOnly())
                .setActive(entity.isActive())
                .setCreated(entity.getCreated())
                .setStrategy(strategyDtoTransformer.toDto(entity.getStrategy()))
                .setSettings(settingsDtoTransformer.toDto(entity.getSettings()))
                .setOptions(optionsDtoTransformer.toDto(entity.getOptions()))
                .setMode(modeDtoTransformer.toDto(entity.getMode()))
                .setGrading(gradingDtoTransformer.toDto(entity.getGrading()))
                .setGradingDetails(schemeGradingManagerService
                        .findDetails(entity.getSchemeId(), entity.getGrading().getGradingId()))
                .setCourse(courseMinMapper.toDto(entity.getCourse()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setAccess(accessMapper.toDto(entity.getAccess()));

        List<SchemeThemeOutDto> themes = entity.getThemes().stream().map(t -> schemeThemeDtoTransformer.toDto(t)).collect(Collectors.toList());
        schemeOutDto.setThemes(themes);
        Set<GroupMinOutDto> groups = entity.getGroups().stream().map(g -> groupMinDtoTransformer.toDto(g)).collect(Collectors.toSet());
        schemeOutDto.setGroups(groups);
        return schemeOutDto;
    }
}
