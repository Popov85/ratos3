package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;

@Component
public class QuestionMinDtoTransformer {

    private ThemeMinDtoTransformer themeMinDtoTransformer;

    private ResourceMinDtoTransformer resourceMinDtoTransformer;

    @Autowired
    public void setThemeMinDtoTransformer(ThemeMinDtoTransformer themeMinDtoTransformer) {
        this.themeMinDtoTransformer = themeMinDtoTransformer;
    }

    @Autowired
    public void setResourceMinDtoTransformer(ResourceMinDtoTransformer resourceMinDtoTransformer) {
        this.resourceMinDtoTransformer = resourceMinDtoTransformer;
    }

    public QuestionSessionMinOutDto toDto(@NonNull final Question entity) {
        return new QuestionSessionMinOutDto()
                .setQuestionId(entity.getQuestionId())
                .setQuestion(entity.getQuestion())
                .setLang(entity.getLang().getAbbreviation())
                .setLevel(entity.getLevel())
                .setType(entity.getType().getTypeId())
                .setHelpAvailable(entity.getHelp().isPresent())
                .setTheme(themeMinDtoTransformer.toDto(entity.getTheme()))
                .setResources((entity.getResources()!=null) ? resourceMinDtoTransformer.toDto(entity.getResources()) : null);
    }
}
