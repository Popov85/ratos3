package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Modifying
    @Transactional
    @Query("update Organisation o set o.deleted = true where o.orgId = ?1")
    void pseudoDeleteById(Long orgId);

}
