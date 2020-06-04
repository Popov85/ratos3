package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grading.FourPointGrading;

import java.util.Optional;
import java.util.Set;

public interface FourPointGradingRepository extends JpaRepository<FourPointGrading, Long> {

    //-----------------------------------------------ONE for update-----------------------------------------------------
    @Query(value = "select g from FourPointGrading g join fetch g.staff where g.fourId =?1")
    Optional<FourPointGrading> findOneForEdit(Long fourId);

    //----------------------------------------------DEFAULT dropdown----------------------------------------------------
    @Query(value = "select g from FourPointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<FourPointGrading> findAllDefault();

    //--------------------------------------------------INSTRUCTOR table/drop-down--------------------------------------
    @Query(value="select g from FourPointGrading g join fetch g.staff join g.department d where g.isDefault = false and d.depId = ?1")
    Set<FourPointGrading> findAllByDepartmentId(Long depId);

    //-----------------------------------------------------ADMIN--------------------------------------------------------
    @Query(value = "select g from FourPointGrading g join fetch g.staff")
    Slice<FourPointGrading> findAllAdmin(Pageable pageable);
}
