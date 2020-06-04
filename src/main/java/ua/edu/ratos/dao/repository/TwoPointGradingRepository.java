package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;

import java.util.Optional;
import java.util.Set;

public interface TwoPointGradingRepository extends JpaRepository<TwoPointGrading, Long> {

    //----------------------------------------------------ONE for update------------------------------------------------
    @Query(value="select g from TwoPointGrading g join fetch g.staff where g.twoId =?1")
    Optional<TwoPointGrading> findOneForEdit(Long twoId);

    //---------------------------------------------------DEFAULT dropdown-----------------------------------------------
    @Query(value="select g from TwoPointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<TwoPointGrading> findAllDefault();

    //--------------------------------------------------INSTRUCTOR table/drop-down--------------------------------------
    @Query(value="select g from TwoPointGrading g join fetch g.staff join g.department d where g.isDefault = false and d.depId = ?1")
    Set<TwoPointGrading> findAllByDepartmentId(Long depId);

    //----------------------------------------------------------ADMIN---------------------------------------------------
    @Query(value="select g from TwoPointGrading g join fetch g.staff")
    Slice<TwoPointGrading> findAllAdmin(Pageable pageable);
}
