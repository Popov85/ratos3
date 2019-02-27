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

    private CourseDtoTransformer courseDtoTransformer;

    private StaffMinDtoTransformer staffDtoTransformer;

    private AccessDtoTransformer accessDtoTransformer;

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

    public ThemeOutDto toDto(@NonNull final Theme entity) {
        return new ThemeOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setCourse(courseDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()));
    }

}
