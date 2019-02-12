package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.repository.CourseRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.CourseInDto;
import ua.edu.ratos.service.dto.out.CourseOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoCourseTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.CourseDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Requested Course not found, courseId = ";

    @PersistenceContext
    private EntityManager em;

    private CourseRepository courseRepository;

    private DtoCourseTransformer dtoCourseTransformer;

    private CourseDtoTransformer courseDtoTransformer;

    private AccessChecker accessChecker;

    private SecurityUtils securityUtils;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setDtoCourseTransformer(DtoCourseTransformer dtoCourseTransformer) {
        this.dtoCourseTransformer = dtoCourseTransformer;
    }

    @Autowired
    public void setCourseDtoTransformer(CourseDtoTransformer courseDtoTransformer) {
        this.courseDtoTransformer = courseDtoTransformer;
    }

    @Autowired
    public void setAccessChecker(AccessChecker accessChecker) {
        this.accessChecker = accessChecker;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //-----------------------------------------------------CRUD---------------------------------------------------------

    @Transactional
    public Long save(@NonNull final CourseInDto dto) {
        return dtoCourseTransformer.toEntity(dto).getCourseId();
    }

    @Transactional
    public void updateName(@NonNull final Long courseId, @NonNull final String name) {
        checkModificationPossibility(courseId);
        courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(COURSE_NOT_FOUND + courseId))
                .setName(name);
    }

    @Transactional
    public void updateAccess(@NonNull final Long courseId, @NonNull final Long accessId) {
        checkModificationPossibility(courseId);
        courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(COURSE_NOT_FOUND + courseId))
                .setAccess(em.getReference(Access.class, accessId));
    }

    @Transactional
    public void deleteById(@NonNull final Long courseId) {
        checkModificationPossibility(courseId);
        courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(COURSE_NOT_FOUND + courseId))
                .setDeleted(true);
    }

    private void checkModificationPossibility(@NonNull final Long courseId) {
        Course course = courseRepository.findForSecurityById(courseId);
        accessChecker.checkModifyAccess(course.getAccess(), course.getStaff());
    }


    //-----------------------------------------------------One (for update)---------------------------------------------

    @Transactional(readOnly = true)
    public CourseOutDto findByIdForUpdate(@NonNull final Long courseId) {
        return courseDtoTransformer.toDto(courseRepository.findForEditById(courseId));
    }

    //---------------------------------------------------SELECT (for tables)--------------------------------------------

    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return courseRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(courseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return courseRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), contains, pageable).map(courseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return courseRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(courseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return courseRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(courseDtoTransformer::toDto);
    }
    
    
    //-------------------------------------------------Admin (for simple table)-----------------------------------------
    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAll(@NonNull final Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseDtoTransformer::toDto);
    }
}
