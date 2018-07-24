package ua.edu.ratos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.repository.OrganisationRepository;

@Slf4j
@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    // Warning: this method is non-transactional for Exception handling purposes
    public void deleteById(Long orgId) {
        log.warn("Organisation is to be removed, Id= {}", orgId);
        organisationRepository.pseudoDeleteById(orgId);
    }


}
