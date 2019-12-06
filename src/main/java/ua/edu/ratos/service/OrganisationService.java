package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.OrganisationRepository;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.OrganisationMinDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    private final OrganisationMinDtoTransformer organisationMinDtoTransformer;


    // TODO: CRUD fro GLOBAL-ADMIN!


    public void deleteById(@NonNull final Long orgId) {
        log.warn("Organisation is to be removed, orgId= {}", orgId);
        organisationRepository.findById(orgId).get().setDeleted(true);
    }


    @Transactional(readOnly = true)
    public Set<OrganisationMinOutDto> findAllOrganisationsForDropDown() {
        return organisationRepository
                .findAllForDropDown()
                .stream()
                .map(organisationMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

}
