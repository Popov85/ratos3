package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;

@Slf4j
@Component
public class SchemeShortDtoTransformer {

    private StrategyDtoTransformer strategyDtoTransformer;

    private SettingsDtoTransformer settingsDtoTransformer;

    private ModeDtoTransformer modeDtoTransformer;

    private GradingDtoTransformer gradingDtoTransformer;

    private CourseDtoTransformer courseDtoTransformer;

    private StaffMinDtoTransformer staffDtoTransformer;

    private AccessDtoTransformer accessDtoTransformer;

    @Autowired
    public void setStrategyDtoTransformer(StrategyDtoTransformer strategyDtoTransformer) {
        this.strategyDtoTransformer = strategyDtoTransformer;
    }

    @Autowired
    public void setSettingsDtoTransformer(SettingsDtoTransformer settingsDtoTransformer) {
        this.settingsDtoTransformer = settingsDtoTransformer;
    }

    @Autowired
    public void setModeDtoTransformer(ModeDtoTransformer modeDtoTransformer) {
        this.modeDtoTransformer = modeDtoTransformer;
    }

    @Autowired
    public void setGradingDtoTransformer(GradingDtoTransformer gradingDtoTransformer) {
        this.gradingDtoTransformer = gradingDtoTransformer;
    }
    @Autowired
    public void setCourseDtoTransformer(CourseDtoTransformer courseDtoTransformer) {
        this.courseDtoTransformer = courseDtoTransformer;
    }
    @Autowired
    public void setStaffDtoTransformer(StaffMinDtoTransformer staffDtoTransformer) {
        this.staffDtoTransformer = staffDtoTransformer;
    }

    @Autowired
    public void setAccessDtoTransformer(AccessDtoTransformer accessDtoTransformer) {
        this.accessDtoTransformer = accessDtoTransformer;
    }

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
                .setGrading(gradingDtoTransformer.toDto(entity.getGrading()))
                .setCourse(courseDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()))
                .setThemes(entity.getThemes().size())
                .setGroups(entity.getGroups().size());
    }
}
