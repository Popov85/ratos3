package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Course;

import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where c.courseId =?1")
    Course findForEditById(Long courseId);

    // For security purposes to check "modify"-access to a course
    @Query(value = "SELECT c FROM Course c join fetch c.access a join fetch c.staff s join fetch s.department d where c.courseId =?1")
    Course findForSecurityById(Long courseId);


    // ----------------------------------------Instructors (for table)--------------------------------------

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where s.staffId =?1 order by c.name asc",
            countQuery = "SELECT count(c) FROM Course c join c.staff s where s.staffId=?1")
    Page<Course> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a where s.staffId =?1 and c.name like %?2% order by c.name asc",
            countQuery = "SELECT count(c) FROM Course c join c.staff s where s.staffId=?1 and c.name like %?2%")
    Page<Course> findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch c.access a where d.depId =?1 order by c.name asc",
            countQuery = "SELECT count(c) FROM Course c join c.staff s join s.department d where d.depId=?1")
    Page<Course> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch c.access a where d.depId =?1 and c.name like %?2% order by c.name asc",
            countQuery = "SELECT count(c) FROM Course c join c.staff s join s.department d where d.depId=?1 and c.name like %?2%")
    Page<Course> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //--------------------------------------------INSTRUCTOR DROPDOWN search-------------------------------------------
    // Quickest and simplest (recommended: 100 per slice)

    @Query(value = "SELECT c FROM Course c join c.staff s join s.department d where d.depId =?1 order by c.name asc")
    Slice<Course> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);


    // ----------------------------------------------ADMIN-TABLE ------------------------------------------------------
    // (for simple table: no organisation, no faculty, no department data)
    @Query(value = "SELECT c FROM Course c join fetch c.staff s join fetch s.user join fetch s.position join fetch c.access a order by c.name asc",
            countQuery = "SELECT count(c) FROM Course c")
    Page<Course> findAll(Pageable pageable);


    // -------------------------------------------TEST (just in case)--------------------------------------------------
    @Query(value = "SELECT c FROM Course c left join c.lmsCourse l where l is not null order by c.name asc")
    Set<Course> findAllNonLMS();
}
