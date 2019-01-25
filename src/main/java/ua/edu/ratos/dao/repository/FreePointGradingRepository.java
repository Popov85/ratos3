package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grade.FreePointGrading;

import java.util.Set;

public interface FreePointGradingRepository extends JpaRepository<FreePointGrading, Long> {

    @Query(value="select g from FreePointGrading g join fetch g.staff where g.freeId =?1")
    FreePointGrading findOneForEdit(Long FreeId);

    //-------------------------------------DEFAULT dropdown---------------------------------
    @Query(value="select g from FreePointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<FreePointGrading> findAllDefault();

    //-------------------------------------INSTRUCTOR table--------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff s where s.staffId = ?1 order by g.name asc")
    Slice<FreePointGrading> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s join s.department d where d.depId = ?1 order by g.name asc")
    Slice<FreePointGrading> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s where s.staffId = ?1 and g.name like %?2% order by g.name asc")
    Slice<FreePointGrading>  findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select g from FreePointGrading g join fetch g.staff s join s.department d where d.depId = ?1 and g.name like %?2% order by g.name asc")
    Slice<FreePointGrading>  findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //-------------------------------------------ADMIN----------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff s order by g.name asc")
    Slice<FreePointGrading> findAllAdmin(Pageable pageable);
}
