package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LTIVersion;
import ua.edu.ratos.service.dto.out.LTIVersionOutDto;

@Deprecated
@Component
public class LTIVersionDtoTransformer {

    public LTIVersionOutDto toDto(@NonNull final LTIVersion entity) {
        return new LTIVersionOutDto()
                .setVersionId(entity.getVersionId())
                .setVersion(entity.getVersion());
    }
}
