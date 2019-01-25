package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.Grading;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Generator of Schemes for performance testing only!
 * Given:
 * 10 Themes;
 * N [100-1000] Schemes;
 * 1-3 themes associated with a Scheme;
 * 1-5 settings associated with a SchemeTheme;
 *
 *
 */
@Component
public class SchemesGenerator {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeGradingManagerService gradingManagerService;

    @Autowired
    private EntityManager em;

    @TrackTime
    @Transactional
    public void generate(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            Scheme scheme = createOne(i);
            schemeRepository.save(scheme);
            gradingManagerService.save(i, scheme.getGrading().getGradingId(), 1L);
        }
    }

    private Scheme createOne(int index) {
        Scheme scheme = new Scheme();
        scheme.setName("Scheme #"+index);
        scheme.setStaff(em.getReference(Staff.class, 1L));
        scheme.setCourse(em.getReference(Course.class, 1L));
        scheme.setGrading(em.getReference(Grading.class, rnd(3)));
        scheme.setSettings(em.getReference(Settings.class, 1L));
        scheme.setStrategy(em.getReference(Strategy.class, rnd(3)));
        scheme.setMode(em.getReference(Mode.class, rnd(2)));
        scheme.setAccess(em.getReference(Access.class, rnd(2)));
        scheme.setCreated(LocalDateTime.now());
        scheme.setActive(true);
        scheme.setThemes(getThemes(3, scheme));
        scheme.setGroups(getGroups(4));
        return scheme;
    }

    private long rnd(int bound) {
        Random r = new Random();
        return r.ints(1, bound).findFirst().getAsInt();
    }

    private List<SchemeTheme> getThemes(int quantity, Scheme scheme) {
        List<SchemeTheme> result = new ArrayList<>(quantity);
        for (int i = 0; i <= rnd(quantity); i++) {
            SchemeTheme s = new SchemeTheme();
            s.setScheme(scheme);
            s.setTheme(em.getReference(Theme.class, rnd(10)));
            s.setSettings(getSettings(s));
            result.add(s);
        }
        return result;
    }

    private Set<SchemeThemeSettings> getSettings(SchemeTheme schemeTheme) {
        Set<SchemeThemeSettings> result = new HashSet<>();
        for (int i = 1; i <= rnd(5); i++) {
            SchemeThemeSettings s = new SchemeThemeSettings();
            s.setSchemeTheme(schemeTheme);
            s.setType(em.getReference(QuestionType.class, rnd(5)));
            s.setLevel1((short) rnd(20));
            s.setLevel2((short) rnd(20));
            s.setLevel3((short) rnd(20));
            result.add(s);
        }
        return result;
    }

    private Set<Group> getGroups(int quantity) {
        Set<Group> groups = new HashSet<>();
        for (int i = 1; i <= rnd(quantity); i++) {
            Group g = em.getReference(Group.class, rnd(10));
            groups.add(g);
        }
        return groups;
    }

}
