package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.dao.repository.OrganisationRepository;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrganisationGenerator {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Transactional
    public List<Organisation> generate(int quantity) {
        List<Organisation> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Organisation org = createOne(i);
            results.add(org);
        }
        organisationRepository.saveAll(results);
        return results;
    }

    private Organisation createOne(int i) {
        Organisation org = new Organisation();
        org.setName("Organisation #"+i);
        return org;
    }
}
