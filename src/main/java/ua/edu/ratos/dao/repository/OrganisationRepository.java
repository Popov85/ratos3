package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Organisation;

import java.util.Set;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query(value = "select new Organisation(o.orgId, o.name) from Organisation o")
    Set<Organisation> findAllForDropDown();

    @Query(value = "select o from Organisation o")
    Set<Organisation> findAllForManagement();

}
