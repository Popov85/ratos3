package ua.edu.ratos.dao.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.Scheme;
import javax.persistence.QueryHint;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.strategy join fetch s.grading join fetch s.access join fetch s.themes st join fetch st.theme join fetch st.settings sts join fetch sts.type left join fetch s.groups where s.schemeId = ?1")
    Scheme findForEditById(Long schemeId);

    // For security purposes to check "modify"-access
    @Query(value = "SELECT s FROM Scheme s join fetch s.access a join fetch s.staff st join fetch st.department d where s.schemeId =?1")
    Scheme findForSecurityById(Long schemeId);

    // -----------------------------------------One for different purposes----------------------------------------------

    @Cacheable("scheme")
    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students where s.schemeId = ?1")
    //@QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Scheme findForSessionById(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s join fetch s.themes t join fetch t.settings where s.schemeId = ?1")
    Scheme findForThemesManipulationById(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s left join fetch s.grading t where s.schemeId = ?1")
    Scheme findForGradingById(Long schemeId);

    //--------------------------------------------Instructors (for table)-----------------------------------------------

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1")
    Page<Scheme> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1")
    Page<Scheme> findAllByDepartmentId(Long depId, Pageable pageable);

    //------------------------------------------------Search (in table)-------------------------------------------------

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1 and s.name like ?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1 and s.name like ?2%")
    Page<Scheme> findAllByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1 and s.name like %?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1 and s.name like %?2%")
    Page<Scheme> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1 and s.name like ?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1 and s.name like ?2%")
    Page<Scheme> findAllByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1 and s.name like %?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1 and s.name like %?2%")
    Page<Scheme> findAllByDepartmentIdAndNameContains(Long depId, String letters, Pageable pageable);

    //----------------------------------------------Slice (for drop-down)-----------------------------------------------
    // Quickest and simplest (recommended: 50 per slice)

    @Query(value = "SELECT s FROM Scheme s join s.department d where d.depId =?1")
    Slice<Scheme> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join s.course c where c.courseId =?1")
    Slice<Scheme> findAllForDropDownByCourseId(Long courseId, Pageable pageable);

    //----------------------------------------------Search (in drop-down)-----------------------------------------------

    @Query(value = "SELECT s FROM Scheme s join s.department d where d.depId =?1 and s.name like ?2%")
    Slice<Scheme> findAllForDropDownByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join s.department d where d.depId =?1 and s.name like %?2%")
    Slice<Scheme> findAllForDropDownByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join s.course c where c.courseId =?1 and s.name like ?2%")
    Slice<Scheme> findAllForDropDownByCourseIdAndNameStarts(Long courseId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join s.course c where c.courseId =?1 and s.name like %?2%")
    Slice<Scheme> findAllForDropDownByCourseIdAndNameLettersContains(Long courseId, String contains, Pageable pageable);


    //------------------------------------------ADMIN-TABLE to replace by Specs-----------------------------------------
    // (for simple table: no organisation, no faculty, no department data)

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups", countQuery = "SELECT count(s) FROM Scheme s")
    Page<Scheme> findAll(Pageable pageable);

}
