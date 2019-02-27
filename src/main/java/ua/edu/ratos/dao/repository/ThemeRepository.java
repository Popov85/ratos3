package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query(value = "SELECT t FROM Theme t join fetch t.course join fetch t.access a join fetch t.staff s join fetch s.position where t.themeId =?1")
    Theme findForEditById(Long themeId);

    // For security purposes to check "modify"-access to a theme
    @Query(value = "SELECT t FROM Theme t join fetch t.access a join fetch t.staff s join fetch t.department d where t.themeId =?1")
    Theme findForSecurityById(Long themeId);

    // ----------------------------------------------Instructors (table)------------------------------------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1")
    Page<Theme> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join t.department d where d.depId=?1")
    Page<Theme> findAllByDepartmentId(Long depId, Pageable pageable);

    //-------------------------------------------------Search (in table)------------------------------------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 and t.name like ?2%",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1 and t.name like ?2%")
    Page<Theme> findAllByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 and t.name like %?2%",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s where s.staffId=?1 and t.name like %?2%")
    Page<Theme> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1 and t.name like ?2%",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join t.department d where d.depId=?1 and t.name like ?2%")
    Page<Theme> findAllByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1 and t.name like %?2%",
            countQuery = "SELECT count(t) FROM Theme t join t.staff s join t.department d where d.depId=?1 and t.name like %?2%")
    Page<Theme> findAllByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);


    // ------------------------------------------------Slice (drop-down)------------------------------------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1")
    Slice<Theme> findAllForDropDownByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1")
    Slice<Theme> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    // -----------------------------------------------Search in drop-down-----------------------------------------------

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 and t.name like ?2%")
    Slice<Theme> findAllForDropDownByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access a where s.staffId =?1 and t.name like %?2%")
    Slice<Theme> findAllForDropDownByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1 and t.name like ?2%")
    Slice<Theme> findAllForDropDownByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.department d join fetch t.course c join fetch t.access a where d.depId =?1 and t.name like %?2%")
    Slice<Theme> findAllForDropDownByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);

    // -----------------------------------------------------ADMIN-TEST -------------------------------------------------
    // (for simple table: no organisation, no faculty, no department data)

    @Query(value = "SELECT t FROM Theme t join fetch t.staff s join fetch s.user join fetch s.position join fetch t.course c join fetch t.access", countQuery = "SELECT count(t) FROM Theme t")
    Page<Theme> findAll(Pageable pageable);

}
