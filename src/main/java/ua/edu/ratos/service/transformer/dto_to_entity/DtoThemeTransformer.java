package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.ThemeInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoThemeTransformer {

    @PersistenceContext
    private EntityManager em;

    private ModelMapper modelMapper;

    private SecurityUtils securityUtils;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Theme toEntity(@NonNull final ThemeInDto dto) {
        Theme theme = modelMapper.map(dto, Theme.class);
        theme.setCourse(em.getReference(Course.class, dto.getCourseId()));
        theme.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        return theme;
    }
}
