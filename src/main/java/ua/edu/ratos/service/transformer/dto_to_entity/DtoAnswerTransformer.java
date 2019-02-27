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
import javax.persistence.PersistenceContext;

@Component
@SuppressWarnings("SpellCheckingInspection")
public class DtoAnswerTransformer {

    @PersistenceContext
    private EntityManager em;

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //------------------------------------MCQ----------------------------------

    // Used to add answer to an existing question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMCQ toEntity(@NonNull final Long questionId, @NonNull final AnswerMCQInDto dto) {
        AnswerMCQ answer = toEntity(dto);
        answer.setQuestion(em.getReference(QuestionMCQ.class, questionId));
        return answer;
    }

    // Used to cascaded savePoints all answers with a new question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMCQ toEntity(@NonNull final AnswerMCQInDto dto) {
        AnswerMCQ answer = modelMapper.map(dto, AnswerMCQ.class);
        if (dto.getResourceId()!=0) {
            // No need to clear() first
            answer.addResource(em.find(Resource.class, dto.getResourceId()));
        } else {
            answer.clearResources();
        }
        return answer;
    }

    //---------------------------------FBSQ-------------------------------------
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBSQ toEntity(@NonNull final AnswerFBSQInDto dto) {
        if ( dto.getPhrasesIds().isEmpty() || dto.getPhrasesIds().size()<1)
            throw new RuntimeException("Answer FBSQ does not make sense without any accepted phrases!");
        AnswerFBSQ answer = modelMapper.map(dto, AnswerFBSQ.class);
        answer.setSettings(em.getReference(SettingsFB.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }

    //---------------------------------FBMQ-------------------------------------

    // Used to add answer to an existing question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBMQ toEntity(@NonNull final Long questionId, @NonNull final AnswerFBMQInDto dto) {
        AnswerFBMQ answer = toEntity(dto);
        answer.setQuestion(em.getReference(QuestionFBMQ.class, questionId));
        return answer;
    }

    // Used to cascaded savePoints all answers with a new question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerFBMQ toEntity(@NonNull final AnswerFBMQInDto dto) {
        if (dto.getPhrasesIds().isEmpty())
            throw new RuntimeException("Answer FBMQ does not make sense without any accepted phrases!");
        AnswerFBMQ answer = modelMapper.map(dto, AnswerFBMQ.class);
        answer.setSettings(em.getReference(SettingsFB.class, dto.getSetId()));
        dto.getPhrasesIds().forEach(id->answer.addPhrase(em.find(Phrase.class, id)));
        return answer;
    }

    //----------------------------------MQ-------------------------------------

    // Used to add answer to an existing question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMQ toEntity(@NonNull final Long questionId, @NonNull final AnswerMQInDto dto) {
        AnswerMQ answer = toEntity(dto);
        answer.setQuestion(em.getReference(QuestionMQ.class, questionId));
        return answer;
    }

    // Used to cascaded savePoints all answers with a new question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerMQ toEntity(@NonNull final AnswerMQInDto dto) {
        AnswerMQ answer = modelMapper.map(dto, AnswerMQ.class);
        answer.setLeftPhrase(em.getReference(Phrase.class, dto.getLeftPhraseId()));
        answer.setRightPhrase(em.getReference(Phrase.class, dto.getRightPhraseId()));
        return answer;
    }

   //-----------------------------------SQ-------------------------------------

    // Used to add answer to an existing question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSQ toEntity(@NonNull final Long questionId, @NonNull final AnswerSQInDto dto) {
        AnswerSQ answer = toEntity(dto);
        answer.setQuestion(em.getReference(QuestionSQ.class, questionId));
        return answer;
    }

    // Used to cascaded savePoints all answers with a new question
    @Transactional(propagation = Propagation.MANDATORY)
    public AnswerSQ toEntity(@NonNull final AnswerSQInDto dto) {
        AnswerSQ answer = modelMapper.map(dto, AnswerSQ.class);
        answer.setPhrase(em.getReference(Phrase.class, dto.getPhraseId()));
        return answer;
    }

}
