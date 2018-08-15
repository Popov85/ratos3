package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.*;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.service.dto.entity.*;
import ua.edu.ratos.service.dto.transformer.DtoQuestionTransformer;
import java.util.*;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResourceRepository resourceRepository;

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

    @Transactional
    public void addResource(@NonNull Resource resource, Long questionId) {
        final Question question = questionRepository.findByIdWithResources(questionId);
        question.addResource(resource);
    }

    @Transactional
    public void deleteResource(@NonNull Resource resource, boolean fromRepository, Long questionId) {
        final Question question = questionRepository.findByIdWithResources(questionId);
        question.removeResource(resource);
        if (fromRepository) resourceRepository.delete(resource);
    }

    /**
     * Fetch all existing questions from the DB into the in-memory cache for further processing
     * @param themeId
     * @return all existing questions in this theme by type
     */
    @Transactional(readOnly = true)
    public Map<String, List<? extends Question>> findAll(@NonNull Long themeId) {
        Map<String, List<? extends Question>> questions = new HashMap<>();
        // First, find all existing types in this theme
        questionRepository.findTypes(themeId).forEach((t)->questions.put(t, findByType(t, themeId)));
        return questions;
    }

    private List<? extends Question> findByType(String type, Long themeId) {
        switch (type) {
            case "MCQ":
                return questionRepository.findAllMCQWithEverythingByThemeId(themeId);
            case "FBSQ":
                return questionRepository.findAllFBSQWithEverythingByThemeId(themeId);
            case "FBMQ":
                return questionRepository.findAllFBMQWithEverythingByThemeId(themeId);
            case "MQ":
                return questionRepository.findAllMQWithEverythingByThemeId(themeId);
            case "SQ":
                return questionRepository.findAllSQWithEverythingByThemeId(themeId);
            default:
                throw new RuntimeException("Unrecognized question type");
        }
    }

    @Transactional
    public void deleteById(@NonNull Long questionId) {
        questionRepository.pseudoDeleteById(questionId);
        log.warn("Question is hidden, ID = {}", questionId);
    }

}
