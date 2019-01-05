package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionFBMQ;
import ua.edu.ratos.dao.entity.question.QuestionFBSQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.entity.question.QuestionMQ;
import ua.edu.ratos.dao.repository.*;
import ua.edu.ratos.service.domain.question.*;
import ua.edu.ratos.service.dto.in.*;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoQuestionTransformer;
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DtoQuestionTransformer transformer;

    @Autowired
    private QuestionDomainTransformer questionDomainTransformer;

    @Transactional
    public Long save(@NonNull QuestionMCQInDto dto) {
        final QuestionMCQ entity = transformer.toEntity(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionFBSQInDto dto) {
        final QuestionFBSQ entity = transformer.toEntity(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionFBMQInDto dto) {
        final QuestionFBMQ entity = transformer.toEntity(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionMQInDto dto) {
        final QuestionMQ entity = transformer.toEntity(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionSQInDto dto) {
        final QuestionSQ entity = transformer.toEntity(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    /**
     * Questions obtained after parsing the .rtp- .txt-files
     * @param questions
     */
    @Transactional
    public void saveAll(@NonNull List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    @Transactional
    public void update(@NonNull Long questionId, @NonNull QuestionInDto dto) {
        final Optional<Question> optional = questionRepository.findById(questionId);
        final Question updatable = optional.orElseThrow(() ->
                new RuntimeException("Question not found, ID = "+ questionId));
        transformer.mapDto(dto, updatable);
    }

    @Transactional
    public void deleteById(@NonNull Long questionId) {
        questionRepository.findById(questionId).get().setDeleted(true);
    }

    /*-----------------------SELECT------------------------*/

    @Transactional(readOnly = true)
    public Set<QuestionDomain> findAllByThemeId(@NonNull Long themeId) {
        Set<QuestionDomain> questionDomains = new HashSet<>();
        // First, find all existing types (answerIds) in this theme
        questionRepository.findTypes(themeId).forEach(typeId-> questionDomains.addAll(findAllByThemeIdAndTypeId(themeId, typeId)));
        return questionDomains;
    }

    @Transactional(readOnly = true)
    public Set<? extends QuestionDomain> findAllByThemeIdAndTypeId(Long themeId, Long typeId) {
        if (typeId==1) {
            Set<QuestionMCQ> set = questionRepository.findAllMCQWithEverythingByThemeId(themeId);
            Set<QuestionMCQDomain> result = set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
            return result;
        }else if (typeId==2) {
            Set<QuestionFBSQ> set = questionRepository.findAllFBSQWithEverythingByThemeId(themeId);
            Set<QuestionFBSQDomain> result = set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
            return result;
        }  else if (typeId==3) {
            Set<QuestionFBMQ> set = questionRepository.findAllFBMQWithEverythingByThemeId(themeId);
            Set<QuestionFBMQDomain> result = set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
            return result;
        } else if (typeId==4) {
            Set<QuestionMQ> set = questionRepository.findAllMQWithEverythingByThemeId(themeId);
            Set<QuestionMQDomain> result = set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
            return result;
        } else if (typeId==5) {
            Set<QuestionSQ> set = questionRepository.findAllSQWithEverythingByThemeId(themeId);
            Set<QuestionSQDomain> result = set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
            return result;
        }
        throw new RuntimeException("Unsupported type");
    }

}
