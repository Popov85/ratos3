package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import ua.edu.ratos.service.dto.out.question.*;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoQuestionTransformer;
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.QuestionDtoTransformer;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {

    private static final String QUESTION_NOT_FOUND = "The requested Question not found, questionId = ";

    private QuestionRepository questionRepository;

    private DtoQuestionTransformer dtoQuestionTransformer;

    private QuestionDomainTransformer questionDomainTransformer;

    private QuestionDtoTransformer questionDtoTransformer;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setDtoQuestionTransformer(DtoQuestionTransformer dtoQuestionTransformer) {
        this.dtoQuestionTransformer = dtoQuestionTransformer;
    }

    @Autowired
    public void setQuestionDomainTransformer(QuestionDomainTransformer questionDomainTransformer) {
        this.questionDomainTransformer = questionDomainTransformer;
    }

    @Autowired
    public void setQuestionDtoTransformer(QuestionDtoTransformer questionDtoTransformer) {
        this.questionDtoTransformer = questionDtoTransformer;
    }

    @Transactional
    public Long save(@NonNull final QuestionMCQInDto dto) {
        return questionRepository.save(dtoQuestionTransformer.toEntity(dto)).getQuestionId();
    }

    @Transactional
    public Long save(@NonNull final QuestionFBSQInDto dto) {
        return questionRepository.save(dtoQuestionTransformer.toEntity(dto)).getQuestionId();
    }

    @Transactional
    public Long save(@NonNull final QuestionFBMQInDto dto) {
        return questionRepository.save(dtoQuestionTransformer.toEntity(dto)).getQuestionId();
    }

    @Transactional
    public Long save(@NonNull final QuestionMQInDto dto) {
        return questionRepository.save(dtoQuestionTransformer.toEntity(dto)).getQuestionId();
    }

    @Transactional
    public Long save(@NonNull final QuestionSQInDto dto) {
        return questionRepository.save(dtoQuestionTransformer.toEntity(dto)).getQuestionId();
    }

    /**
     * Questions obtained after parsing the .rtp- .txt-files
     * @param questions batch of questions from a file
     */
    @Transactional
    public void saveAll(@NonNull final List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    @Transactional
    public void update(@NonNull final Long questionId, @NonNull final QuestionInDto dto) {
        final Question entity = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException(QUESTION_NOT_FOUND + questionId));
        dtoQuestionTransformer.mapDto(dto, entity);
    }

    @Transactional
    public void deleteById(@NonNull final Long questionId) {
        questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException(QUESTION_NOT_FOUND + questionId))
                .setDeleted(true);
    }

    //--------------------------------------STUDENT SESSION cache---------------------------------

    @Transactional(readOnly = true)
    public Set<QuestionDomain> findAllByThemeId(@NonNull final Long themeId) {
        Set<QuestionDomain> questionDomains = new HashSet<>();
        // First, findDetails all existing types (answerIds) in this theme
        questionRepository.findTypes(themeId).forEach(typeId-> questionDomains.addAll(findAllByThemeIdAndTypeId(themeId, typeId)));
        return questionDomains;
    }

    @Transactional(readOnly = true)
    public Set<? extends QuestionDomain> findAllByThemeIdAndTypeId(@NonNull final Long themeId, @NonNull final Long typeId) {
        if (typeId==1) {
            Set<QuestionMCQ> set = questionRepository.findAllMCQWithEverythingByThemeId(themeId);
            return set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
        }else if (typeId==2) {
            Set<QuestionFBSQ> set = questionRepository.findAllFBSQWithEverythingByThemeId(themeId);
            return set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
        }  else if (typeId==3) {
            Set<QuestionFBMQ> set = questionRepository.findAllFBMQWithEverythingByThemeId(themeId);
            return set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
        } else if (typeId==4) {
            Set<QuestionMQ> set = questionRepository.findAllMQWithEverythingByThemeId(themeId);
            return set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
        } else if (typeId==5) {
            Set<QuestionSQ> set = questionRepository.findAllSQWithEverythingByThemeId(themeId);
            return set.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toSet());
        }
        throw new RuntimeException("Unsupported type");
    }

    //--------------------------------------INSTRUCTOR for edit------------------------------------

    @Transactional(readOnly = true)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeId(@NonNull final Long themeId, @NonNull final Pageable pageable) {
        return questionRepository.findAllMCQForEditByThemeId(themeId, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeId(@NonNull final Long themeId, @NonNull final Pageable pageable) {
        return questionRepository.findAllFBSQForEditByThemeId(themeId, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeId(@NonNull final Long themeId, @NonNull final Pageable pageable) {
        return questionRepository.findAllFBMQForEditByThemeId(themeId, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionMQOutDto> findAllMQForEditByThemeId(@NonNull final Long themeId, @NonNull final Pageable pageable) {
        return questionRepository.findAllMQForEditByThemeId(themeId, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeId(@NonNull final Long themeId, @NonNull final Pageable pageable) {
        return questionRepository.findAllSQForEditByThemeId(themeId, pageable).map(questionDtoTransformer::toDto);
    }

    //-----------------------------------INSTRUCTOR search by question--------------------------------

    @Transactional(readOnly = true)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeIdAndQuestionLettersContains(@NonNull final Long themeId, @NonNull final String letters, @NonNull final Pageable pageable) {
        return questionRepository.findAllMCQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeIdAndQuestionLettersContains(@NonNull final Long themeId, @NonNull final String letters, @NonNull final Pageable pageable) {
        return questionRepository.findAllFBSQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeIdAndQuestionLettersContains(@NonNull final Long themeId, @NonNull final String letters, @NonNull final Pageable pageable) {
        return questionRepository.findAllFBMQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionMQOutDto> findAllMQForEditByThemeIdAndQuestionLettersContains(@NonNull final Long themeId, @NonNull final String letters, @NonNull final Pageable pageable) {
        return questionRepository.findAllMQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeIdAndQuestionLettersContains(@NonNull final Long themeId, @NonNull final String letters, @NonNull final Pageable pageable) {
        return questionRepository.findAllSQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable).map(questionDtoTransformer::toDto);
    }
}
