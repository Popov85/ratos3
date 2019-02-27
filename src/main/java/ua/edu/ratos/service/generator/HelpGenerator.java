package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.repository.HelpRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelpGenerator {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private Rnd rnd;

    @TrackTime
    @Transactional
    public List<Help> generate(int quantity, List<Resource> resources) {
        List<Help> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Resource resource = resources.get(rnd.rnd(0, resources.size() - 1));
            Help help = createOne(i, resource);
            result.add(help);
        }
        helpRepository.saveAll(result);
        return result;
    }

    private Help createOne(int number, Resource resource) {
        Help help = new Help();
        help.setName("Help name "+number);
        help.setHelp("Help content "+number);
        help.addResource(em.getReference(Resource.class, resource.getResourceId()));
        help.setStaff(em.getReference(Staff.class, 1L));
        return help;
    }
}
