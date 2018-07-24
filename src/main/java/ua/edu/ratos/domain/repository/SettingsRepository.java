package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
