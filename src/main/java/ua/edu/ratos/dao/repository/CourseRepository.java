package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Course;

import javax.persistence.Tuple;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where c.courseId =?1")
    Course findForEditById(Long courseId);

    // For security purposes to check "modify"-access to a course
    @Query(value = "SELECT c FROM Course c join fetch c.access a join fetch c.staff s join fetch s.user join fetch s.department d where c.courseId =?1")
    Course findForSecurityById(Long courseId);

    // -----------------------------------------------------Staff (drop down)-------------------------------------------
    @Query(value = "SELECT new Course(c.courseId, c.name) FROM Course c join c.staff s where s.staffId =?1")
    Set<Course> findAllForDropDownByStaffId(Long staffId);

    @Query(value = "SELECT new Course(c.courseId, c.name) FROM Course c join c.department d where d.depId =?1")
    Set<Course> findAllForDropDownByDepartmentId(Long depId);

    // -----------------------------------------------------Staff (table)-----------------------------------------------
    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where s.staffId =?1",
            countQuery = "SELECT count(c) FROM Course c join c.staff s where s.staffId=?1")
    Page<Course> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.department d join fetch c.access a where d.depId =?1",
            countQuery = "SELECT count(c) FROM Course c join c.department d where d.depId=?1")
    Page<Course> findAllByDepartmentId(Long depId, Pageable pageable);

    //-------------------------------------------------Search (in table)------------------------------------------------
    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where s.staffId =?1 and c.name like ?2%",
            countQuery = "SELECT count(c) FROM Course c join c.staff s where s.staffId=?1 and c.name like ?2%")
    Page<Course> findAllByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where s.staffId =?1 and c.name like %?2%",
            countQuery = "SELECT count(c) FROM Course c join c.staff s where s.staffId=?1 and c.name like %?2%")
    Page<Course> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.department d join fetch c.access a where d.depId =?1 and c.name like ?2%",
            countQuery = "SELECT count(c) FROM Course c join c.department d where d.depId=?1 and c.name like ?2%")
    Page<Course> findAllByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.department d join fetch c.access a where d.depId =?1 and c.name like %?2%",
            countQuery = "SELECT count(c) FROM Course c join c.department d where d.depId=?1 and c.name like %?2%")
    Page<Course> findAllByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);


    //-----------------------------------------------------Dropdown-----------------------------------------------------
    // Quickest and simplest (recommended: 30 per slice to fit all th department courses in the first slice)

    @Query(value = "SELECT c FROM Course c join c.staff s where s.staffId =?1")
    Slice<Course> findAllForDropDownByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join c.department d where d.depId =?1")
    Slice<Course> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    //-----------------------------------------------Search (in drop-down)----------------------------------------------

    @Query(value = "SELECT c FROM Course c join c.staff s where s.staffId =?1 and c.name like ?2%")
    Slice<Course> findAllForDropDownByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join c.staff s where s.staffId =?1 and c.name like %?2%")
    Slice<Course> findAllForDropDownByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);


    @Query(value = "SELECT c FROM Course c join c.department d where d.depId =?1 and c.name like ?2%")
    Slice<Course> findAllForDropDownByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join c.department d where d.depId =?1 and c.name like %?2%")
    Slice<Course> findAllForDropDownByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);


    // -----------------------------------------------ADMIN-TABLE ------------------------------------------------------
    // (for simple table: no organisation, no faculty, no department data)

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access", countQuery = "SELECT count(c) FROM Course c")
    Page<Course> findAll(Pageable pageable);



    //-------------------------------------------------REPORT on content------------------------------------------------
    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(c) as count FROM Course c left join c.department d join d.faculty f join f.organisation o where d.depId=?1 group by d.depId")
    Tuple countCoursesByDepOfDepId(Long depId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(c) as count FROM Course c left join c.department d join d.faculty f join f.organisation o where f.facId=?1 group by d.depId")
    Set<Tuple> countCoursesByDepOfFacId(Long facId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(c) as count FROM Course c left join c.department d join d.faculty f join f.organisation o where o.orgId=?1 group by d.depId")
    Set<Tuple> countCoursesByDepOfOrgId(Long orgId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(c) as count FROM Course c left join c.department d join d.faculty f join f.organisation o group by d.depId")
    Set<Tuple> countCoursesByDepOfRatos();

}
