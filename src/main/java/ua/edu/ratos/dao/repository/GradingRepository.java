package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.grading.Grading;

public interface GradingRepository extends JpaRepository<Grading, Long> {
}
