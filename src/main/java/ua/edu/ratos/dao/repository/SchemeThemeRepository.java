package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SchemeTheme;
import java.util.Set;

public interface SchemeThemeRepository extends JpaRepository<SchemeTheme, Long> {

    @Query(value = "SELECT s FROM SchemeTheme s join fetch s.settings join s.scheme sh where sh.schemeId = ?1")
    SchemeTheme findForDtoById(Long schemeId);

    @Query(value = "SELECT s FROM SchemeTheme s join fetch s.settings join s.scheme sh where sh.schemeId = ?1")
    Set<SchemeTheme> findAllBySchemeId(Long schemeId);
}
