package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.out.question.*;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuestionDtoTransformer {

    private AnswerDtoTransformer answerDtoTransformer;

    private HelpDtoTransformer helpDtoTransformer;

    private ResourceDtoTransformer resourceDtoTransformer;

    private LanguageDtoTransformer languageDtoTransformer;

    private QuestionTypeDtoTransformer questionTypeDtoTransformer;

    private ThemeMinDtoTransformer themeMinDtoTransformer;

    @Autowired
    public void setAnswerDtoTransformer(AnswerDtoTransformer answerDtoTransformer) {
        this.answerDtoTransformer = answerDtoTransformer;
    }

    @Autowired
    public void setHelpDtoTransformer(HelpDtoTransformer helpDtoTransformer) {
        this.helpDtoTransformer = helpDtoTransformer;
    }

    @Autowired
    public void setResourceDtoTransformer(ResourceDtoTransformer resourceDtoTransformer) {
        this.resourceDtoTransformer = resourceDtoTransformer;
    }

    @Autowired
    public void setLanguageDtoTransformer(LanguageDtoTransformer languageDtoTransformer) {
        this.languageDtoTransformer = languageDtoTransformer;
    }

    @Autowired
    public void setQuestionTypeDtoTransformer(QuestionTypeDtoTransformer questionTypeDtoTransformer) {
        this.questionTypeDtoTransformer = questionTypeDtoTransformer;
    }

    @Autowired
    public void setThemeMinDtoTransformer(ThemeMinDtoTransformer themeMinDtoTransformer) {
        this.themeMinDtoTransformer = themeMinDtoTransformer;
    }

    public QuestionFBMQOutDto toDto(@NonNull final QuestionFBMQ entity) {
        QuestionFBMQOutDto dto = new QuestionFBMQOutDto();
        mapDto(entity, dto);
        entity.getAnswers().forEach(a -> dto.addAnswer(answerDtoTransformer.toDto(a)));
        return dto;
    }

    public QuestionFBSQOutDto toDto(@NonNull final QuestionFBSQ entity) {
        QuestionFBSQOutDto dto = new QuestionFBSQOutDto();
        mapDto(entity, dto);
        dto.addAnswer(answerDtoTransformer.toDto(entity.getAnswer()));
        return dto;
    }

    public QuestionMCQOutDto toDto(@NonNull final QuestionMCQ entity) {
        QuestionMCQOutDto dto = new QuestionMCQOutDto();
        mapDto(entity, dto);
        entity.getAnswers().forEach(a-> dto.addAnswer(answerDtoTransformer.toDto(a)));
        return dto;
    }

    public QuestionMQOutDto toDto(@NonNull final QuestionMQ entity) {
        QuestionMQOutDto dto = new QuestionMQOutDto();
        mapDto(entity, dto);
        entity.getAnswers().forEach(a -> dto.addAnswer(answerDtoTransformer.toDto(a)));
        return dto;
    }

    public QuestionSQOutDto toDto(@NonNull final QuestionSQ entity) {
        QuestionSQOutDto dto = new QuestionSQOutDto();
        mapDto(entity, dto);
        entity.getAnswers().forEach(a -> dto.addAnswer(answerDtoTransformer.toDto(a)));
        return dto;
    }

    public QuestionMinOutDto toDto(@NonNull final Question entity) {
        QuestionMinOutDto dto = new QuestionMinOutDto();
        mapDto(entity, dto);
        return dto;
    }


    private void mapDto(@NonNull final Question entity, @NonNull final QuestionOutDto dto) {
        dto.setQuestionId(entity.getQuestionId());
        dto.setQuestion(entity.getQuestion());
        dto.setLevel(entity.getLevel());
        dto.setRequired(entity.isRequired());
        dto.setLang(languageDtoTransformer.toDto(entity.getLang()));
        dto.setType(questionTypeDtoTransformer.toDto(entity.getType()));
        dto.setTheme(themeMinDtoTransformer.toDto(entity.getTheme()));
        dto.setHelp((entity.getHelp().isPresent()) ? helpDtoTransformer.toDto(entity.getHelp().get()) : null);
        if (entity.getResources()!=null) {
            dto.setResources(entity.getResources().stream().map(resourceDtoTransformer::toDto).collect(Collectors.toSet()));
        } else {
            dto.setResources(Collections.emptySet());
        }
    }
    
}
