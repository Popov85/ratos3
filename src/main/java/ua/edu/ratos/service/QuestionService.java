package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.SchemeThemeSettings;
import ua.edu.ratos.domain.entity.question.*;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.service.dto.entity.*;
import ua.edu.ratos.service.dto.transformer.DtoQuestionTransformer;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DtoQuestionTransformer transformer;


    @Transactional
    public Long save(@NonNull QuestionMCQInDto dto) {
        final QuestionMultipleChoice entity = transformer.fromDto(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionFBSQInDto dto) {
        final QuestionFillBlankSingle entity = transformer.fromDto(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionFBMQInDto dto) {
        final QuestionFillBlankMultiple entity = transformer.fromDto(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionMQInDto dto) {
        final QuestionMatcher entity = transformer.fromDto(dto);
        final Question savedQuestion = questionRepository.save(entity);
        return savedQuestion.getQuestionId();
    }

    @Transactional
    public Long save(@NonNull QuestionSQInDto dto) {
        final QuestionSequence entity = transformer.fromDto(dto);
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
    public void update(@NonNull QuestionInDto dto) {
        final Long questionId = dto.getQuestionId();
        final Optional<Question> optional = questionRepository.findById(questionId);
        final Question updatable = optional.orElseThrow(() ->
                new RuntimeException("Question not found, ID = "+ questionId));
        transformer.mapDto(dto, updatable);
    }


    @Transactional(readOnly = true)
    public Set<Question> findAllByThemeId(@NonNull Long themeId) {
        Set<Question> questions = new HashSet<>();
        // First, find all existing types (ids) in this theme
        questionRepository.findTypes(themeId).forEach(typeId->questions.addAll(findAllByThemeIdAndTypeId(themeId, typeId)));
        return questions;
    }

    @Transactional(readOnly = true)
    public Set<? extends Question> findAllByThemeIdAndTypeId(Long themeId, Long typeId) {
        if (typeId==1) {
            return questionRepository.findAllMCQWithEverythingByThemeId(themeId);
        } else if (typeId==2) {
            return questionRepository.findAllFBSQWithEverythingByThemeId(themeId);
        } else if (typeId==3) {
            return questionRepository.findAllFBMQWithEverythingByThemeId(themeId);
        } else if (typeId==4) {
            return questionRepository.findAllMQWithEverythingByThemeId(themeId);
        } else if (typeId==5) {
            return questionRepository.findAllSQWithEverythingByThemeId(themeId);
        }
        throw new RuntimeException("Unsupported type");
    }

    @Transactional
    public void deleteById(@NonNull Long questionId) {
        questionRepository.pseudoDeleteById(questionId);
        log.warn("Question is hidden, ID = {}", questionId);
    }

}
