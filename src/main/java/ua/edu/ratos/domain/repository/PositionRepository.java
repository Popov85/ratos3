package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Position;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
