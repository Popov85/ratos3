package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grade.SchemeFreePoint;

public interface SchemeFreePointRepository extends JpaRepository<SchemeFreePoint, Long> {

    @Query(value = "SELECT s FROM SchemeFreePoint s join fetch s.freePointGrading where s.schemeId =?1")
    SchemeFreePoint findForDtoById(Long schemeId);
}
