package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
