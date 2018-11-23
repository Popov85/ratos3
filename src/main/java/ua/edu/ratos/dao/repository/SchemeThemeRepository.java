package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SchemeTheme;

public interface SchemeThemeRepository extends JpaRepository<SchemeTheme, Long> {
}
