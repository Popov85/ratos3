package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.dao.repository.ThemeRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.ThemeInDto;
import ua.edu.ratos.service.dto.out.ThemeExtOutDto;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import ua.edu.ratos.service.dto.out.ThemeMapOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoThemeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeExtDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeMapDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ThemeService {

    private static final String THEME_NOT_FOUND = "Requested theme not found, themeId = ";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private QuestionRepository questionRepository;

    private ThemeRepository themeRepository;

    private DtoThemeTransformer dtoThemeTransformer;

    private ThemeDtoTransformer themeDtoTransformer;

    private ThemeExtDtoTransformer themeExtDtoTransformer;

    private ThemeMapDtoTransformer themeMapDtoTransformer;

    private AccessChecker accessChecker;

    private SecurityUtils securityUtils;

    @Autowired
    public void setThemeRepository(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Autowired
    public void setDtoThemeTransformer(DtoThemeTransformer dtoThemeTransformer) {
        this.dtoThemeTransformer = dtoThemeTransformer;
    }

    @Autowired
    public void setThemeDtoTransformer(ThemeDtoTransformer themeDtoTransformer) {
        this.themeDtoTransformer = themeDtoTransformer;
    }

    @Autowired
    public void setThemeExtDtoTransformer(ThemeExtDtoTransformer themeExtDtoTransformer) {
        this.themeExtDtoTransformer = themeExtDtoTransformer;
    }

    @Autowired
    public void setThemeMapDtoTransformer(ThemeMapDtoTransformer themeMapDtoTransformer) {
        this.themeMapDtoTransformer = themeMapDtoTransformer;
    }

    @Autowired
    public void setAccessChecker(AccessChecker accessChecker) {
        this.accessChecker = accessChecker;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Long save(@NonNull final ThemeInDto dto) {
        Theme theme = dtoThemeTransformer.toEntity(dto);
        return themeRepository.save(theme).getThemeId();
    }

    @Transactional
    public void updateName(@NonNull final Long themeId, @NonNull final String name) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setName(name);
    }

    @Transactional
    public void updateAccess(@NonNull final Long themeId, @NonNull final Long accessId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void updateCourse(@NonNull final Long themeId, @NonNull final Long courseId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setCourse(em.getReference(Course.class, courseId));
    }

    @Transactional
    public void deleteById(@NonNull final Long themeId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setDeleted(true);
    }

    private void checkModificationPossibility(@NonNull final Long themeId) {
        Theme theme = themeRepository.findForSecurityById(themeId);
        accessChecker.checkModifyAccess(theme.getAccess(), theme.getStaff());
    }

    //---------------------------------------------One (for update)-----------------------------------------------------

    @Transactional(readOnly = true)
    public ThemeOutDto findByIdForUpdate(@NonNull final Long themeId) {
        return themeDtoTransformer.toDto(themeRepository.findForEditById(themeId));
    }

    //--------------------------------------------Staff theme table-----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByStaffId(@NonNull final Pageable pageable) {
        return themeRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(themeExtDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentId(@NonNull final Pageable pageable) {
        return themeRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(themeExtDtoTransformer::toDto);
    }

    //--------------------------------------------Staff table search----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByStaffIdAndName(@NonNull final String starts, boolean contains, @NonNull final Pageable pageable) {
        if (contains) return themeRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), starts, pageable).map(themeExtDtoTransformer::toDto);
        return themeRepository.findAllByStaffIdAndNameStarts(securityUtils.getAuthStaffId(), starts, pageable).map(themeExtDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentIdAndName(@NonNull final String starts, boolean contains, @NonNull final Pageable pageable) {
        if (contains) return themeRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), starts, pageable).map(themeExtDtoTransformer::toDto);
        return themeRepository.findAllByDepartmentIdAndNameStarts(securityUtils.getAuthDepId(), starts, pageable).map(themeExtDtoTransformer::toDto);
    }

    //----------------------------------------------Slice drop-down-----------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<ThemeExtOutDto> findAllForDropDownByStaffId(@NonNull final Pageable pageable) {
        return themeRepository.findAllForDropDownByStaffId(securityUtils.getAuthStaffId(), pageable).map(themeExtDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<ThemeExtOutDto> findAllForDropDownByDepartmentId(@NonNull final Pageable pageable) {
        return themeRepository.findAllForDropDownByDepartmentId(securityUtils.getAuthDepId(), pageable).map(themeExtDtoTransformer::toDto);
    }

    //----------------------------------------------Drop-down search----------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<ThemeExtOutDto> findAllForDropDownByStaffIdAndName(@NonNull final String starts, boolean contains, @NonNull final Pageable pageable) {
        if (contains) return themeRepository.findAllForDropDownByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), starts, pageable).map(themeExtDtoTransformer::toDto);
        return themeRepository.findAllForDropDownByStaffIdAndNameStarts(securityUtils.getAuthStaffId(), starts, pageable).map(themeExtDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<ThemeExtOutDto> findAllForDropDownByDepartmentIdAndName(@NonNull final String starts, boolean contains, @NonNull final Pageable pageable) {
        if (contains) return themeRepository.findAllForDropDownByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), starts, pageable).map(themeExtDtoTransformer::toDto);
        return themeRepository.findAllForDropDownByDepartmentIdAndNameStarts(securityUtils.getAuthDepId(), starts, pageable).map(themeExtDtoTransformer::toDto);
    }

    //----------------------------------------------Scheme creating support---------------------------------------------

    @Transactional(readOnly = true)
    public ThemeMapOutDto getQuestionTypeLevelMapByThemeId(@NonNull final Long themeId) {
        Set<Question> questions = questionRepository.findAllForTypeLevelMapByThemeId(themeId);
        if (questions==null || questions.isEmpty()) throw new IllegalStateException("No questions in this theme!");
        return themeMapDtoTransformer.toDto(themeId, questions);
    }

    //------------------------------------------------------DEBUG-------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentIdDebug(Long depId, @NonNull final Pageable pageable) {
        return themeRepository.findAllByDepartmentId(depId, pageable).map(themeExtDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentIdAndNameStartsDebug(Long depId, @NonNull final String letters, boolean contains, @NonNull final Pageable pageable) {
        if (contains) return themeRepository.findAllByDepartmentIdAndNameLettersContains(depId, letters, pageable).map(themeExtDtoTransformer::toDto);
        return themeRepository.findAllByDepartmentIdAndNameStarts(depId, letters, pageable).map(themeExtDtoTransformer::toDto);
    }


    // -----------------------------------------------Admin table-------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAll(@NonNull final Pageable pageable) {
        return themeRepository.findAll(pageable).map(themeDtoTransformer::toDto);
    }
}
