package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.SchemeTheme;

public interface SchemeThemeRepository extends JpaRepository<SchemeTheme, Long> {
}
