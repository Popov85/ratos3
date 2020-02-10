package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.repository.QuestionTypeRepository;
import ua.edu.ratos.service.dto.in.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Slf4j
@Component
@AllArgsConstructor
public class DtoQuestionTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final DtoAnswerTransformer transformer;

    private final QuestionTypeRepository questionTypeRepository;


    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMCQ toEntity(@NonNull final QuestionMCQInDto dto) {
        QuestionMCQ question = new QuestionMCQ();
        mapDto(dto, question, "MCQ");
        dto.getAnswers().forEach(a -> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFBSQ toEntity(@NonNull final QuestionFBSQInDto dto) {
        check(dto.getAnswer().getPhrasesIds(), 1, "This question must contain at least one accepted phrase");
        QuestionFBSQ question = new QuestionFBSQ();
        mapDto(dto, question, "FBSQ");
        AnswerFBSQInDto answerDto = dto.getAnswer();
        question.addAnswer(transformer.toEntity(answerDto));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFBMQ toEntity(@NonNull final QuestionFBMQInDto dto) {
        check(dto.getAnswers(), 1, "This question must contain at least 1 answer");
        QuestionFBMQ question = new QuestionFBMQ();
        mapDto(dto, question, "FBMQ");
        dto.getAnswers().forEach(a -> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMQ toEntity(@NonNull final QuestionMQInDto dto) {
        check(dto.getAnswers(), 2, "This question must contain at least 2 answers");
        QuestionMQ question = new QuestionMQ();
        mapDto(dto, question, "MQ");
        dto.getAnswers().forEach(a -> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSQ toEntity(@NonNull final QuestionSQInDto dto) {
        check(dto.getAnswers(), 3, "This question must contain at least 3 answers");
        QuestionSQ question = new QuestionSQ();
        mapDto(dto, question, "SQ");
        dto.getAnswers().forEach(a -> question.addAnswer(transformer.toEntity(a)));
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mapDto(@NonNull final QuestionInDto dto, @NonNull final Question question, @NonNull final String questionType) {
        question.setQuestionId(dto.getQuestionId());
        question.setQuestion(dto.getQuestion());
        question.setLevel(dto.getLevel());
        question.setTheme(em.getReference(Theme.class, dto.getThemeId()));
        question.setType(questionTypeRepository.findTypeByAbbreviation(questionType)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find QuestionType by its abbreviation")));
        question.setRequired(dto.isRequired());
        if (dto.getHelpId().isPresent()) {
            question.addHelp(em.find(Help.class, dto.getHelpId().get()));
        } else {
            question.clearHelps();
        }
        if (dto.getResourceId().isPresent()) {
            question.addResource(em.find(Resource.class, dto.getResourceId().get()));
        } else {
            question.clearResources();
        }
    }

    // TODO: consider move this functionality to the dedicated validators!
    private <T> void check(Collection<T> collection, int threshold, String message) {
        if (collection.isEmpty() || collection.size() < threshold)
            throw new IllegalStateException(message);
    }

}
