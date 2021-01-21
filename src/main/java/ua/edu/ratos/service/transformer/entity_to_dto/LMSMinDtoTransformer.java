package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.out.LMSMinOutDto;

@Deprecated
@Component
public class LMSMinDtoTransformer {

    public LMSMinOutDto toDto(@NonNull final LMS entity) {
        return new LMSMinOutDto()
                .setLmsId(entity.getLmsId())
                .setName(entity.getName());
    }
}
