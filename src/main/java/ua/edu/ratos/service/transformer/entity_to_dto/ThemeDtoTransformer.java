package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import ua.edu.ratos.service.transformer.AccessMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Slf4j
@Component
@AllArgsConstructor
public class ThemeDtoTransformer {

    private final CourseMinLMSDtoTransformer courseMinLMSDtoTransformer;

    private final StaffMinMapper staffMinMapper;

    private final AccessMapper accessMapper;


    public ThemeOutDto toDto(@NonNull final Theme entity) {
        return new ThemeOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setCourse(courseMinLMSDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setAccess(accessMapper.toDto(entity.getAccess()));
    }

}
