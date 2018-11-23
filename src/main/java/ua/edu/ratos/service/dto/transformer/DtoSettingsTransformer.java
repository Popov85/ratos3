package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.entity.SettingsInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSettingsTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Settings fromDto(@NonNull SettingsInDto dto) {
        Settings settings = modelMapper.map(dto, Settings.class);
        settings.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        return settings;
    }
}
