package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Theme;
import java.util.Set;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query(value = "SELECT t FROM Theme t join t.course c where c.courseId =?1 order by t.name desc")
    Set<Theme> findAllByCourseId(Long courseId);

    @Query(value = "SELECT t FROM Theme t join t.course c join c.department d where d.depId =?1 order by t.name desc")
    Page<Theme> findByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join t.course c join c.department d join d.faculty f where f.facId =?1 order by t.name desc")
    Page<Theme> findByFacultyId(Long facId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join t.course c join c.department d join d.faculty f join f.organisation o where o.orgId =?1 order by t.name desc")
    Page<Theme> findByOrganisationId(Long orgId, Pageable pageable);

}
