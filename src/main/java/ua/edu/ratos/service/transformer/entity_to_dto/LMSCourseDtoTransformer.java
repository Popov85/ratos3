package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.service.dto.out.LMSCourseOutDto;

@Slf4j
@Component
public class LMSCourseDtoTransformer {

    private AccessDtoTransformer accessDtoTransformer;

    private StaffMinDtoTransformer staffMinDtoTransformer;

    private LMSMinDtoTransformer lmsMinDtoTransformer;

    @Autowired
    public void setAccessDtoTransformer(AccessDtoTransformer accessDtoTransformer) {
        this.accessDtoTransformer = accessDtoTransformer;
    }

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    @Autowired
    public void setLmsMinDtoTransformer(LMSMinDtoTransformer lmsMinDtoTransformer) {
        this.lmsMinDtoTransformer = lmsMinDtoTransformer;
    }

    public LMSCourseOutDto toDto(@NonNull final LMSCourse entity) {
        LMSCourseOutDto courseOutDto = new LMSCourseOutDto();
        courseOutDto.setCourseId(entity.getCourseId());
        courseOutDto.setName(entity.getCourse().getName());
        courseOutDto.setCreated(entity.getCourse().getCreated());
        courseOutDto.setAccess(accessDtoTransformer.toDto(entity.getCourse().getAccess()));
        courseOutDto.setStaff(staffMinDtoTransformer.toDto(entity.getCourse().getStaff()));
        courseOutDto.setLms(lmsMinDtoTransformer.toDto(entity.getLms()));
        return courseOutDto;
    }
}
