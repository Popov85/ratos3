package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.in.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Component
public class DtoQuestionTransformer {

    @PersistenceContext
    private EntityManager em;

    private DtoAnswerTransformer transformer;

    @Autowired
    public void setTransformer(DtoAnswerTransformer transformer) {
        this.transformer = transformer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMCQ toEntity(@NonNull final  QuestionMCQInDto dto) {
        check(dto.getAnswers(), 2, "This question must contain at least 2 answers");
        QuestionMCQ question = new QuestionMCQ();
        mapDto(dto, question);
        dto.getAnswers().forEach(a-> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFBSQ toEntity(@NonNull final  QuestionFBSQInDto dto) {
        check(dto.getAnswer().getPhrasesIds(), 1, "This question must contain at least one accepted phrase");
        QuestionFBSQ question = new QuestionFBSQ();
        mapDto(dto, question);
        AnswerFBSQInDto answerDto = dto.getAnswer();
        question.addAnswer(transformer.toEntity(answerDto));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFBMQ toEntity(@NonNull final  QuestionFBMQInDto dto) {
        check(dto.getAnswers(), 1, "This question must contain at least 1 answer");
        QuestionFBMQ question = new QuestionFBMQ();
        mapDto(dto, question);
        dto.getAnswers().forEach(a-> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMQ toEntity(@NonNull final QuestionMQInDto dto) {
        check(dto.getAnswers(), 2, "This question must contain at least 2 answers");
        QuestionMQ question = new QuestionMQ();
        mapDto(dto, question);
        dto.getAnswers().forEach(a-> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSQ toEntity(@NonNull final QuestionSQInDto dto) {
        check(dto.getAnswers(), 3, "This question must contain at least 3 answers");
        QuestionSQ question = new QuestionSQ();
        mapDto(dto, question);
        dto.getAnswers().forEach(a-> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mapDto(@NonNull final QuestionInDto dto, @NonNull final Question question) {
        question.setQuestionId(dto.getQuestionId());
        question.setQuestion(dto.getQuestion());
        question.setLevel(dto.getLevel());
        question.setTheme(em.getReference(Theme.class, dto.getThemeId()));
        question.setType(em.getReference(QuestionType.class, dto.getQuestionTypeId()));
        question.setLang(em.getReference(Language.class, dto.getLangId()));
        if (dto.getHelpId()!=0) {
            question.addHelp(em.find(Help.class, dto.getHelpId()));
        } else {
            question.clearHelps();
        }
        if (dto.getResourcesIds()!=null) {
            Set<Resource> resources = question.getResources();
            dto.getResourcesIds().forEach(id->resources.add(em.find(Resource.class, id)));
        } else {
            question.clearResources();
        }
    }

    private <T> void check(Collection<T> collection, int threshold, String message) {
        if (collection.isEmpty() || collection.size()<threshold)
            throw new IllegalStateException(message);
    }

}
