package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Class;
import ua.edu.ratos.dao.repository.StudentRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Component
public class StudentsGenerator {

    private static final int[] YEAR = {2016, 2017, 2018, 2019};

    @Autowired
    private Rnd rnd;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudentRepository studentRepository;

    @TrackTime
    @Transactional
    public List<Student> generate(int quantity, List<Class> list) {
        List<Role> roles =  Arrays.asList(em.getReference(Role.class, 2L));
        List<Student> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Class cl;
            if (list.size() == 1) {
                cl = list.get(0);
            } else {
                cl = list.get(rnd.rnd(0, list.size() - 1));
            }
            Student stud = createOne(i, cl, roles);
            results.add(stud);
        }
        studentRepository.saveAll(results);
        return results;
    }

    private Student createOne(int i, Class cl, List<Role> roles) {
        Student stud = new Student();
        User user = new User();
        user.setName("name"+i);
        user.setSurname("surname"+i);
        user.setEmail(user.getName()+"."+user.getSurname()+"@example.com");
        user.setPassword(("name&surname"+i).toCharArray());
        user.setActive(true);
        user.setRoles(new HashSet<>(roles));
        stud.setUser(user);
        stud.setEntranceYear(YEAR[rnd.rnd(0, 3)]);
        stud.setStudentClass(cl);
        return stud;
    }

}
