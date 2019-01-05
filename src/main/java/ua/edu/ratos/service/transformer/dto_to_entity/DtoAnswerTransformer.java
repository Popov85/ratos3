package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.dao.entity.question.QuestionFBMQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.entity.question.QuestionMQ;
import ua.edu.ratos.dao.entity.question.QuestionSQ;
import ua.edu.ratos.service.dto.in.*;
import javax.persistence.EntityManager;

@Component
public class DtoAnswerTransformer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMCQ toEntity(@NonNull AnswerMCQInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMCQ toEntity(Long answerId, @NonNull AnswerMCQInDto dto) {
        AnswerMCQ answer = modelMapper.map(dto, AnswerMCQ.class);
        if (dto.getResourceId()!=0) {
            // No need to clear() first
            answer.addResource(em.find(Resource.class, dto.getResourceId()));
        } else {
            answer.clearResources();
        }
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionMCQ.class, dto.getQuestionId()));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBSQ toEntity(Long answerId, @NonNull AnswerFBSQInDto dto) {
        if ( dto.getPhrasesIds().isEmpty() || dto.getPhrasesIds().size()<1)
            throw new RuntimeException("Answer fbsq does not make sense without any accepted phrases!");
        AnswerFBSQ answer = modelMapper.map(dto, AnswerFBSQ.class);
        answer.setAnswerId(answerId);
        answer.setSettings(em.getReference(SettingsFB.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBMQ toEntity(@NonNull AnswerFBMQInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBMQ toEntity(Long answerId, @NonNull AnswerFBMQInDto dto) {
        if (dto.getPhrasesIds().isEmpty())
            throw new RuntimeException("Answer fbmq does not make sense without any accepted phrases!");
        AnswerFBMQ answer = modelMapper.map(dto, AnswerFBMQ.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionFBMQ.class, dto.getQuestionId()));
        answer.setSettings(em.getReference(SettingsFB.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMQ toEntity(@NonNull AnswerMQInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMQ toEntity(Long answerId, @NonNull AnswerMQInDto dto) {
        AnswerMQ answer = modelMapper.map(dto, AnswerMQ.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionMQ.class, dto.getQuestionId()));
        answer.setLeftPhrase(em.getReference(Phrase.class, dto.getLeftPhraseId()));
        answer.setRightPhrase(em.getReference(Phrase.class, dto.getRightPhraseId()));
        return answer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSQ toEntity(@NonNull AnswerSQInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSQ toEntity(Long answerId, @NonNull AnswerSQInDto dto) {
        AnswerSQ answer = modelMapper.map(dto, AnswerSQ.class);
        answer.setAnswerId(answerId);
        if (dto.getQuestionId()!=null) answer.setQuestion(em.getReference(QuestionSQ.class, dto.getQuestionId()));
        answer.setPhrase(em.getReference(Phrase.class, dto.getPhraseId()));
        return answer;
    }

}
