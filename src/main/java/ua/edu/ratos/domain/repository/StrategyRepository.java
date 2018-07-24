package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Strategy;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
}
