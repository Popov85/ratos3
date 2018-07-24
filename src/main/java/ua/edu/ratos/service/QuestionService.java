package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.repository.*;
import java.util.*;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Question added while editing Theme's set of Questions in front-end Editor tool,
     * this method is used for saving questions of every possible type
     * @param question
     */
    @Transactional
    public void save(@NonNull Question question) {
        final Question savedQuestion = questionRepository.save(question);
        log.debug("Saved question :: {} ", savedQuestion);
    }

    /**
     * Questions added after parsing the .rtp- .txt-files
     * @param questions
     */
    @Transactional
    public void saveAll(@NonNull List<Question> questions) {
        questionRepository.saveAll(questions);
        log.debug("Saved multiple questions :: {} ", questions.size());
    }

    /**
     * Updates only generic fields of Question
     * @param updatedQuestion base Question, without any child fields, except required Language
     *  In order to update a Question's Help/Resources or Answers refer to the corresponding classes
     * @link
     */
    @Transactional
    public void update(@NonNull Question updatedQuestion) {
        final Long questionId = updatedQuestion.getQuestionId();
        final Optional<Question> optional = questionRepository.findById(questionId);
        final Question oldQuestion = optional.orElseThrow(() ->
                new RuntimeException("Question not found, ID = "+ questionId));
        oldQuestion.setQuestion(updatedQuestion.getQuestion());
        oldQuestion.setLevel(updatedQuestion.getLevel());
        oldQuestion.setLang(updatedQuestion.getLang());
        log.debug("Updated question :: {} ", oldQuestion);
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
/*
    @Transactional
    public void addHelp(@NonNull Help help) {
        if (help.getHelpId()==null || help.getHelpId()==0)
            throw new RuntimeException("helpId must not be null");
        helpRepository.save(help);
    }

    @Transactional
    public void removeHelp(@NonNull Long questionId) {
        helpRepository.deleteById(questionId);
    }*/


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
        log.warn("Question is to be hidden, Id= {}", questionId);
    }

}
