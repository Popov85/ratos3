package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;

import java.util.Set;

public interface FreePointGradingRepository extends JpaRepository<FreePointGrading, Long> {

    @Query(value="select g from FreePointGrading g join fetch g.staff where g.freeId =?1")
    FreePointGrading findOneForEdit(Long FreeId);

    //----------------------------------------------------DEFAULT dropdown----------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<FreePointGrading> findAllDefault();

    //-----------------------------------------------------INSTRUCTOR table---------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff s where s.staffId = ?1")
    Slice<FreePointGrading> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s join s.department d where d.depId = ?1")
    Slice<FreePointGrading> findAllByDepartmentId(Long depId, Pageable pageable);

    //---------------------------------------------------------Search---------------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff s where s.staffId = ?1 and g.name like ?2%")
    Slice<FreePointGrading> findAllByStaffIdAndNameStarts(Long staffId, String starts, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s where s.staffId = ?1 and g.name like %?2%")
    Slice<FreePointGrading> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s join s.department d where d.depId = ?1 and g.name like ?2%")
    Slice<FreePointGrading> findAllByDepartmentIdAndNameStarts(Long depId, String starts, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s join s.department d where d.depId = ?1 and g.name like %?2%")
    Slice<FreePointGrading> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //----------------------------------------------------------ADMIN---------------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff")
    Slice<FreePointGrading> findAllAdmin(Pageable pageable);
}
