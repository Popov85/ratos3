package ua.edu.ratos.service.transformer.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SettingsInDto;
import ua.edu.ratos.service.transformer.SettingsTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@AllArgsConstructor
public class SettingsTransformerImpl implements SettingsTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final ModelMapper modelMapper;

    private final SecurityUtils securityUtils;

    @Override
    public Settings toEntity(@NonNull final SettingsInDto dto) {
        Settings settings = modelMapper.map(dto, Settings.class);
        settings.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        settings.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        return settings;
    }
}
