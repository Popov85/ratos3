package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;

public interface SchemeTwoPointRepository extends JpaRepository<SchemeTwoPoint, Long> {

    @Query(value = "SELECT s FROM SchemeTwoPoint s join fetch s.twoPointGrading where s.schemeId =?1")
    SchemeTwoPoint findForDtoById(Long schemeId);
}
