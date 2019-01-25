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
import ua.edu.ratos.service.dto.in.CourseInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoCourseTransformer {

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toEntity(@NonNull final CourseInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toEntity(final Long courseId, @NonNull final CourseInDto dto) {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCreated(LocalDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        course.setDepartment(em.getReference(Department.class, dto.getDepId()));
        return course;
    }
}
