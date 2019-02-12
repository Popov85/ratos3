package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.Grading;
import ua.edu.ratos.dao.repository.GroupSchemeRepository;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;
import ua.edu.ratos.service.grading.SchemeGradingServiceFactory;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSchemeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeShortDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SchemeService {

    private static final String SCHEME_NOT_FOUND_ID = "Requested scheme not found, schemeId = ";

    @PersistenceContext
    private EntityManager em;

    private SchemeRepository schemeRepository;

    private GroupSchemeRepository groupSchemeRepository;

    private SchemeThemeService schemeThemeService;

    private DtoSchemeTransformer dtoSchemeTransformer;

    private SchemeGradingManagerService gradingManagerService;

    private SchemeGradingServiceFactory schemeGradingServiceFactory;

    private SchemeShortDtoTransformer schemeShortDtoTransformer;

    private SchemeDtoTransformer schemeDtoTransformer;

    private SecurityUtils securityUtils;

    private AccessChecker accessChecker;

    @Autowired
    public void setSchemeRepository(SchemeRepository schemeRepository) {
        this.schemeRepository = schemeRepository;
    }

    @Autowired
    public void setGroupSchemeRepository(GroupSchemeRepository groupSchemeRepository) {
        this.groupSchemeRepository = groupSchemeRepository;
    }

    @Autowired
    public void setSchemeThemeService(SchemeThemeService schemeThemeService) {
        this.schemeThemeService = schemeThemeService;
    }

    @Autowired
    public void setDtoSchemeTransformer(DtoSchemeTransformer dtoSchemeTransformer) {
        this.dtoSchemeTransformer = dtoSchemeTransformer;
    }

    @Autowired
    public void setGradingManagerService(SchemeGradingManagerService gradingManagerService) {
        this.gradingManagerService = gradingManagerService;
    }

    @Autowired
    public void setSchemeGradingServiceFactory(SchemeGradingServiceFactory schemeGradingServiceFactory) {
        this.schemeGradingServiceFactory = schemeGradingServiceFactory;
    }

    @Autowired
    public void setSchemeShortDtoTransformer(SchemeShortDtoTransformer schemeShortDtoTransformer) {
        this.schemeShortDtoTransformer = schemeShortDtoTransformer;
    }

    @Autowired
    public void setSchemeDtoTransformer(SchemeDtoTransformer schemeDtoTransformer) {
        this.schemeDtoTransformer = schemeDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setAccessChecker(AccessChecker accessChecker) {
        this.accessChecker = accessChecker;
    }

    //---------------------------------------------------CRUD---------------------------------------------------------

    @Transactional
    public Long save(@NonNull final SchemeInDto dto) {
        Scheme scheme = dtoSchemeTransformer.toEntity(dto);
        final Long schemeId = schemeRepository.save(scheme).getSchemeId();
        // By design, we cannot cascade saving of the grading details, we do it separately in the same transaction,
        // depending on the scheme's grading type specified and ID of selected details
        final long gradingId = dto.getGradingId();
        final long gradingDetailsId = dto.getGradingDetailsId();
        gradingManagerService.save(schemeId, gradingId, gradingDetailsId);
        return schemeId;
    }

    @Transactional
    public void updateName(@NonNull final Long schemeId, @NonNull final String name) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setName(name);
    }

    @Transactional
    public void updateSettings(@NonNull final Long schemeId, @NonNull final Long setId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setSettings(em.getReference(Settings.class, setId));
    }

    @Transactional
    public void updateMode(@NonNull final Long schemeId, @NonNull final Long modeId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setMode(em.getReference(Mode.class, modeId));
    }

    @Transactional
    public void updateStrategy(@NonNull final Long schemeId, @NonNull final Long strId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setStrategy(em.getReference(Strategy.class, strId));
    }

    @Transactional
    public void updateAccess(@NonNull final Long schemeId, @NonNull final Long accessId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void updateCourse(@NonNull final Long schemeId, @NonNull final Long courseId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setCourse(em.getReference(Course.class, courseId));
    }

    @Transactional
    public void updateGrading(@NonNull final Long schemeId, @NonNull final Long gradId, @NonNull final Long gradDetailsId) {
        checkModificationPossibility(schemeId);
        final Scheme scheme = schemeRepository.findForGradingById(schemeId);
        if (scheme==null) throw new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId);
        long oldGradingId = scheme.getGrading().getGradingId();
        if (oldGradingId != gradId) {
            gradingManagerService.save(schemeId, gradId, gradDetailsId);
            gradingManagerService.remove(schemeId, oldGradingId);
        } else {
            // Scheme type didn't change, but specific settings could have been changed,
            // {ID=4 -> ID=5}, save plays like UPDATE
            gradingManagerService.save(schemeId, oldGradingId, gradDetailsId);
        }
        scheme.setGrading(em.getReference(Grading.class, gradId));
    }

    @Transactional
    public void updateIsActive(@NonNull final Long schemeId, boolean isActive) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setActive(isActive);
    }

    @Transactional
    public void updateIsLmsOnly(@NonNull final Long schemeId, boolean isLmsOnly) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setLmsOnly(isLmsOnly);
    }


    @Transactional
    public void deleteById(@NonNull final Long schemeId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId).orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setDeleted(true);
    }

    private void checkModificationPossibility(@NonNull final Long schemeId) {
        Scheme scheme = schemeRepository.findForSecurityById(schemeId);
        accessChecker.checkModifyAccess(scheme.getAccess(), scheme.getStaff());
    }


    //----------------------------------------------------THEMES--------------------------------------------------------
    // we manage it here cause it affects scheme completeness

    @Transactional
    public void removeTheme(@NonNull final Long schemeId, @NonNull final Long schemeThemeId) {
        if (!hasMultipleThemes(schemeId)) throw new RuntimeException("Cannot remove the last theme");
        schemeThemeService.remove(schemeThemeId);
    }

    private boolean hasMultipleThemes(@NonNull final Long schemeId) {
        Set<SchemeTheme> themes = schemeThemeService.findAllBySchemeId(schemeId);
        return themes != null && themes.size() > 1;
    }

    @Transactional
    public void reOrderThemes(@NonNull final Long schemeId, @NonNull final List<Long> schemeThemeIds) {
        final Scheme scheme = schemeRepository.findForThemesManipulationById(schemeId);
        if (scheme.getThemes().size()!=schemeThemeIds.size())
            throw new RuntimeException("Cannot re-order themes: unequal list size");
        scheme.clearSchemeTheme();
        schemeThemeIds.forEach(id -> scheme.addSchemeTheme(em.find(SchemeTheme.class, id)));
    }


    //---------------------------------------------------GROUPS---------------------------------------------------------

    @Transactional
    public void addGroup(@NonNull final Long schemeId, @NonNull final Long groupId) {
        GroupScheme groupScheme = new GroupScheme();
        groupScheme.setGroup(em.getReference(Group.class, groupId));
        groupScheme.setScheme(em.getReference(Scheme.class, schemeId));
        groupSchemeRepository.save(groupScheme);
    }

    @Transactional
    public void removeGroup(@NonNull final Long schemeId, @NonNull final Long groupId) {
        groupSchemeRepository.deleteById(new GroupSchemeId(groupId, schemeId));
    }


    //-------------------------------------------------One (for session)------------------------------------------------

    @Transactional(readOnly = true)
    public Scheme findByIdForSession(@NonNull final Long schemeId) {
        return schemeRepository.findForSessionById(schemeId);
    }

    //------------------------------------------------One (for update)--------------------------------------------------

    @Transactional(readOnly = true)
    public SchemeOutDto findByIdForUpdate(@NonNull final Long schemeId) {
        SchemeOutDto schemeOutDto = schemeDtoTransformer.toDto(schemeRepository.findForEditById(schemeId));
        // Work here to add grading details DTO;
        Long gradingId = schemeOutDto.getGrading().getGradingId();
        Object gradingDetails = schemeGradingServiceFactory.getGraderService(gradingId).findDetails(schemeId);
        schemeOutDto.setGradingDetails(gradingDetails);
        return schemeOutDto;
    }

    //-------------------------------------------------Staff tables-----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return schemeRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByStaffIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return schemeRepository.findAllByStaffIdAndNameContains(securityUtils.getAuthStaffId(), contains, pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return schemeRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByDepartmentIdAndNameContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return schemeRepository.findAllByDepartmentIdAndNameContains(securityUtils.getAuthDepId(), contains, pageable).map(schemeShortDtoTransformer::toDto);
    }
    

    //-------------------------------------------------Admin (for table)------------------------------------------------

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAll(@NonNull final Pageable pageable) {
        return schemeRepository.findAll(pageable).map(schemeShortDtoTransformer::toDto);
    }

}
