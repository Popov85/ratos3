package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseOutDto;
import ua.edu.ratos.service.transformer.AccessMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Slf4j
@Component
@AllArgsConstructor
public class CourseDtoTransformer{

    private final AccessMapper accessMapper;

    private final StaffMinMapper staffMinMapper;

    private final LMSMinDtoTransformer lmsMinDtoTransformer;


    public CourseOutDto toDto(@NonNull final Course entity) {
        return new CourseOutDto()
                .setCourseId(entity.getCourseId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setAccess(accessMapper.toDto(entity.getAccess()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setActive(!entity.isDeleted())
                .setLms(entity.getLmsCourse()!=null
                        ? lmsMinDtoTransformer.toDto(entity.getLmsCourse().getLms())
                        : null);
    }
}
