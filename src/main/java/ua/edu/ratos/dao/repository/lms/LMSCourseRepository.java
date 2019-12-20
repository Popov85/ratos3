package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LMSCourse;

import javax.persistence.Tuple;
import java.util.Optional;
import java.util.Set;

public interface LMSCourseRepository extends JpaRepository<LMSCourse, Long> {

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access join fetch c.lms where c.courseId =?1")
    Optional<LMSCourse> findForEditById(Long courseId);

    // For security purposes to check "modify"-access to a LMSCourse
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.access a join fetch co.staff s join fetch co.department d where c.courseId =?1")
    Optional<LMSCourse> findForSecurityById(Long courseId);

    // ----------------------------------------Instructors (for table)--------------------------------------------------
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms where s.staffId =?1",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s where s.staffId=?1")
    Page<LMSCourse> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.department d join fetch co.access a join fetch c.lms where d.depId =?1",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.department d where d.depId=?1")
    Page<LMSCourse> findAllByDepartmentId(Long depId, Pageable pageable);

    //---------------------------------------------Search in table------------------------------------------------------
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms where s.staffId =?1 and co.name like %?2%",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s where s.staffId=?1 and co.name like %?2%")
    Page<LMSCourse> findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);


    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch s.department d join fetch co.access a join fetch c.lms where d.depId =?1 and co.name like %?2%",
            countQuery = "SELECT count(c) FROM LMSCourse c join c.course co join co.staff s join co.department d where d.depId=?1 and co.name like %?2%")
    Page<LMSCourse> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //--------------------------------------------INSTRUCTOR DROPDOWN search--------------------------------------------
    // Quickest and simplest (recommended: 100 per slice to fit all courses in the first slice)
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.staff s where s.staffId =?1")
    Slice<LMSCourse> findAllForDropDownByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.department d where d.depId =?1")
    Slice<LMSCourse> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    //-------------------------------------------------Dropdown search--------------------------------------------------
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.staff s where s.staffId =?1 and co.name like ?2%")
    Slice<LMSCourse> findAllForDropDownByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.staff s where s.staffId =?1 and co.name like %?2%")
    Slice<LMSCourse> findAllForDropDownByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.department d where d.depId =?1 and co.name like ?2%")
    Slice<LMSCourse> findAllForDropDownByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join co.department d where d.depId =?1 and co.name like %?2%")
    Slice<LMSCourse> findAllForDropDownByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);

    // --------------------------------------------------ADMIN ---------------------------------------------------------
    @Query(value = "SELECT c FROM LMSCourse c join fetch c.course co join fetch co.staff s join fetch s.user join fetch s.position join fetch co.access a join fetch c.lms", countQuery = "SELECT count(c) FROM LMSCourse c")
    Page<LMSCourse> findAll(Pageable pageable);


    //-------------------------------------------------REPORT on content------------------------------------------------
    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(lmsc) as count FROM LMSCourse lmsc left join lmsc.course c join c.department d join d.faculty f join f.organisation o where d.depId=?1 group by d.depId")
    Tuple countLMSCoursesByDepOfDepId(Long depId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(lmsc) as count FROM LMSCourse lmsc left join lmsc.course c join c.department d join d.faculty f join f.organisation o where f.facId=?1 group by d.depId")
    Set<Tuple> countLMSCoursesByDepOfFacId(Long facId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(lmsc) as count FROM LMSCourse lmsc left join lmsc.course c join c.department d join d.faculty f join f.organisation o where o.orgId=?1 group by d.depId")
    Set<Tuple> countLMSCoursesByDepOfOrgId(Long orgId);

    @Query(value = "SELECT o.name as org, f.name as fac, d.name as dep, count(lmsc) as count FROM LMSCourse lmsc left join lmsc.course c join c.department d join d.faculty f join f.organisation o group by d.depId")
    Set<Tuple> countLMSCoursesByDepOfRatos();
}
