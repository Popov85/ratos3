package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.*;
import ua.edu.ratos.service.dto.out.question.*;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoQuestionTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.QuestionDtoTransformer;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuestionService {

    private static final String QUESTION_NOT_FOUND = "The requested Question is not found, questionId = ";

    private final QuestionRepository questionRepository;

    private final DtoQuestionTransformer dtoQuestionTransformer;

    private final QuestionDtoTransformer questionDtoTransformer;

    private final SecurityUtils securityUtils;

    //---------------------------------------------------CRUD-----------------------------------------------------------
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
        final Question entity = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException(QUESTION_NOT_FOUND + questionId));
        dtoQuestionTransformer.mapDto(dto, entity);
    }

    @Transactional
    public void deleteById(@NonNull final Long questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException(QUESTION_NOT_FOUND + questionId))
                .setDeleted(true);
    }

    //----------------------------------------------------One for edit--------------------------------------------------
    @Transactional(readOnly = true)
    public QuestionMCQOutDto findOneMCQForEditById(@NonNull final Long questionId) {
        return questionDtoTransformer.toDto(questionRepository.findOneMCQForEditById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionMCQ is not found , questionId = " + questionId)));
    }

    @Transactional(readOnly = true)
    public QuestionFBSQOutDto findOneFBSQForEditById(@NonNull final Long questionId) {
        return questionDtoTransformer.toDto(questionRepository.findOneFBSQForEditById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionFBSQ is not found, questionId = " + questionId)));
    }

    @Transactional(readOnly = true)
    public QuestionFBMQOutDto findOneFBMQForEditById(@NonNull final Long questionId) {
        return questionDtoTransformer.toDto(questionRepository.findOneFBMQForEditById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionFBMQ is not found, questionId = " + questionId)));
    }

    @Transactional(readOnly = true)
    public QuestionMQOutDto findOneMQForEditById(@NonNull final Long questionId) {
        return questionDtoTransformer.toDto(questionRepository.findOneMQForEditById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionMQ is not found, questionId = " + questionId)));
    }

    @Transactional(readOnly = true)
    public QuestionSQOutDto findOneSQForEditById(@NonNull final Long questionId) {
        return questionDtoTransformer.toDto(questionRepository.findOneSQForEditById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("QuestionSQ is not found, questionId = " + questionId)));
    }

    //-------------------------------------------------------Session----------------------------------------------------
    @Transactional(readOnly = true)
    public Set<Question> findAllForSessionByThemeIdAndType(@NonNull final Long themeId, @NonNull final Long typeId, byte level) {
        return questionRepository.findAllForSimpleSessionByThemeTypeAndLevel(themeId, typeId, level);
    }

    //----------------------------------------------------Cached session------------------------------------------------
    @Cacheable(value = "question", key = "{'MCQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionMCQ> findAllMCQForCachedSessionByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllMCQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @Cacheable(value = "question", key = "{'FBSQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionFBSQ> findAllFBSQForCachedSessionByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllFBSQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @Cacheable(value = "question", key = "{'FBMQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionFBMQ> findAllFBMQForCachedSessionByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllFBMQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @Cacheable(value = "question", key = "{'MQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionMQ> findAllMQForCachedSessionByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllMQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @Cacheable(value = "question", key = "{'SQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionSQ> findAllSQForCachedSessionByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllSQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    // -----------------------------------------------Update Cache------------------------------------------------------
    @CachePut(value = "question", key = "{'MCQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionMCQ> findAllMCQForCacheUpdateByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllMCQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @CachePut(value = "question", key = "{'FBSQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionFBSQ> findAllFBSQForCacheUpdateByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllFBSQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @CachePut(value = "question", key = "{'FBMQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionFBMQ> findAllFBMQForCacheUpdateByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllFBMQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @CachePut(value = "question", key = "{'MQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionMQ> findAllMQForCacheUpdateByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllMQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    @CachePut(value = "question", key = "{'SQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public Set<QuestionSQ> findAllSQForCacheUpdateByThemeId(@NonNull final Long themeId, byte level) {
        return questionRepository.findAllSQForCachedSessionWithEverythingByThemeAndLevel(themeId, level);
    }

    //-------------------------------------------------Invalidate cache-------------------------------------------------
    // Consider use @CachePut methods instead!
    @CacheEvict(value = "question", key = "{'MCQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public void invalidateCacheOfAllMCQByThemeId(@NonNull final Long themeId, byte level) {
    }

    @CacheEvict(value = "question", key = "{'FBSQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public void invalidateCacheOfAllFBSQByThemeId(@NonNull final Long themeId, byte level) {
    }

    @CacheEvict(value = "question", key = "{'FBMQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public void invalidateCacheOfAllFBMQByThemeId(@NonNull final Long themeId, byte level) {
    }

    @CacheEvict(value = "question", key = "{'MQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public void invalidateCacheOfAllMQByThemeId(@NonNull final Long themeId, byte level) {
    }

    @CacheEvict(value = "question", key = "{'SQ', #themeId, #level}")
    @Transactional(readOnly = true)
    public void invalidateCacheOfAllSQByThemeId(@NonNull final Long themeId, byte level) {
    }

    //----------------------------------------------------Staff table---------------------------------------------------
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

    //---------------------------------------Staff (global search throughout department)--------------------------------
    @Transactional(readOnly = true)
    public Slice<QuestionMCQOutDto> findAllMCQForSearchByDepartmentIdAndTitleContains(@NonNull final String starts, @NonNull final Pageable pageable) {
        Long depId = securityUtils.getAuthDepId();
        return questionRepository.findAllMCQForSearchByDepartmentIdAndTitleContains(depId, starts, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<QuestionFBSQOutDto> findAllFBSQForSearchByDepartmentIdAndTitleContains(@NonNull final String starts, @NonNull final Pageable pageable) {
        Long depId = securityUtils.getAuthDepId();
        return questionRepository.findAllFBSQForSearchByDepartmentIdAndTitleContains(depId, starts, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<QuestionFBMQOutDto> findAllFBMQForSearchByDepartmentIdAndTitleContains(@NonNull final String starts, @NonNull final Pageable pageable) {
        Long depId = securityUtils.getAuthDepId();
        return questionRepository.findAllFBMQForSearchByDepartmentIdAndTitleContains(depId, starts, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<QuestionMQOutDto> findAllMQForSearchByDepartmentIdAndTitleContains(@NonNull final String starts, @NonNull final Pageable pageable) {
        Long depId = securityUtils.getAuthDepId();
        return questionRepository.findAllMQForSearchByDepartmentIdAndTitleContains(depId, starts, pageable).map(questionDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<QuestionSQOutDto> findAllSQForSearchByDepartmentIdAndTitleContains(@NonNull final String starts, @NonNull final Pageable pageable) {
        Long depId = securityUtils.getAuthDepId();
        return questionRepository.findAllSQForSearchByDepartmentIdAndTitleContains(depId, starts, pageable).map(questionDtoTransformer::toDto);
    }
}
