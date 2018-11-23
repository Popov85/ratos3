package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.entity.ModeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoModeTransformer {
    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Mode fromDto(@NonNull ModeInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Mode fromDto(Long modeId, @NonNull ModeInDto dto) {
        Mode mode = modelMapper.map(dto, Mode.class);
        mode.setModeId(modeId);
        mode.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        return mode;
    }
}
