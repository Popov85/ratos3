package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Position;
import ua.edu.ratos.service.dto.out.PositionOutDto;

@Component
public class PositionDtoTransformer {

    public PositionOutDto toDto(@NonNull final Position entity) {
        return new PositionOutDto().setPosId(entity.getPosId())
                .setName(entity.getName());
    }
}
