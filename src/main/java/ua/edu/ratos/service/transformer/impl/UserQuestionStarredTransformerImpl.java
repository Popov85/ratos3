package ua.edu.ratos.service.transformer.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.transformer.QuestionMapper;
import ua.edu.ratos.service.transformer.QuestionMinMapper;
import ua.edu.ratos.service.transformer.UserQuestionStarredTransformer;

@Component
@AllArgsConstructor
public class UserQuestionStarredTransformerImpl implements UserQuestionStarredTransformer {

    private final QuestionMapper questionMapper;

    private final QuestionMinMapper questionMinMapper;

    @Override
    public QuestionSessionMinOutDto toDto(@NonNull final UserQuestionStarred entity) {
        Question question = entity.getQuestion();
        return questionMinMapper.toDto(question);
    }

    @Override
    public QuestionSessionOutDto toDtoExt(@NonNull final UserQuestionStarred entity) {
        Question question = entity.getQuestion();
        String abbr = question.getType().getAbbreviation();
        if ("MCQ".equals(abbr)) return questionMapper.toDomain((QuestionMCQ) question).toDto();
        if ("FBSQ".equals(abbr)) return questionMapper.toDomain((QuestionFBSQ) question).toDto();
        if ("FBMQ".equals(abbr)) return questionMapper.toDomain((QuestionFBMQ) question).toDto();
        if ("MQ".equals(abbr)) return questionMapper.toDomain((QuestionMQ) question).toDto();
        if ("SQ".equals(abbr)) return questionMapper.toDomain((QuestionSQ) question).toDto();
        throw new UnsupportedOperationException("Unsupported question questionType");
    }
}
