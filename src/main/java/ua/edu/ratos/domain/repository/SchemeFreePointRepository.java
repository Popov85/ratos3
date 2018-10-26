package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.grade.SchemeFreePoint;

public interface SchemeFreePointRepository extends JpaRepository<SchemeFreePoint, Long> {
}
