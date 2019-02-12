package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.out.SchemeMinOutDto;

@Slf4j
@Component
public class SchemeMinDtoTransformer {

    public SchemeMinOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeMinOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName());
    }
}
