package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.dao.repository.GroupSchemeRepository;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import ua.edu.ratos.service.dto.out.*;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;
import ua.edu.ratos.service.grading.SchemeGradingServiceFactory;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSchemeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeInfoDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeMinDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeShortDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchemeService {

    private static final String SCHEME_NOT_FOUND_ID = "Requested scheme not found, schemeId = ";

    @PersistenceContext
    private final EntityManager em;

    private final SchemeRepository schemeRepository;

    private final GroupSchemeRepository groupSchemeRepository;

    private final SchemeThemeService schemeThemeService;

    private final DtoSchemeTransformer dtoSchemeTransformer;

    private final SchemeGradingManagerService gradingManagerService;

    private final SchemeGradingServiceFactory schemeGradingServiceFactory;

    private final SchemeShortDtoTransformer schemeShortDtoTransformer;

    private final SchemeMinDtoTransformer schemeMinDtoTransformer;

    private final SchemeDtoTransformer schemeDtoTransformer;

    private final SchemeInfoDtoTransformer schemeInfoDtoTransformer;

    private final AccessChecker accessChecker;

    private final SecurityUtils securityUtils;


    //---------------------------------------------------CRUD-----------------------------------------------------------
    @Transactional
    public Long save(@NonNull final SchemeInDto dto) {
        Scheme scheme = dtoSchemeTransformer.toEntity(dto);
        final Long schemeId = schemeRepository.save(scheme).getSchemeId();
        // By design, we cannot cascade saving of the grading details, we do it separately in the same transaction,
        // depending on the scheme's grading questionType specified and ID of selected details
        final long gradingId = dto.getGradingId();
        final long gradingDetailsId = dto.getGradingDetailsId();
        gradingManagerService.save(schemeId, gradingId, gradingDetailsId);
        return schemeId;
    }

    @Transactional
    public void updateName(@NonNull final Long schemeId, @NonNull final String name) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setName(name);
    }

    @Transactional
    public void updateSettings(@NonNull final Long schemeId, @NonNull final Long setId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setSettings(em.getReference(Settings.class, setId));
    }

    @Transactional
    public void updateMode(@NonNull final Long schemeId, @NonNull final Long modeId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setMode(em.getReference(Mode.class, modeId));
    }

    @Transactional
    public void updateStrategy(@NonNull final Long schemeId, @NonNull final Long strId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setStrategy(em.getReference(Strategy.class, strId));
    }

    @Transactional
    public void updateAccess(@NonNull final Long schemeId, @NonNull final Long accessId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void updateCourse(@NonNull final Long schemeId, @NonNull final Long courseId) {
        checkModificationPossibility(schemeId);
        schemeRepository.findById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId))
                .setCourse(em.getReference(Course.class, courseId));
    }

    @Transactional
    public void updateGrading(@NonNull final Long schemeId, @NonNull final Long gradId, @NonNull final Long gradDetailsId) {
        checkModificationPossibility(schemeId);
        final Scheme scheme = schemeRepository.findForGradingById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId));
        if (scheme == null) throw new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId);
        long oldGradingId = scheme.getGrading().getGradingId();
        if (oldGradingId != gradId) {
            gradingManagerService.save(schemeId, gradId, gradDetailsId);
            gradingManagerService.remove(schemeId, oldGradingId);
        } else {
            // Scheme questionType didn't change, but specific settings could have been changed,
            // {ID=4 -> ID=5}, doGameProcessing plays like UPDATE
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
        Scheme scheme = schemeRepository.findForSecurityById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId));
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
        final Scheme scheme = schemeRepository.findForThemesManipulationById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId));
        if (scheme.getThemes().size() != schemeThemeIds.size())
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

    //--------------------------------------------One (for cached session)----------------------------------------------
    @Transactional(readOnly = true)
    public Scheme findByIdForSession(@NonNull final Long schemeId) {
        return schemeRepository.findForSessionById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId));
    }

    //-------------------------------------------One for getting basic Info---------------------------------------------
    @Transactional(readOnly = true)
    public SchemeInfoOutDto findByIdForInfo(@NonNull final Long schemeId) {
        return schemeInfoDtoTransformer.toDto(schemeRepository.findForInfoById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId)));
    }

    //------------------------------------------------One (for update)--------------------------------------------------
    @Transactional(readOnly = true)
    public SchemeOutDto findByIdForUpdate(@NonNull final Long schemeId) {
        SchemeOutDto schemeOutDto = schemeDtoTransformer.toDto(schemeRepository.findForEditById(schemeId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEME_NOT_FOUND_ID + schemeId)));
        // Work here to add grading details DTO;
        Long gradingId = schemeOutDto.getGrading().getGradingId();
        Object gradingDetails = schemeGradingServiceFactory.getInstance(gradingId).findDetails(schemeId);
        schemeOutDto.setGradingDetails(gradingDetails);
        return schemeOutDto;
    }

    //----------------------------------------------Start-up cache update-----------------------------------------------
    @Transactional(readOnly = true)
    public Slice<Scheme> findAllForCachedSession(@NonNull final Pageable pageable) {
        return schemeRepository.findAllForCachedSession(pageable);
    }

    @Transactional(readOnly = true)
    public Slice<Scheme> findLargeForCachedSession(@NonNull final Pageable pageable) {
        return schemeRepository.findLargeForCachedSession(pageable);
    }

    //-----------------------------------------------Runtime cache update-----------------------------------------------
    @Transactional(readOnly = true)
    public Slice<Scheme> findCoursesSchemesForCachedSession(@NonNull final Pageable pageable, @NonNull final Long courseId) {
        return schemeRepository.findCoursesSchemesForCachedSession(courseId, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<Scheme> findDepartmentSchemesForCachedSession(@NonNull final Pageable pageable, @NonNull final Long depId) {
        return schemeRepository.findDepartmentSchemesForCachedSession(depId, pageable);
    }

    //-------------------------------------------------Staff tables-----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return schemeRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return schemeRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(schemeShortDtoTransformer::toDto);
    }

    //-------------------------------------------------Search in table--------------------------------------------------
    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByStaffIdAndName(@NonNull final String letters, boolean contains, @NonNull final Pageable pageable) {
        if (contains)
            return schemeRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
        return schemeRepository.findAllByStaffIdAndNameStarts(securityUtils.getAuthStaffId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAllByDepartmentIdAndName(@NonNull final String letters, boolean contains, @NonNull final Pageable pageable) {
        if (contains)
            return schemeRepository.findAllByDepartmentIdAndNameContains(securityUtils.getAuthDepId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
        return schemeRepository.findAllByDepartmentIdAndNameStarts(securityUtils.getAuthDepId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
    }

    //-------------------------------------------------Staff (min for drop down)----------------------------------------
    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByStaffId() {
        return schemeRepository.findAllForDropDownByStaffId(securityUtils.getAuthStaffId())
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByCourseId(@NonNull final Long courseId) {
        return schemeRepository.findAllForDropDownByCourseId(courseId)
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByDepartmentId(@NonNull final Long depId) {
        return schemeRepository.findAllForDropDownByDepartmentId(depId)
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByDepartmentId() {
        return schemeRepository.findAllForDropDownByDepartmentId(securityUtils.getAuthDepId())
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByFacultyId(@NonNull final Long facId) {
        return schemeRepository.findAllForDropDownByFacultyId(facId)
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByFacultyId() {
        return schemeRepository.findAllForDropDownByFacultyId(securityUtils.getAuthFacId())
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByOrganisationId(@NonNull final Long orgId) {
        return schemeRepository.findAllForDropDownByOrganisationId(orgId)
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdownByOrganisationId() {
        return schemeRepository.findAllForDropDownByOrganisationId(securityUtils.getAuthOrgId())
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<SchemeMinOutDto> findAllForDropdown() {
        return schemeRepository.findAll()
                .stream()
                .map(schemeMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //-------------------------------------------------Slice drop-down--------------------------------------------------
    @Transactional(readOnly = true)
    public Slice<SchemeShortOutDto> findAllForDropDownByDepartmentId(@NonNull final Pageable pageable) {
        return schemeRepository.findAllForDropDownByDepartmentId(securityUtils.getAuthDepId(), pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<SchemeShortOutDto> findAllForDropDownByCourseId(@NonNull final Long courseId, @NonNull final Pageable pageable) {
        return schemeRepository.findAllForDropDownByCourseId(courseId, pageable).map(schemeShortDtoTransformer::toDto);
    }

    //------------------------------------------------Search in drop-down-----------------------------------------------
    @Transactional(readOnly = true)
    public Slice<SchemeShortOutDto> findAllForDropDownByDepartmentIdAndName(@NonNull final String letters, boolean contains, @NonNull final Pageable pageable) {
        if (contains)
            return schemeRepository.findAllForDropDownByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
        return schemeRepository.findAllForDropDownByDepartmentIdAndNameStarts(securityUtils.getAuthDepId(), letters, pageable).map(schemeShortDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<SchemeShortOutDto> findAllForDropDownByCourseIdAndName(@NonNull final Long courseId, @NonNull final String letters, boolean contains, @NonNull final Pageable pageable) {
        if (contains)
            return schemeRepository.findAllForDropDownByCourseIdAndNameLettersContains(courseId, letters, pageable).map(schemeShortDtoTransformer::toDto);
        return schemeRepository.findAllForDropDownByCourseIdAndNameStarts(courseId, letters, pageable).map(schemeShortDtoTransformer::toDto);
    }

    //-------------------------------------------------Admin (for table)------------------------------------------------
    @Transactional(readOnly = true)
    public Page<SchemeShortOutDto> findAll(@NonNull final Pageable pageable) {
        return schemeRepository.findAll(pageable).map(schemeShortDtoTransformer::toDto);
    }

}
