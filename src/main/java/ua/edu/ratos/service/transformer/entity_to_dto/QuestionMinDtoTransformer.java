package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.transformer.ResourceMinMapper;
import ua.edu.ratos.service.transformer.ThemeMinMapper;

@Deprecated
@Component
@AllArgsConstructor
public class QuestionMinDtoTransformer {

    private final ThemeMinMapper themeMinMapper;

    private final ResourceMinMapper resourceMinMapper;

    public QuestionSessionMinOutDto toDto(@NonNull final Question entity) {
        return new QuestionSessionMinOutDto()
                .setQuestionId(entity.getQuestionId())
                .setQuestion(entity.getQuestion())
                .setLevel(entity.getLevel())
                .setType(entity.getType().getTypeId())
                .setHelpAvailable(entity.getHelp().isPresent())
                .setTheme(themeMinMapper.toDto(entity.getTheme()))
                .setResources((entity.getResources()!=null) ? resourceMinMapper.toDto(entity.getResources()) : null);
    }
}
