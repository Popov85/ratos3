package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.GroupInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class DtoGroupTransformer {

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
    public Group toEntity(@NonNull final GroupInDto dto) {
        Group group = modelMapper.map(dto, Group.class);
        group.setCreated(LocalDateTime.now());
        group.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        return group;
    }
}
