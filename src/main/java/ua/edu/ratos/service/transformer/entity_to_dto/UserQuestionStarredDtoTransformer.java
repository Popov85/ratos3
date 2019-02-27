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
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;

@Component
public class UserQuestionStarredDtoTransformer {

    private QuestionDomainTransformer questionDomainTransformer;

    private QuestionMinDtoTransformer questionMinDtoTransformer;

    @Autowired
    public void setQuestionDomainTransformer(QuestionDomainTransformer questionDomainTransformer) {
        this.questionDomainTransformer = questionDomainTransformer;
    }

    @Autowired
    public void setQuestionMinDtoTransformer(QuestionMinDtoTransformer questionMinDtoTransformer) {
        this.questionMinDtoTransformer = questionMinDtoTransformer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSessionMinOutDto toDto(@NonNull final UserQuestionStarred entity) {
        Question question = entity.getQuestion();
        return questionMinDtoTransformer.toDto(question);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSessionOutDto toDtoExt(@NonNull final UserQuestionStarred entity) {
        Question question = entity.getQuestion();
        String abbr = question.getType().getAbbreviation();
        if ("MCQ".equals(abbr)) return questionDomainTransformer.toDomain((QuestionMCQ) question).toDto();
        if ("FBSQ".equals(abbr)) return questionDomainTransformer.toDomain((QuestionFBSQ) question).toDto();
        if ("FBMQ".equals(abbr)) return questionDomainTransformer.toDomain((QuestionFBMQ) question).toDto();
        if ("MQ".equals(abbr)) return questionDomainTransformer.toDomain((QuestionMQ) question).toDto();
        if ("SQ".equals(abbr)) return questionDomainTransformer.toDomain((QuestionSQ) question).toDto();
        throw new UnsupportedOperationException("Unsupported question questionType");
    }
}
