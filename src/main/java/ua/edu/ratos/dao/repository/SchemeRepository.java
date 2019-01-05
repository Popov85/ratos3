package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.Scheme;

import javax.persistence.QueryHint;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = "SELECT s FROM Scheme s left join fetch s.themes t where s.schemeId = ?1")
    Scheme findByIdWithThemes(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.strategy join fetch s.grading left join fetch s.themes st left join fetch st.schemeThemeSettings left join fetch s.groups g left join fetch g.group gg left join fetch gg.students where s.schemeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Scheme findByIdForSession(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s left join fetch s.grading t where s.schemeId = ?1")
    Scheme findByIdWithGrading(Long schemeId);

}
