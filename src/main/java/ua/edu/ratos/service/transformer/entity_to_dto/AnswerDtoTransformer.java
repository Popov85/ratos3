package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.service.dto.out.answer.*;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class AnswerDtoTransformer {

    private final SettingsFBDtoTransformer settingsFBDtoTransformer;

    private final ResourceMinDtoTransformer resourceMinDtoTransformer;

    private final PhraseDtoTransformer phraseDtoTransformer;


    public AnswerFBMQOutDto toDto(@NonNull final AnswerFBMQ entity) {
        return new AnswerFBMQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setPhrase(entity.getPhrase())
                .setOccurrence(entity.getOccurrence())
                .setSettings(settingsFBDtoTransformer.toDto(entity.getSettings()))
                .setAcceptedPhrases(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseDtoTransformer::toDto)
                        .collect(Collectors.toSet()));
    }

    public AnswerFBSQOutDto toDto(@NonNull final AnswerFBSQ entity) {
        return new AnswerFBSQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setSettings(settingsFBDtoTransformer.toDto(entity.getSettings()))
                .setAcceptedPhrases(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseDtoTransformer::toDto)
                        .collect(Collectors.toSet()));
    }

    public AnswerMCQOutDto toDto(@NonNull final AnswerMCQ entity) {
        return new AnswerMCQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setAnswer(entity.getAnswer())
                .setPercent(entity.getPercent())
                .setRequired(entity.isRequired())
                .setResource((entity.getResource().isPresent()) ? resourceMinDtoTransformer.toDto(entity.getResource().get()) : null);
    }

    public AnswerMQOutDto toDto(@NonNull final AnswerMQ entity) {
        return new AnswerMQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setLeftPhrase(phraseDtoTransformer.toDto(entity.getLeftPhrase()))
                .setRightPhrase(phraseDtoTransformer.toDto(entity.getRightPhrase()));
    }

    public AnswerSQOutDto toDto(@NonNull final AnswerSQ entity) {
        return new AnswerSQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setPhrase(phraseDtoTransformer.toDto(entity.getPhrase()))
                .setOrder(entity.getOrder());
    }
}
