package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.service.dto.out.ThemeOutDto;

@Slf4j
@Component
public class ThemeDtoTransformer {

    @Autowired
    private CourseDtoTransformer courseDtoTransformer;

    @Autowired
    private StaffMinDtoTransformer staffDtoTransformer;

    @Autowired
    private AccessDtoTransformer accessDtoTransformer;

    public ThemeOutDto toDto(@NonNull final Theme entity) {
        return new ThemeOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCourse(courseDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()));
    }

}
