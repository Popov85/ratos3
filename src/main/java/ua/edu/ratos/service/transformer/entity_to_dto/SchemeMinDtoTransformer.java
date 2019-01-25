package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeMinOutDto;

@Slf4j
@Component
public class SchemeMinDtoTransformer {

    @Autowired
    private StrategyDtoTransformer strategyDtoTransformer;

    @Autowired
    private SettingsDtoTransformer settingsDtoTransformer;

    @Autowired
    private ModeDtoTransformer modeDtoTransformer;

    @Autowired
    private GradingDtoTransformer gradingDtoTransformer;

    @Autowired
    private CourseDtoTransformer courseDtoTransformer;

    @Autowired
    private StaffMinDtoTransformer staffDtoTransformer;

    @Autowired
    private AccessDtoTransformer accessDtoTransformer;

    public SchemeMinOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeMinOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setLmsOnly(entity.isLmsOnly())
                .setActive(entity.isActive())
                .setCreated(entity.getCreated())
                .setStrategy(strategyDtoTransformer.toDto(entity.getStrategy()))
                .setSettings(settingsDtoTransformer.toDto(entity.getSettings()))
                .setMode(modeDtoTransformer.toDto(entity.getMode()))
                .setGrading(gradingDtoTransformer.toDto(entity.getGrading()))
                .setCourse(courseDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()))
                .setThemes(entity.getThemes().size())
                .setGroups(entity.getGroups().size());
    }
}
