package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.dao.repository.lms.LMSCourseRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;
import ua.edu.ratos.service.dto.out.LMSCourseOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoLMSCourseTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.LMSCourseDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LMSCourseService {

    private static final String LMS_COURSE_NOT_FOUND = "Requested LMSCourse is not found, courseId = ";

    @PersistenceContext
    private final EntityManager em;

    private final LMSCourseRepository lmsCourseRepository;

    private final DtoLMSCourseTransformer dtoLMSCourseTransformer;

    private final LMSCourseDtoTransformer lmsCourseDtoTransformer;

    private final AccessChecker accessChecker;

    private final SecurityUtils securityUtils;

    //------------------------------------------------------CRUD--------------------------------------------------------
    @Transactional
    public Long save(@NonNull final LMSCourseInDto dto) {
        return lmsCourseRepository.save(dtoLMSCourseTransformer.toEntity(dto)).getCourseId();
    }

    @Transactional
    public void updateName(@NonNull final Long courseId, @NonNull final String name) {
        checkModificationPossibility(courseId);
        lmsCourseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_COURSE_NOT_FOUND + courseId))
                .getCourse().setName(name);
    }

    @Transactional
    public void updateAccess(@NonNull final Long courseId, @NonNull final Long accessId) {
        checkModificationPossibility(courseId);
        lmsCourseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_COURSE_NOT_FOUND + courseId))
                .getCourse().setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void deleteById(@NonNull final Long courseId) {
        checkModificationPossibility(courseId);
        lmsCourseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_COURSE_NOT_FOUND + courseId))
                .getCourse().setDeleted(true);
    }

    private void checkModificationPossibility(@NonNull final Long courseId) {
        Optional<LMSCourse> optional = lmsCourseRepository.findForSecurityById(courseId);
        if (!optional.isPresent()) throw new EntityNotFoundException(LMS_COURSE_NOT_FOUND + courseId);
        accessChecker.checkModifyAccess(optional.get().getCourse().getAccess(), optional.get().getCourse().getStaff());
    }

    //-----------------------------------------------------One (for update)---------------------------------------------
    @Transactional(readOnly = true)
    public LMSCourseOutDto findByIdForUpdate(@NonNull final Long courseId) {
        return lmsCourseDtoTransformer.toDto(lmsCourseRepository.findForEditById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_COURSE_NOT_FOUND + courseId)));
    }

    //-------------------------------------------------------Staff table------------------------------------------------
    @Transactional(readOnly = true)
    public Page<LMSCourseOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(lmsCourseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LMSCourseOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), contains, pageable).map(lmsCourseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LMSCourseOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(lmsCourseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LMSCourseOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(lmsCourseDtoTransformer::toDto);
    }

    //---------------------------------------------------SLICE drop-down------------------------------------------------
    @Transactional(readOnly = true)
    public Slice<LMSCourseOutDto> findAllForDropDownByStaffId(@NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllForDropDownByStaffId(securityUtils.getAuthStaffId(), pageable).map(lmsCourseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<LMSCourseOutDto> findAllForDropDownByDepartmentId(@NonNull final Pageable pageable) {
        return lmsCourseRepository.findAllForDropDownByDepartmentId(securityUtils.getAuthDepId(), pageable).map(lmsCourseDtoTransformer::toDto);
    }

    //--------------------------------------------------------Admin-----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<LMSCourseOutDto> findAll(@NonNull final Pageable pageable) {
        return lmsCourseRepository.findAll(pageable).map(lmsCourseDtoTransformer::toDto);
    }
}
