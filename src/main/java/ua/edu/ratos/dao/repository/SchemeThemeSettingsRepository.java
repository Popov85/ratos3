package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import java.util.Set;

public interface SchemeThemeSettingsRepository extends JpaRepository<SchemeThemeSettings, Long> {

    @Query(value = "SELECT s FROM SchemeThemeSettings s join s.schemeTheme t join fetch s.type where t.schemeThemeId = ?1")
    Set<SchemeThemeSettings> findAllBySchemeThemeId(Long schemeThemeId);
}
