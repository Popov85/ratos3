package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.CourseInDto;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.OffsetDateTime;

@Component
@AllArgsConstructor
public class DtoCourseTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final SecurityUtils securityUtils;


    @Transactional(propagation = Propagation.MANDATORY)
    public Course toEntity(@NonNull final CourseInDto dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        // If it is a new one!
        if (dto.getCourseId() == null)
            course.setCreated(OffsetDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        course.setDeleted(!dto.isActive());
        return course;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toLMSEntity(@NonNull final LMSCourseInDto dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        // If it is a new one!
        if (dto.getCourseId() == null)
            course.setCreated(OffsetDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        course.setDeleted(!dto.isActive());
        // Add LMS
        LMSCourse lmsCourse = new LMSCourse();
        lmsCourse.setCourseId(dto.getCourseId());
        lmsCourse.setCourse(course);
        lmsCourse.setLms(em.getReference(LMS.class, dto.getLmsId()));
        course.setLmsCourse(lmsCourse);
        return course;
    }
    //---------------------------------------------Mutator--------------------------------------------------------------

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toEntity(@NonNull final Course course, @NonNull final CourseInDto dto) {
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        // If it is a new one!
        if (dto.getCourseId() == null)
            course.setCreated(OffsetDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        course.setDeleted(!dto.isActive());
        return course;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toLMSEntity(@NonNull final Course course, @NonNull final LMSCourseInDto dto) {
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        // If it is a new one!
        if (dto.getCourseId() == null)
            course.setCreated(OffsetDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        course.setDeleted(!dto.isActive());
        // Add LMS
        LMSCourse lmsCourse = new LMSCourse();
        lmsCourse.setCourseId(dto.getCourseId());
        lmsCourse.setCourse(course);
        lmsCourse.setLms(em.getReference(LMS.class, dto.getLmsId()));
        course.setLmsCourse(lmsCourse);
        return course;
    }
}
