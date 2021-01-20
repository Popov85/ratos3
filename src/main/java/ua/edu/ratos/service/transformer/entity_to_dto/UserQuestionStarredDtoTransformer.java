package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.transformer.QuestionMapper;
import ua.edu.ratos.service.transformer.QuestionMinMapper;

@Component
public class UserQuestionStarredDtoTransformer {

    private QuestionMapper questionMapper;

    private QuestionMinMapper questionMinMapper;

    @Autowired
    public void setQuestionDomainTransformer(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setQuestionMinDtoTransformer(QuestionMinMapper questionMinMapper) {
        this.questionMinMapper = questionMinMapper;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSessionMinOutDto toDto(@NonNull final UserQuestionStarred entity) {
        Question question = entity.getQuestion();
        return questionMinMapper.toDto(question);
    }

    @Transactional(propagation = Propagation.MANDATORY)
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
