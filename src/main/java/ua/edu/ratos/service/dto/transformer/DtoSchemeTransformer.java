package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.*;
import ua.edu.ratos.service.dto.entity.SchemeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSchemeTransformer {

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public Scheme fromDto(@NonNull SchemeInDto dto) {
        Scheme scheme = new Scheme();
        scheme.setSchemeId(dto.getSchemeId());
        scheme.setName(dto.getName());
        scheme.setActive(dto.isActive());
        scheme.setCompleted(false);
        scheme.setStrategy(em.getReference(Strategy.class, dto.getStrategyId()));
        scheme.setSettings(em.getReference(Settings.class, dto.getSettingsId()));
        scheme.setMode(em.getReference(Mode.class, dto.getModeId()));
        scheme.setCourse(em.getReference(Course.class, dto.getCourseId()));
        scheme.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        return scheme;
    }
}
