package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.ThemeRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThemeGenerator {

    @Autowired
    private Rnd rnd;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ThemeRepository themeRepository;

    @Transactional
    public List<Theme> generate(int quantity, List<Course> list) {
        List<Theme> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Course course = list.get(rnd.rnd(0, list.size() - 1));
            Theme theme = createOne(i, course);
            result.add(theme);
        }
        themeRepository.saveAll(result);
        return result;
    }

    private Theme createOne(int i, Course course) {
        Theme theme = new Theme();
        theme.setName("Theme #"+i);
        theme.setAccess(em.getReference(Access.class, rnd.rndOne(2)));
        theme.setStaff(em.getReference(Staff.class, 1L));
        theme.setCourse(course);
        return theme;
    }
}
