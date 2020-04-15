package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.dao.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"dev", "demo"})
public class DepartmentGenerator {

    @Autowired
    private Rnd rnd;

    @Autowired
    private DepartmentRepository departmentRepository;

    @TrackTime
    @Transactional
    public List<Department> generate(int quantity, List<Faculty> list) {
        List<Department> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Faculty fac;
            if (list.size() == 1) {
                fac = list.get(0);
            } else {
                fac = list.get(rnd.rnd(0, list.size()));
            }
            Department dep = createOne(i, fac);
            results.add(dep);
        }
        departmentRepository.saveAll(results);
        return results;
    }

    private Department createOne(int i, Faculty faculty) {
        Department dep = new Department();
        dep.setName("Department #"+i);
        dep.setFaculty(faculty);
        return dep;
    }
}
