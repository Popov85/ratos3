package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.domain.entity.Scheme;

import javax.persistence.QueryHint;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = "SELECT s FROM Scheme s left join fetch s.schemeThemes t where s.schemeId = ?1")
    Scheme findByIdWithThemes(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.strategy left join fetch s.schemeThemes st left join fetch st.schemeThemeSettings where s.schemeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Scheme findByIdForSession(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s left join fetch s.grading t where s.schemeId = ?1")
    Scheme findByIdWithGrading(Long schemeId);

}
