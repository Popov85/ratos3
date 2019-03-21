package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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
import ua.edu.ratos.service.dto.in.LMSCourseInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class DtoLMSCourseTransformer {

    @PersistenceContext
    private EntityManager em;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public LMSCourse toEntity(@NonNull final LMSCourseInDto dto) {
        LMSCourse lmsCourse = new LMSCourse();
        // cascade doGameProcessing
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setName(dto.getName());
        course.setCreated(LocalDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        lmsCourse.setCourse(course);
        lmsCourse.setLms(em.getReference(LMS.class, dto.getLmsId()));
        return lmsCourse;
    }
}
