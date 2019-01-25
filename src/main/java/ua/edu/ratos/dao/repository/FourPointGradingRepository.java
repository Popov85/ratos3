package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grade.FourPointGrading;

import java.util.Set;

public interface FourPointGradingRepository extends JpaRepository<FourPointGrading, Long> {

    //-------------------------------------ONE for update----------------------------------
    @Query(value="select g from FourPointGrading g join fetch g.staff where g.fourId =?1")
    FourPointGrading findOneForEdit(Long fourId);

    //-------------------------------------DEFAULT dropdown---------------------------------
    @Query(value="select g from FourPointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<FourPointGrading> findAllDefault();

    //-------------------------------------INSTRUCTOR table--------------------------------

    @Query(value="select g from FourPointGrading g join fetch g.staff s where s.staffId = ?1 order by g.name asc")
    Slice<FourPointGrading> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select g from FourPointGrading g join fetch g.staff s join s.department d where d.depId = ?1 order by g.name asc")
    Slice<FourPointGrading> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select g from FourPointGrading g join fetch g.staff s where s.staffId = ?1 and g.name like %?2% order by g.name asc")
    Slice<FourPointGrading>  findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select g from FourPointGrading g join fetch g.staff s join s.department d where d.depId = ?1 and g.name like %?2% order by g.name asc")
    Slice<FourPointGrading>  findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //-------------------------------------------ADMIN----------------------------------------

    @Query(value="select g from FourPointGrading g join fetch g.staff s order by g.name asc")
    Slice<FourPointGrading> findAllAdmin(Pageable pageable);
}
