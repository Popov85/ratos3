package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.dto.out.SettingsMinOutDto;

@Slf4j
@Component
public class SettingsMinDtoTransformer {

    public SettingsMinOutDto toDto(@NonNull final Settings entity) {
        return new SettingsMinOutDto()
                .setSetId(entity.getSetId())
                .setName(entity.getName())
                .setQuestionsPerSheet(entity.getQuestionsPerSheet())
                .setSecondsPerQuestion(entity.getSecondsPerQuestion())
                .setStrictControlTimePerQuestion(entity.isStrictControlTimePerQuestion());
    }
}
