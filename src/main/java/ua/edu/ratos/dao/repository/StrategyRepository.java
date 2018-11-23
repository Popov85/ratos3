package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Strategy;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
}
