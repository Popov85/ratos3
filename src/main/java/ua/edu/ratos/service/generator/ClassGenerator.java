package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.dao.repository.ClazzRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"dev", "demo"})
public class ClassGenerator {

    @Autowired
    private Rnd rnd;

    @Autowired
    private ClazzRepository classRepository;

    @Transactional
    public List<Clazz> generate(int quantity, List<Faculty> list) {
        List<Clazz> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Faculty fac;
            if (list.size() == 1) {
                fac = list.get(0);
            } else {
                fac = list.get(rnd.rnd(0, list.size()));
            }
            Clazz cl = createOne(i, fac);
            results.add(cl);
        }
        classRepository.saveAll(results);
        return results;
    }

    private Clazz createOne(int i, Faculty faculty) {
        Clazz cl = new Clazz();
        cl.setName("Class #"+i);
        cl.setFaculty(faculty);
        return cl;
    }
}
