package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.out.CourseMinLMSOutDto;

@Slf4j
@Component
@AllArgsConstructor
public class CourseMinLMSDtoTransformer {

    private final LMSMinDtoTransformer lmsMinDtoTransformer;

    public CourseMinLMSOutDto toDto(@NonNull final Course entity) {
        return new CourseMinLMSOutDto()
                .setCourseId(entity.getCourseId())
                .setName(entity.getName())
                .setLms(entity.getLmsCourse()!=null
                        ? lmsMinDtoTransformer.toDto(entity.getLmsCourse().getLms())
                        : null);
    }
}
