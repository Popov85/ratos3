package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.ThemeRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.ThemeInDto;
import ua.edu.ratos.service.dto.out.ThemeExtendedOutDto;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoThemeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeExtendedDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ThemeService {

    private static final String THEME_NOT_FOUND = "Requested theme not found, themeId = ";

    @PersistenceContext
    private EntityManager em;

    private ThemeRepository themeRepository;

    private DtoThemeTransformer dtoThemeTransformer;

    private ThemeDtoTransformer themeDtoTransformer;

    private ThemeExtendedDtoTransformer themeExtendedDtoTransformer;

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
    public void setThemeExtendedDtoTransformer(ThemeExtendedDtoTransformer themeExtendedDtoTransformer) {
        this.themeExtendedDtoTransformer = themeExtendedDtoTransformer;
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
        themeRepository.findById(themeId).orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setName(name);
    }

    @Transactional
    public void updateAccess(@NonNull final Long themeId, @NonNull final Long accessId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId).orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void updateCourse(@NonNull final Long themeId, @NonNull final Long courseId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId).orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setCourse(em.getReference(Course.class, courseId));
    }

    @Transactional
    public void deleteById(@NonNull final Long themeId) {
        checkModificationPossibility(themeId);
        themeRepository.findById(themeId).orElseThrow(() -> new EntityNotFoundException(THEME_NOT_FOUND + themeId))
                .setDeleted(true);
    }

    private void checkModificationPossibility(@NonNull final Long themeId) {
        Theme theme = themeRepository.findForSecurityById(themeId);
        accessChecker.checkModifyAccess(theme.getAccess(), theme.getStaff());
    }

    //--------------------------SELECT (for update)-------------------

    @Transactional(readOnly = true)
    public ThemeOutDto findByIdForUpdate(@NonNull final Long themeId) {
        return themeDtoTransformer.toDto(themeRepository.findForEditById(themeId));
    }

    //-------------------------SELECT (for themes tables)---------------------

    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return themeRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable)
                .map(themeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAllByStaffIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return themeRepository.findAllByStaffIdAndNameContains(securityUtils.getAuthStaffId(), contains, pageable)
                .map(themeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return themeRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable)
                .map(themeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAllByDepartmentIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return themeRepository.findAllByDepartmentIdAndNameContains(securityUtils.getAuthDepId(), contains, pageable)
                .map(themeDtoTransformer::toDto);
    }

    //-------------------------SELECT (for questions tables)---------------------

    @TrackTime
    @Transactional(readOnly = true)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByStaffId(@NonNull final Pageable pageable) {
        return themeRepository.findAllForQuestionsTableByStaffId(securityUtils.getAuthStaffId(), pageable)
                .map(themeExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByStaffIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return themeRepository.findAllForQuestionsTableByStaffIdAndNameContains(securityUtils.getAuthStaffId(), contains, pageable)
                .map(themeExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByDepartmentId(@NonNull final Pageable pageable) {
        return themeRepository.findAllForQuestionsTableByDepartmentId(securityUtils.getAuthDepId(), pageable)
                .map(themeExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByDepartmentIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return themeRepository.findAllForQuestionsTableByDepartmentIdAndNameContains(securityUtils.getAuthDepId(), contains, pageable)
                .map(themeExtendedDtoTransformer::toDto);
    }


    // --------------------------SELECT (admin, for table)------------------------
    @Transactional(readOnly = true)
    public Page<ThemeOutDto> findAll(@NonNull final Pageable pageable) {
        return themeRepository.findAll(pageable)
                .map(themeDtoTransformer::toDto);
    }
}
