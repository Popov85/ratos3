package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.OptionsInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@AllArgsConstructor
public class DtoOptionsTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final ModelMapper modelMapper;

    private final SecurityUtils securityUtils;

    @Transactional(propagation = Propagation.MANDATORY)
    public Options toEntity(@NonNull final OptionsInDto dto) {
        Options settings = modelMapper.map(dto, Options.class);
        settings.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        settings.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        return settings;
    }
}
