package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Scheme;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = "SELECT s FROM Scheme s left join fetch s.schemeThemes t where s.schemeId = ?1")
    Scheme findByIdWithThemes(Long schemeId);



   /* @Query(value="select s from Scheme s join fetch s.course c join fetch c.department d join fetch d.faculty f join fetch d.organisation o where t.themeViewId.orgId = ?1")
    Page<Scheme> findAllByOrganisationId(Long orgId, Pageable pageable);*/

}
