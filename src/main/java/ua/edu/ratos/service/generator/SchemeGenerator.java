package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.Grading;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.service.grading.SchemeGradingManagerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Generator of Schemes for performance testing only!
 * Given:
 * N Themes;
 * 1-3 themes associated with a Scheme;
 * 1-5 settings associated with a SchemeTheme;
 */
@Component
public class SchemeGenerator {

    @Autowired
    private Rnd rnd;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeGradingManagerService gradingManagerService;

    @Transactional
    public List<Scheme> generate(int quantity, List<Theme> themes, List<Department> departments, List<Course> courses) {
        List<Scheme> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Department department = departments.get(rnd.rnd(0, departments.size() - 1));
            Course course = courses.get(rnd.rnd(0, courses.size() - 1));
            Scheme scheme = createOne(i, themes, department, course);
            schemeRepository.save(scheme);
            gradingManagerService.save(i, scheme.getGrading().getGradingId(), 1L);
            result.add(scheme);
        }
        return result;
    }

    private Scheme createOne(int i, List<Theme> themes, Department department, Course course) {
        Scheme scheme = new Scheme();
        scheme.setName("Scheme_#"+i);
        scheme.setStaff(em.getReference(Staff.class, 1L));
        scheme.setDepartment(department);
        scheme.setCourse(course);
        scheme.setGrading(em.getReference(Grading.class, rnd.rndOne(3)));
        scheme.setSettings(em.getReference(Settings.class, 1L));
        scheme.setStrategy(em.getReference(Strategy.class, rnd.rndOne(3)));
        scheme.setMode(em.getReference(Mode.class, rnd.rndOne(2)));
        scheme.setAccess(em.getReference(Access.class, rnd.rndOne(2)));
        scheme.setCreated(LocalDateTime.now().minusDays(rnd.rnd(1, 1000)));
        scheme.setActive(true);
        scheme.setThemes(getThemes(3, themes, scheme));
        //scheme.setGroups(getGroups(4));
        return scheme;
    }


    private List<SchemeTheme> getThemes(int quantity, List<Theme> themes, Scheme scheme) {
        List<SchemeTheme> result = new ArrayList<>(quantity);
        for (int i = 0; i <= rnd.rndOne(quantity); i++) {
            SchemeTheme s = new SchemeTheme();
            s.setScheme(scheme);
            s.setTheme(themes.get(rnd.rnd(0, themes.size()-1)));
            s.setSettings(getSettings(s));
            result.add(s);
        }
        return result;
    }

    private Set<SchemeThemeSettings> getSettings(SchemeTheme schemeTheme) {
        Set<SchemeThemeSettings> result = new HashSet<>();
        for (int i = 1; i <= rnd.rndOne(5); i++) {
            SchemeThemeSettings s = new SchemeThemeSettings();
            s.setSchemeTheme(schemeTheme);
            s.setType(em.getReference(QuestionType.class, rnd.rndOne(5)));
            s.setLevel1((short) rnd.rndOne(20));
            s.setLevel2((short) rnd.rndOne(20));
            s.setLevel3((short) rnd.rndOne(20));
            result.add(s);
        }
        return result;
    }

 /*   private Set<Group> getGroups(int quantity) {
        Set<Group> groups = new HashSet<>();
        for (int i = 1; i <= rnd.rndOne(quantity); i++) {
            Group g = em.getReference(Group.class, rnd.rndOne(10));
            groups.add(g);
        }
        return groups;
    }*/

}
