package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.repository.CourseRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CourseGenerator {
    @Autowired
    private Rnd rnd;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public List<Course> generate(int quantity, List<Department> list) {
        List<Course> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Department dep = list.get(rnd.rnd(0, list.size() - 1));
            Course course = createOne(i, dep);
            result.add(course);
        }
        courseRepository.saveAll(result);
        return result;
    }

    private Course createOne(int i, Department department) {
        Course course = new Course();
        course.setName("Course #"+i);
        course.setCreated(LocalDateTime.now());
        course.setAccess(em.getReference(Access.class, rnd.rndOne(2)));
        course.setStaff(em.getReference(Staff.class, 1L));
        course.setDepartment(department);
        return course;
    }
}
