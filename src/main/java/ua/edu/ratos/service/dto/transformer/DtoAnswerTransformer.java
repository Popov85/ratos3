package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.dao.entity.question.QuestionFillBlankMultiple;
import ua.edu.ratos.dao.entity.question.QuestionMatcher;
import ua.edu.ratos.dao.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.dao.entity.question.QuestionSequence;
import ua.edu.ratos.service.dto.entity.*;
import javax.persistence.EntityManager;


@Component
public class DtoAnswerTransformer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMultipleChoice fromDto(@NonNull AnswerMCQInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMultipleChoice fromDto(Long answerId, @NonNull AnswerMCQInDto dto) {
        AnswerMultipleChoice answer = modelMapper.map(dto, AnswerMultipleChoice.class);
        if (dto.getResourceId()!=0) {
            // No need to clear() first
            answer.addResource(em.find(Resource.class, dto.getResourceId()));
        } else {
            answer.getResources().clear();
        }
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionMultipleChoice.class, dto.getQuestionId()));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFillBlankSingle fromDto(Long answerId, @NonNull AnswerFBSQInDto dto) {
        if ( dto.getPhrasesIds().isEmpty() || dto.getPhrasesIds().size()<1)
            throw new RuntimeException("Answer fbsq does not make sense without any accepted phrases!");
        AnswerFillBlankSingle answer = modelMapper.map(dto, AnswerFillBlankSingle.class);
        answer.setAnswerId(answerId);
        answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFillBlankMultiple fromDto(@NonNull AnswerFBMQInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFillBlankMultiple fromDto(Long answerId, @NonNull AnswerFBMQInDto dto) {
        if (dto.getPhrasesIds().isEmpty())
            throw new RuntimeException("Answer fbmq does not make sense without any accepted phrases!");
        AnswerFillBlankMultiple answer = modelMapper.map(dto, AnswerFillBlankMultiple.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionFillBlankMultiple.class, dto.getQuestionId()));
        answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMatcher fromDto(@NonNull AnswerMQInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMatcher fromDto(Long answerId, @NonNull AnswerMQInDto dto) {
        AnswerMatcher answer = modelMapper.map(dto, AnswerMatcher.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionMatcher.class, dto.getQuestionId()));
        answer.setLeftPhrase(em.getReference(Phrase.class, dto.getLeftPhraseId()));
        answer.setRightPhrase(em.getReference(Phrase.class, dto.getRightPhraseId()));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSequence fromDto(@NonNull AnswerSQInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSequence fromDto(Long answerId, @NonNull AnswerSQInDto dto) {
        AnswerSequence answer = modelMapper.map(dto, AnswerSequence.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionSequence.class, dto.getQuestionId()));
        answer.setPhrase(em.getReference(Phrase.class, dto.getPhraseId()));
        return answer;
    }

}
