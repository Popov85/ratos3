package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.in.ModeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoModeTransformer {
    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Mode toEntity(@NonNull ModeInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Mode toEntity(Long modeId, @NonNull ModeInDto dto) {
        Mode mode = modelMapper.map(dto, Mode.class);
        mode.setModeId(modeId);
        mode.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        return mode;
    }
}
