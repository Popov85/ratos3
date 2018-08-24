package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Theme;
import java.util.Set;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query(value = "SELECT t FROM Theme t join t.course c where c.courseId =?1 order by t.name desc")
    Set<Theme> findAllByCourseId(Long courseId);

    @Query(value = "SELECT t FROM Theme t join t.course c join c.department d where d.depId =?1 order by t.name desc")
    Set<Theme> findAllByDepartmentId(Long depId);

    @Query(value = "SELECT t FROM Theme t join t.course c join c.department d join d.organisation o where o.orgId =?1 order by t.name desc")
    Page<Theme> findByOrganisationId(Long orgId, Pageable pageable);

    @Modifying
    @Query("update Theme t set t.deleted = true where t.themeId = ?1")
    void pseudoDeleteById(Long themeId);
}