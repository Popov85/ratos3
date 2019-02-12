package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseMinOutDto;

@Slf4j
@Component
public class CourseMinDtoTransformer {

    public CourseMinOutDto toDto(@NonNull final Course entity) {
        return new CourseMinOutDto()
                .setCourseId(entity.getCourseId())
                .setName(entity.getName());
    }
}
