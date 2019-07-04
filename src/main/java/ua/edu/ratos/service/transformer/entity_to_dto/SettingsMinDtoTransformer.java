package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.dto.out.SettingsMinOutDto;
import ua.edu.ratos.service.dto.out.SettingsOutDto;

@Slf4j
@Component
public class SettingsMinDtoTransformer {

    public SettingsMinOutDto toDto(@NonNull final Settings entity) {
        return new SettingsMinOutDto()
                .setSetId(entity.getSetId())
                .setName(entity.getName())
                .setDisplayMark(entity.isDisplayMark())
                .setDisplayPercent(entity.isDisplayPercent())
                .setDisplayThemeResults(entity.isDisplayThemeResults())
                .setDisplayQuestionResults(entity.isDisplayQuestionResults())
                .setQuestionsPerSheet(entity.getQuestionsPerSheet())
                .setSecondsPerQuestion(entity.getSecondsPerQuestion())
                .setStrictControlTimePerQuestion(entity.isStrictControlTimePerQuestion());
    }
}
