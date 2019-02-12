package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    //--------------------------------------------One for update-------------------------------------
    @Query(value = "SELECT g FROM Group g join fetch g.staff s join fetch g.students st join fetch st.studentClass where g.groupId =?1")
    Group findOneForEdit(Long groupId);

    //-----------------------------------------Instructor for table-----------------------------------

    @Query(value = "SELECT g FROM Group g join fetch g.staff s where s.staffId =?1 order by g.name asc",
            countQuery = "SELECT count(g) FROM Group g join g.staff s where s.staffId =?1")
    Page<Group> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT g FROM Group g join fetch g.staff s join s.department d where d.depId =?1 order by g.name asc",
            countQuery = "SELECT count(g) FROM Group g join g.staff s join s.department d where d.depId =?1")
    Page<Group> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT g FROM Group g join g.staff s where s.staffId =?1 and g.name like %?2% order by g.name asc",
            countQuery = "SELECT count(g) FROM Group g join g.staff s where s.staffId =?1 and g.name like %?2%")
    Page<Group> findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT g FROM Group g join fetch g.staff s join s.department d where d.depId =?1 and g.name like %?2% order by g.name asc",
            countQuery = "SELECT count(g) FROM Group g join g.staff s join s.department d where d.depId =?1 and g.name like %?2%")
    Page<Group> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //----------------------------------------INSTRUCTOR DROPDOWN search---------------------------------
    // Quickest and simplest (recommended: 100 per slice)

    @Query(value = "SELECT g FROM Group g join g.staff s join s.department d where d.depId =?1 and g.enabled = true order by g.name asc")
    Slice<Group> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    // -------------------------------------------------ADMIN---------------------------------------------
    @Query(value="SELECT g FROM Group g join fetch g.staff order by g.name asc", countQuery = "SELECT count(g) FROM Group g")
    Page<Group> findAllAdmin(Pageable pageable);
}
