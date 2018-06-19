package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Organisation;

public interface OrganisationRepository extends CrudRepository<Organisation, Long> {
}
