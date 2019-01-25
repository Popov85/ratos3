package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.Grading;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSchemeTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private SecurityUtils securityUtils;

    @Transactional(propagation = Propagation.MANDATORY)
    public Scheme toEntity(@NonNull final SchemeInDto dto) {
        Scheme scheme = new Scheme();
        scheme.setName(dto.getName());
        scheme.setActive(dto.isActive());
        scheme.setStrategy(em.getReference(Strategy.class, dto.getStrategyId()));
        scheme.setSettings(em.getReference(Settings.class, dto.getSettingsId()));
        scheme.setMode(em.getReference(Mode.class, dto.getModeId()));
        scheme.setGrading(em.getReference(Grading.class, dto.getGradingId()));
        scheme.setCourse(em.getReference(Course.class, dto.getCourseId()));
        // SecurityUtils in cation
        scheme.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        scheme.setAccess(em.getReference(Access.class, dto.getAccessId()));
        // themes
        if (dto.getThemes()==null || dto.getThemes().isEmpty())
            throw new RuntimeException("Failed to create Scheme: no themes");
        dto.getThemes().forEach(t->{
            SchemeTheme st = new SchemeTheme();
            st.setTheme(em.getReference(Theme.class, t.getThemeId()));
            st.setOrder(t.getOrder());
            if (t.getSettings()==null || t.getSettings().isEmpty())
                throw new RuntimeException("Failed to create Scheme: theme has no settings");
            t.getSettings().forEach(s-> {
                SchemeThemeSettings sts = new SchemeThemeSettings();
                sts.setType(em.getReference(QuestionType.class, s.getQuestionTypeId()));
                sts.setLevel1(s.getLevel1());
                sts.setLevel2(s.getLevel2());
                sts.setLevel3(s.getLevel3());
                st.addSchemeThemeSettings(sts);
            });
            scheme.addSchemeTheme(st);
        });

        // groups
        if (dto.getGroups()!=null && !dto.getGroups().isEmpty()) {
            dto.getGroups().forEach(groupId->scheme.addGroup(em.getReference(Group.class, groupId)));
        }

        return scheme;
    }
}
