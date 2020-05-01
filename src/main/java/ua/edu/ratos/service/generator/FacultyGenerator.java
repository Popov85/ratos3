package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.dao.repository.FacultyRepository;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"dev"})
public class FacultyGenerator {

    @Autowired
    private Rnd rnd;

    @Autowired
    private FacultyRepository facultyRepository;

    @TrackTime
    @Transactional
    public List<Faculty> generate(int quantity, List<Organisation> list) {
        List<Faculty> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Organisation org;
            if (list.size() == 1) {
                org = list.get(0);
            } else {
                org = list.get(rnd.rnd(0, list.size()));
            }
            Faculty fac = createOne(i, org);
            results.add(fac);
        }
        facultyRepository.saveAll(results);
        return results;
    }

    private Faculty createOne(int i, Organisation organisation) {
        Faculty fac = new Faculty();
        fac.setName("Faculty #"+i);
        fac.setOrganisation(organisation);
        return fac;
    }

}
