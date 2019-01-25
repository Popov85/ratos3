package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query(value = "SELECT t FROM Theme t join fetch t.course join fetch t.access a join fetch t.staff s join fetch s.position where t.themeId =?1")
    Theme findForEditById(Long themeId);

    // For security purposes to check "modify"-access to a theme
    @Query(value = "SELECT t FROM Theme t join fetch t.access a join fetch t.staff s join fetch s.department d where t.themeId =?1")
    Theme findForSecurityById(Long themeId);

    // -----------------------Instructors (for themes table)----------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1")
    Page<Theme> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 and t.name like %?2% order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1 and t.name like %?2%")
    Page<Theme> findAllByStaffIdAndNameContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch t.course c join fetch t.access a where d.depId =?1 order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join s.department d where d.depId=?1")
    Page<Theme> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch t.course c join fetch t.access a where d.depId =?1 and t.name like %?2% order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join s.department d where d.depId=?1 and t.name like %?2%")
    Page<Theme> findAllByDepartmentIdAndNameContains(Long depId, String letters, Pageable pageable);

    // -----------------------Instructors (for questions table)----------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access left join fetch t.questions q left join fetch q.type where s.staffId =?1 order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1")
    Page<Theme> findAllForQuestionsTableByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access left join fetch t.questions q left join fetch q.type where s.staffId =?1 and t.name like %?2% order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1 and t.name like %?2%")
    Page<Theme> findAllForQuestionsTableByStaffIdAndNameContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch t.course c join fetch t.access left join fetch t.questions q left join fetch q.type where d.depId =?1 order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join s.department d where d.depId=?1")
    Page<Theme> findAllForQuestionsTableByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch t.course c join fetch t.access left join fetch t.questions q left join fetch q.type where d.depId =?1 and t.name like %?2% order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join s.department d where d.depId=?1 and t.name like %?2%")
    Page<Theme> findAllForQuestionsTableByDepartmentIdAndNameContains(Long depId, String letters, Pageable pageable);


    // ------------------------ADMIN-TEST ---------------------------------
    // (for simple table: no organisation, no faculty, no department data)
    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a order by t.name asc",
            countQuery = "SELECT count(t) FROM Theme t")
    Page<Theme> findAll(Pageable pageable);

}
