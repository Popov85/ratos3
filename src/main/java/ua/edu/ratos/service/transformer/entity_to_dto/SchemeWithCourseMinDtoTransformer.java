package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeWithCourseMinOutDto;

@Slf4j
@Component
@AllArgsConstructor
public class SchemeWithCourseMinDtoTransformer {

    private final CourseMinDtoTransformer courseMinDtoTransformer;

    public SchemeWithCourseMinOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeWithCourseMinOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setCourse(courseMinDtoTransformer.toDto(entity.getCourse()));
    }
}
