package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseOutDto;

@Slf4j
@Component
public class CourseDtoTransformer{

    private AccessDtoTransformer accessDtoTransformer;

    private StaffMinDtoTransformer staffMinDtoTransformer;

    @Autowired
    public void setAccessDtoTransformer(AccessDtoTransformer accessDtoTransformer) {
        this.accessDtoTransformer = accessDtoTransformer;
    }

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    public CourseOutDto toDto(@NonNull final Course entity) {
        return new CourseOutDto()
                .setCourseId(entity.getCourseId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setAccess(accessDtoTransformer.toDto(entity.getAccess()))
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
