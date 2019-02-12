package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Position;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.StaffInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoStaffTransformer {

    @PersistenceContext
    private EntityManager em;

    private DtoUserTransformer dtoUserTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setDtoUserTransformer(DtoUserTransformer dtoUserTransformer) {
        this.dtoUserTransformer = dtoUserTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Staff toEntity(@NonNull final StaffInDto dto) {
        Staff staff = new Staff();
        staff.setStaffId(dto.getStaffId());
        staff.setUser(dtoUserTransformer.toEntity(dto.getUser()));
        staff.setPosition(em.getReference(Position.class, dto.getPositionId()));
        staff.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        return staff;
    }
}
