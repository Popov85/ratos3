package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
