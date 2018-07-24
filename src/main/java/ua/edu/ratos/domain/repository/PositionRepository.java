package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
