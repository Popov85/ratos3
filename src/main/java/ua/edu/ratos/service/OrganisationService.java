package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.repository.OrganisationRepository;

@Slf4j
@Service
public class OrganisationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public void setOrganisationRepository(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public void deleteById(@NonNull final Long orgId) {
        log.warn("Organisation is to be removed, orgId= {}", orgId);
        organisationRepository.findById(orgId).get().setDeleted(true);
    }

}
