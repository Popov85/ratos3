package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;

import java.util.Optional;
import java.util.Set;

public interface FreePointGradingRepository extends JpaRepository<FreePointGrading, Long> {

    @Query(value="select g from FreePointGrading g join fetch g.staff where g.freeId =?1")
    Optional<FreePointGrading> findOneForEdit(Long FreeId);

    //----------------------------------------------------DEFAULT dropdown----------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<FreePointGrading> findAllDefault();

    //--------------------------------------------------INSTRUCTOR table/drop-down--------------------------------------
    @Query(value="select g from FreePointGrading g join fetch g.staff join g.department d where g.isDefault = false and d.depId = ?1")
    Set<FreePointGrading> findAllByDepartmentId(Long depId);

    //----------------------------------------------------------ADMIN---------------------------------------------------

    @Query(value="select g from FreePointGrading g join fetch g.staff")
    Slice<FreePointGrading> findAllAdmin(Pageable pageable);
}
