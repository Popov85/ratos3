package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.SessionPreserved;
import ua.edu.ratos.service.dto.out.SessionPreservedOutDto;

@Deprecated
@Component
public class SessionPreservedDtoTransformer {

    public SessionPreservedOutDto toDto(@NonNull final SessionPreserved entity) {
        return new SessionPreservedOutDto()
                .setUuid(entity.getKey())
                .setScheme(entity.getScheme().getName())
                .setWhenPreserved(entity.getWhenPreserved())
                .setProgress(entity.getProgress());
    }
}
