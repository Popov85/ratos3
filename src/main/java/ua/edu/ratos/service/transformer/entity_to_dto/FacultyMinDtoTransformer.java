package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;

@Deprecated
@Component
public class FacultyMinDtoTransformer {

    public FacultyMinOutDto toDto(@NonNull final Faculty entity) {
        return new FacultyMinOutDto()
                .setFacId(entity.getFacId())
                .setName(entity.getName());
    }
}
