package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.repository.ResultRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResultsGenerator {

    @Autowired
    private Rnd rnd;

    @Autowired
    private ResultRepository resultRepository;

    @Transactional
    public List<Result> generate(int quantity, List<Student> students, List<Scheme> schemes) {
        List<Result> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Student student = students.get(rnd.rnd(0, students.size() - 1));
            Scheme scheme = schemes.get(rnd.rnd(0, schemes.size() - 1));
            Result result = createOne(student, scheme);
            results.add(result);
        }
        resultRepository.saveAll(results);
        return results;
    }

    private Result createOne(Student stud, Scheme scheme) {
        Result result = new Result();
        result.setScheme(scheme);
        result.setUser(stud.getUser());
        result.setDepartment(scheme.getDepartment());
        result.setGrade(5);
        result.setPassed(true);
        result.setPercent(rnd.rnd(80, 100));
        result.setSessionEnded(LocalDateTime.now().minusMinutes(rnd.rnd(1000, 10000)));
        result.setTimeOuted(false);
        result.setCancelled(false);
        result.setSessionLasted(rnd.rnd(100, 1500));
        return result;
    }

}
