package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.dao.repository.OrganisationRepository;
import ua.edu.ratos.service.dto.in.OrganisationInDto;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoOrganisationTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.OrganisationMinDtoTransformer;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrganisationService {

    private static final String ORG_NOT_FOUND = "Requested organisation is not found, orgId = ";


    private final OrganisationRepository organisationRepository;

    private final DtoOrganisationTransformer dtoOrganisationTransformer;

    private final OrganisationMinDtoTransformer organisationMinDtoTransformer;


    @Transactional
    public Long save(@NonNull final OrganisationInDto dto) {
        Organisation organisation = dtoOrganisationTransformer.toEntity(dto);
        return organisationRepository.save(organisation).getOrgId();
    }

    @Transactional
    public void update(@NonNull final OrganisationInDto dto) {
        if (dto.getOrgId()==null)
            throw new RuntimeException("Failed to update, nullable orgId field");
        Organisation organisation = dtoOrganisationTransformer.toEntity(dto);
        organisationRepository.save(organisation);
        return;
    }

    @Transactional
    public void updateName(@NonNull final Long orgId, @NonNull final String name) {
        organisationRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException(ORG_NOT_FOUND + orgId))
                .setName(name);
    }

    @Transactional
    public void deleteById(@NonNull final Long orgId) {
        log.warn("Organisation is to be removed, orgId= {}", orgId);
        organisationRepository.deleteById(orgId);
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
