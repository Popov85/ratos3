package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.service.dto.out.GradingOutDto;

@Slf4j
@Component
public class GradingDtoTransformer {

    public GradingOutDto toDto(@NonNull final Grading entity) {
        return new GradingOutDto()
                .setGradingId(entity.getGradingId())
                .setName(entity.getName())
                .setDescription(entity.getDescription());
    }
}
