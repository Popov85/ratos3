package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Organisation;

import java.util.Set;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query(value = "select o from Organisation o where o.deleted=0")
    Set<Organisation> findAllForRegistration();

    @Query(value = "select o from Organisation o where o.deleted=0")
    Slice<Organisation> findAllForAdministration(Pageable pageable);
}
