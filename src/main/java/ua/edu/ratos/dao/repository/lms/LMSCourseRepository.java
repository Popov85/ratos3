package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LMSCourse;

public interface LMSCourseRepository extends JpaRepository<LMSCourse, Long> {

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access join fetch c.lms where c.courseId =?1")
    LMSCourse findForEditById(Long courseId);

    // For security purposes to check "modify"-access to a LMSCourse
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.access a join fetch co.staff s join fetch s.department d where c.courseId =?1")
    LMSCourse findForSecurityById(Long courseId);


    // ----------------------------------------Instructors (for table)-------------------------------------------------

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms where s.staffId =?1 order by co.name asc",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s where s.staffId=?1")
    Page<LMSCourse> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms where s.staffId =?1 and co.name like %?2% order by co.name asc",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s where s.staffId=?1 and co.name like %?2%")
    Page<LMSCourse> findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch co.access a join fetch c.lms where d.depId =?1 order by co.name asc",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s join s.department d where d.depId=?1")
    Page<LMSCourse> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch co.access a join fetch c.lms where d.depId =?1 and co.name like %?2% order by co.name asc",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s join s.department d where d.depId=?1 and co.name like %?2%")
    Page<LMSCourse> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);


    // --------------------------------------------------ADMIN --------------------------------------------------------

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms order by co.name asc",
            countQuery = "SELECT count(c) FROM LMSCourse c")
    Page<LMSCourse> findAll(Pageable pageable);
}
