package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.dao.repository.CourseRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.CourseInDto;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;
import ua.edu.ratos.service.dto.out.CourseMinOutDto;
import ua.edu.ratos.service.dto.out.CourseOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoCourseTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.CourseDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.CourseMinDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Requested Course not found, courseId = ";

    @PersistenceContext
    private final EntityManager em;

    private final CourseRepository courseRepository;

    private final DtoCourseTransformer dtoCourseTransformer;

    private final CourseDtoTransformer courseDtoTransformer;

    private final CourseMinDtoTransformer courseMinDtoTransformer;

    private final AccessChecker accessChecker;

    private final SecurityUtils securityUtils;


    @Transactional
    public CourseOutDto save(@NonNull final CourseInDto dto) {
        Course course = dtoCourseTransformer.toEntity(dto);
        Course result = courseRepository.save(course);
        return courseDtoTransformer.toDto(result);
    }

    @Transactional
    public CourseOutDto save(@NonNull final LMSCourseInDto dto) {
        Course course = dtoCourseTransformer.toLMSEntity(dto);
        Course result = courseRepository.save(course);
        return courseDtoTransformer.toDto(result);
    }

    @Transactional
    public CourseOutDto update(@NonNull final CourseInDto dto) {
        if (dto.getCourseId()==null)
            throw new RuntimeException("Failed to update, courseId is nullable!");
        Course course = checkModificationPossibility(dto.getCourseId());
        course = dtoCourseTransformer.toEntity(course, dto);
        Course result = courseRepository.save(course);
        return courseDtoTransformer.toDto(result);
    }

    @Transactional
    public CourseOutDto update(@NonNull final LMSCourseInDto dto) {
        if (dto.getCourseId()==null)
            throw new RuntimeException("Failed to update LMS course, courseId is nullable!");
        Course course = checkModificationPossibility(dto.getCourseId());
        course = dtoCourseTransformer.toLMSEntity(course, dto);
        Course result = courseRepository.save(course);
        return courseDtoTransformer.toDto(result);
    }

    @Transactional
    public void updateName(@NonNull final Long courseId, @NonNull final String name) {
        Course course = checkModificationPossibility(courseId);
        course.setName(name);
    }

    @Transactional
    public void associateWithLMS(@NonNull final Long courseId, @NonNull final Long lmsId) {
        Course course = checkModificationPossibility(courseId);
        LMSCourse lmsCourse = new LMSCourse();
        lmsCourse.setCourseId(courseId);
        lmsCourse.setCourse(course);
        lmsCourse.setLms(em.getReference(LMS.class, lmsId));
        course.setLmsCourse(lmsCourse);
    }

    @Transactional
    public void disassociateWithLMS(@NonNull final Long courseId) {
        Course course = checkModificationPossibility(courseId);
        LMSCourse lmsCourse = course.getLmsCourse();
        lmsCourse.setCourse(null);
        course.setLmsCourse(null);
    }

    @Transactional
    public void deleteById(@NonNull final Long courseId) {
        Course course = checkModificationPossibility(courseId);
        courseRepository.delete(course);
    }

    @Transactional
    public void deleteByIdSoft(@NonNull final Long courseId) {
        Course course = checkModificationPossibility(courseId);
        course.setDeleted(true);
    }

    private Course checkModificationPossibility(@NonNull final Long courseId) {
        Course course= courseRepository.findForSecurityById(courseId)
                .orElseThrow(()->new EntityNotFoundException(COURSE_NOT_FOUND + courseId));
        accessChecker.checkModifyAccess(course.getAccess(), course.getStaff());
        return course;
    }

    //-------------------------------------------------Staff (min for drop down)----------------------------------------
    @Transactional(readOnly = true)
    public Set<CourseMinOutDto> findAllForDropdownByStaffId() {
        return courseRepository.findAllForDropDownByStaffId(securityUtils.getAuthStaffId())
                .stream()
                .map(courseMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<CourseMinOutDto> findAllForDropdownByDepartmentId() {
        return courseRepository.findAllForDropDownByDepartmentId(securityUtils.getAuthDepId())
                .stream()
                .map(courseMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public Set<CourseMinOutDto> findAllForDropdownByDepartmentId(@NonNull final Long depId) {
        return courseRepository.findAllForDropDownByDepartmentId(depId)
                .stream()
                .map(courseMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //---------------------------------------------------Staff (table)--------------------------------------------------
    @Transactional(readOnly = true)
    public Set<CourseOutDto> findAllForTableByDepartment() {
        return courseRepository.findAllForTableByDepartmentId(securityUtils.getAuthDepId())
                .stream()
                .map(courseDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<CourseOutDto> findAllForTableByDepartmentId(@NonNull Long depId) {
        return courseRepository.findAllForTableByDepartmentId(depId)
                .stream()
                .map(courseDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    //-----------------------------------------------Admin (for simple table)-------------------------------------------
    @Transactional(readOnly = true)
    public Page<CourseOutDto> findAll(@NonNull final Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseDtoTransformer::toDto);
    }
}
