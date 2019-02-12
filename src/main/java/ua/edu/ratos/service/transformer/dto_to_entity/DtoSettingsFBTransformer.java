package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Language;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SettingsFBInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoSettingsFBTransformer {

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
    public SettingsFB toEntity(@NonNull final SettingsFBInDto dto) {
        SettingsFB settings = modelMapper.map(dto, SettingsFB.class);
        settings.setLang(em.getReference(Language.class, dto.getLang()));
        settings.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        return settings;
    }
}
