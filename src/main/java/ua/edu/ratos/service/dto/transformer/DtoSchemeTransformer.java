package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.Grading;
import ua.edu.ratos.service.dto.entity.SchemeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSchemeTransformer {

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public Scheme fromDto(@NonNull SchemeInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Scheme fromDto(Long schemeId, @NonNull SchemeInDto dto) {
        Scheme scheme = new Scheme();
        scheme.setSchemeId(schemeId);
        scheme.setName(dto.getName());
        scheme.setActive(dto.isActive());
        scheme.setCompleted(false);
        scheme.setStrategy(em.getReference(Strategy.class, dto.getStrategyId()));
        scheme.setSettings(em.getReference(Settings.class, dto.getSettingsId()));
        scheme.setMode(em.getReference(Mode.class, dto.getModeId()));
        scheme.setGrading(em.getReference(Grading.class, dto.getGradingId()));
        scheme.setCourse(em.getReference(Course.class, dto.getCourseId()));
        scheme.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        return scheme;
    }
}
