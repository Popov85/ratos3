package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.out.question.*;

@Slf4j
@Component
@AllArgsConstructor
public class QuestionDtoTransformer {

    private final AnswerDtoTransformer answerDtoTransformer;

    private final HelpMinDtoTransformer helpDtoTransformer;

    private final ResourceDtoTransformer resourceDtoTransformer;

    private final QuestionTypeDtoTransformer questionTypeDtoTransformer;


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
        dto.setSingle(entity.isSingle());
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
        dto.setType(questionTypeDtoTransformer.toDto(entity.getType()));
        dto.setHelp(entity.getHelp().isPresent() ? helpDtoTransformer.toDto(entity.getHelp().get()) : null);
        dto.setResource(entity.getResource().isPresent() ? resourceDtoTransformer.toDto(entity.getResource().get()): null);
    }
    
}
