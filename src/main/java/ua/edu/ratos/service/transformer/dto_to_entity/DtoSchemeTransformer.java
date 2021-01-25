package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import ua.edu.ratos.service.transformer.SchemeThemeTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Deprecated
@Component
@AllArgsConstructor
public class DtoSchemeTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final SecurityUtils securityUtils;

    private final SchemeThemeTransformer schemeThemeTransformer;

    @Transactional(propagation = Propagation.MANDATORY)
    public Scheme toEntity(@NonNull final SchemeInDto dto) {
        Scheme scheme = new Scheme();
        scheme.setSchemeId(dto.getSchemeId());
        scheme.setName(dto.getName());
        scheme.setActive(dto.isActive());
        scheme.setStrategy(em.getReference(Strategy.class, dto.getStrategyId()));
        scheme.setSettings(em.getReference(Settings.class, dto.getSettingsId()));
        scheme.setMode(em.getReference(Mode.class, dto.getModeId()));
        scheme.setOptions(em.getReference(Options.class, dto.getOptId()));
        scheme.setGrading(em.getReference(Grading.class, dto.getGradingId()));
        scheme.setCourse(em.getReference(Course.class, dto.getCourseId()));
        scheme.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        scheme.setDepartment(em.getReference(Department.class, securityUtils.getAuthDepId()));
        scheme.setAccess(em.getReference(Access.class, dto.getAccessId()));
        // themes
        if (dto.getThemes()==null || dto.getThemes().isEmpty())
            throw new RuntimeException("Failed to create Scheme: no themes");

        dto.getThemes().forEach(t->{
            SchemeTheme schemeTheme = schemeThemeTransformer.toEntity(t);
            scheme.addSchemeTheme(schemeTheme);
        });

        // groups
        if (dto.getGroups()!=null && !dto.getGroups().isEmpty()) {
            dto.getGroups().forEach(groupId->scheme.addGroup(em.getReference(Group.class, groupId)));
        }

        return scheme;
    }
}
