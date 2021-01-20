package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.service.dto.out.answer.*;
import ua.edu.ratos.service.transformer.PhraseMapper;
import ua.edu.ratos.service.transformer.ResourceMinMapper;
import ua.edu.ratos.service.transformer.SettingsFBMapper;

import java.util.stream.Collectors;

@Deprecated
@Slf4j
@Component
@AllArgsConstructor
public class AnswerDtoTransformer {

    private final SettingsFBMapper settingsFBMapper;

    private final ResourceMinMapper resourceMinMapper;

    private final PhraseMapper phraseMapper;


    public AnswerFBMQOutDto toDto(@NonNull final AnswerFBMQ entity) {
        return new AnswerFBMQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setPhrase(entity.getPhrase())
                .setOccurrence(entity.getOccurrence())
                .setSettings(settingsFBMapper.toDto(entity.getSettings()))
                .setAcceptedPhrases(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseMapper::toDto)
                        .collect(Collectors.toSet()));
    }

    public AnswerFBSQOutDto toDto(@NonNull final AnswerFBSQ entity) {
        return new AnswerFBSQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setSettings(settingsFBMapper.toDto(entity.getSettings()))
                .setAcceptedPhrases(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseMapper::toDto)
                        .collect(Collectors.toSet()));
    }

    public AnswerMCQOutDto toDto(@NonNull final AnswerMCQ entity) {
        return new AnswerMCQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setAnswer(entity.getAnswer())
                .setPercent(entity.getPercent())
                .setRequired(entity.isRequired())
                .setResource((entity.getResource().isPresent()) ? resourceMinMapper.toDto(entity.getResource().get()) : null);
    }

    public AnswerMQOutDto toDto(@NonNull final AnswerMQ entity) {
        return new AnswerMQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setLeftPhrase(phraseMapper.toDto(entity.getLeftPhrase()))
                .setRightPhrase(phraseMapper.toDto(entity.getRightPhrase()));
    }

    public AnswerSQOutDto toDto(@NonNull final AnswerSQ entity) {
        return new AnswerSQOutDto()
                .setAnswerId(entity.getAnswerId())
                .setPhrase(phraseMapper.toDto(entity.getPhrase()))
                .setOrder(entity.getOrder());
    }
}
