package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grade.SchemeFourPoint;

public interface SchemeFourPointRepository extends JpaRepository<SchemeFourPoint, Long> {

    @Query(value = "SELECT s FROM SchemeFourPoint s join fetch s.fourPointGrading where s.schemeId =?1")
    SchemeFourPoint findForDtoById(Long schemeId);

}
