package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;

@Component
@AllArgsConstructor
public class SchemeShortDtoTransformer {

    private final StrategyDtoTransformer strategyDtoTransformer;

    private final SettingsDtoTransformer settingsDtoTransformer;

    private final ModeDtoTransformer modeDtoTransformer;

    private final OptionsDtoTransformer optionsDtoTransformer;

    private final GradingDtoTransformer gradingDtoTransformer;

    private final CourseMinDtoTransformer courseMinDtoTransformer;

    private final StaffMinDtoTransformer staffDtoTransformer;

    private final AccessDtoTransformer accessDtoTransformer;


    public SchemeShortOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeShortOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setLmsOnly(entity.isLmsOnly())
                .setActive(entity.isActive())
                .setCreated(entity.getCreated())
                .setStrategy(strategyDtoTransformer.toDto(entity.getStrategy()))
                .setSettings(settingsDtoTransformer.toDto(entity.getSettings()))
                .setMode(modeDtoTransformer.toDto(entity.getMode()))
                .setOptions(optionsDtoTransformer.toDto(entity.getOptions()))
                .setGrading(gradingDtoTransformer.toDto(entity.getGrading()))
                .setCourse(courseMinDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()))
                .setThemesCount(entity.getThemes().size())
                .setGroupsCount(entity.getGroups().size());
    }
}
