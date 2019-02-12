package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.CourseInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class DtoCourseTransformer {

    @PersistenceContext
    private EntityManager em;

    private SecurityUtils securityUtils;

    private ModelMapper modelMapper;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Course toEntity(@NonNull final CourseInDto dto) {
        Course course = modelMapper.map(dto, Course.class);
        course.setCreated(LocalDateTime.now());
        course.setAccess(em.getReference(Access.class, dto.getAccessId()));
        course.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        course.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        return course;
    }
}
