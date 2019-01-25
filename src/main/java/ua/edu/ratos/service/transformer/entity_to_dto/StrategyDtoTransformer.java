package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Strategy;
import ua.edu.ratos.service.dto.out.StrategyOutDto;

@Slf4j
@Component
public class StrategyDtoTransformer {

    public StrategyOutDto toDto(@NonNull final Strategy entity) {
        return new StrategyOutDto()
                .setStrId(entity.getStrId())
                .setName(entity.getName())
                .setDescription(entity.getDescription());
    }
}
