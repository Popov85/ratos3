package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Mode;

public interface ModeRepository extends JpaRepository<Mode, Long> {
}
