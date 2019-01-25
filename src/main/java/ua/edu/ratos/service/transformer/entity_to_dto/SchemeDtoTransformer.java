package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.GroupOutDto;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SchemeDtoTransformer {

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

    @Autowired
    private GroupDtoTransformer groupDtoTransformer;

    @Autowired
    private SchemeThemeDtoTransformer schemeThemeDtoTransformer;


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
        Set<GroupOutDto> groups = entity.getGroups().stream().map(g -> groupDtoTransformer.toDto(g)).collect(Collectors.toSet());
        schemeOutDto.setGroups(groups);
        return schemeOutDto;
    }
}
