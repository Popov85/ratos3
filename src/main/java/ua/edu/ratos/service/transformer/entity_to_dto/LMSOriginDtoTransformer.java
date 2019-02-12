package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMSOrigin;
import ua.edu.ratos.service.dto.out.LMSOriginOutDto;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LMSOriginDtoTransformer {

    public LMSOriginOutDto toDto(@NonNull final LMSOrigin entity) {
        return new LMSOriginOutDto()
                .setOriginId(entity.getOriginId())
                .setLink(entity.getLink());
    }

    public Set<LMSOriginOutDto> toDto(@NonNull final Set<LMSOrigin> entity) {
        return entity.stream().map(this::toDto).collect(Collectors.toSet());
    }

}
