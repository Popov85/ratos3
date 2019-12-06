package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.Scheme;
import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.Set;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.strategy join fetch s.grading join fetch s.options join fetch s.access join fetch s.themes st join fetch st.theme join fetch st.settings sts join fetch sts.type left join fetch s.groups where s.schemeId = ?1")
    Optional<Scheme> findForEditById(Long schemeId);

    // For security purposes to check "modify"-access
    @Query(value = "SELECT s FROM Scheme s join fetch s.access a join fetch s.staff st join fetch st.user join fetch st.department d where s.schemeId =?1")
    Optional<Scheme> findForSecurityById(Long schemeId);

    // -----------------------------------------One for different purposes----------------------------------------------
    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students where s.schemeId = ?1")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="USE"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE")})
    Optional<Scheme> findForSessionById(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings sts join fetch sts.type where s.schemeId = ?1")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="USE"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE")})
    Optional<Scheme> findForInfoById(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s join fetch s.themes t join fetch t.settings where s.schemeId = ?1")
    Optional<Scheme> findForThemesManipulationById(Long schemeId);

    @Query(value = "SELECT s FROM Scheme s left join fetch s.grading t where s.schemeId = ?1")
    Optional<Scheme> findForGradingById(Long schemeId);

    //-------------------------------------------Populate cache on start-up---------------------------------------------
    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="REFRESH"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="BYPASS")})
    Slice<Scheme> findAllForCachedSession(Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students where size(s.themes)>1")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="REFRESH"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="BYPASS")})
    Slice<Scheme> findLargeForCachedSession(Pageable pageable);

    //--------------------------------------------Populate cache at run-time--------------------------------------------
    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students join s.course c where c.courseId=?1")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="REFRESH"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="BYPASS")})
    Slice<Scheme> findCoursesSchemesForCachedSession(Long courseId, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.mode join fetch s.settings join fetch s.options join fetch s.strategy join fetch s.grading join fetch s.themes st join fetch st.settings left join fetch s.groups g left join fetch g.students join s.department d where d.depId=?1")
    @QueryHints({@QueryHint(name="javax.persistence.cache.storeMode", value="REFRESH"), @QueryHint(name="javax.persistence.cache.retrieveMode", value="BYPASS")})
    Slice<Scheme> findDepartmentSchemesForCachedSession(Long depId, Pageable pageable);

    //--------------------------------------------Instructors (for table)-----------------------------------------------
    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1")
    Page<Scheme> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1")
    Page<Scheme> findAllByDepartmentId(Long depId, Pageable pageable);

    //------------------------------------------------Search (in table)-------------------------------------------------
    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1 and s.name like ?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1 and s.name like ?2%")
    Page<Scheme> findAllByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups where st.staffId =?1 and s.name like %?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.staff st where st.staffId=?1 and s.name like %?2%")
    Page<Scheme> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1 and s.name like ?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1 and s.name like ?2%")
    Page<Scheme> findAllByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join s.department d join fetch s.access left join fetch s.themes left join fetch s.groups where d.depId =?1 and s.name like %?2%",
            countQuery = "SELECT count(s) FROM Scheme s join s.department d where d.depId=?1 and s.name like %?2%")
    Page<Scheme> findAllByDepartmentIdAndNameContains(Long depId, String letters, Pageable pageable);


    //----------------------------------------------Set (min for drop-down)---------------------------------------------
    // TODO: select only id and name!!!

    @Query(value = "SELECT new Scheme(s.schemeId, s.name) FROM Scheme s join s.staff st where st.staffId =?1")
    Set<Scheme> findAllForDropDownByStaffId(Long staffId);

    @Query(value = "SELECT new Scheme(s.schemeId, s.name) FROM Scheme s join s.department d where d.depId =?1")
    Set<Scheme> findAllForDropDownByDepartmentId(Long depId);

    @Query(value = "SELECT new Scheme(s.schemeId, s.name) FROM Scheme s join s.course c where c.courseId =?1")
    Set<Scheme> findAllForDropDownByCourseId(Long courseId);

    @Query(value = "SELECT new Scheme(s.schemeId, s.name) FROM Scheme s join s.department d join d.faculty f where f.facId =?1")
    Set<Scheme> findAllForDropDownByFacultyId(Long facId);

    @Query(value = "SELECT new Scheme(s.schemeId, s.name) FROM Scheme s join s.department d join d.faculty f join f.organisation o where o.orgId =?1")
    Set<Scheme> findAllForDropDownByOrganisationId(Long orgId);

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
    @Query(value = "SELECT s FROM Scheme s join fetch s.strategy join fetch s.settings join fetch s.options join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff st join fetch st.user join fetch st.position join fetch s.access left join fetch s.themes left join fetch s.groups", countQuery = "SELECT count(s) FROM Scheme s")
    Page<Scheme> findAll(Pageable pageable);

}
