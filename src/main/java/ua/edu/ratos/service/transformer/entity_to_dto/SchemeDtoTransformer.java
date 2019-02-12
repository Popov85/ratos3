package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.GroupMinOutDto;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SchemeDtoTransformer {

    private StrategyDtoTransformer strategyDtoTransformer;

    private SettingsDtoTransformer settingsDtoTransformer;

    private ModeDtoTransformer modeDtoTransformer;

    private GradingDtoTransformer gradingDtoTransformer;

    private CourseDtoTransformer courseDtoTransformer;

    private StaffMinDtoTransformer staffDtoTransformer;

    private AccessDtoTransformer accessDtoTransformer;

    private GroupMinDtoTransformer groupMinDtoTransformer;

    private SchemeThemeDtoTransformer schemeThemeDtoTransformer;


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

    @Autowired
    public void setGroupMinDtoTransformer(GroupMinDtoTransformer groupMinDtoTransformer) {
        this.groupMinDtoTransformer = groupMinDtoTransformer;
    }

    @Autowired
    public void setSchemeThemeDtoTransformer(SchemeThemeDtoTransformer schemeThemeDtoTransformer) {
        this.schemeThemeDtoTransformer = schemeThemeDtoTransformer;
    }

    public SchemeOutDto toDto(@NonNull final Scheme entity) {
        SchemeOutDto schemeOutDto = new SchemeOutDto()
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
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()));

        List<SchemeThemeOutDto> themes = entity.getThemes().stream().map(t -> schemeThemeDtoTransformer.toDto(t)).collect(Collectors.toList());
        schemeOutDto.setThemes(themes);
        Set<GroupMinOutDto> groups = entity.getGroups().stream().map(g -> groupMinDtoTransformer.toDto(g)).collect(Collectors.toSet());
        schemeOutDto.setGroups(groups);
        return schemeOutDto;
    }
}
