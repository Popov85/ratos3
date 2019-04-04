package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;

import java.util.Set;

public interface TwoPointGradingRepository extends JpaRepository<TwoPointGrading, Long> {

    //----------------------------------------------------ONE for update------------------------------------------------

    @Query(value="select g from TwoPointGrading g join fetch g.staff where g.twoId =?1")
    TwoPointGrading findOneForEdit(Long twoId);

    //---------------------------------------------------DEFAULT dropdown-----------------------------------------------

    @Query(value="select g from TwoPointGrading g join fetch g.staff where g.isDefault = true order by g.name asc")
    Set<TwoPointGrading> findAllDefault();

    //----------------------------------------------INSTRUCTOR table & dropdown-----------------------------------------

    @Query(value="select g from TwoPointGrading g join fetch g.staff s where s.staffId = ?1")
    Slice<TwoPointGrading> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select g from TwoPointGrading g join fetch g.staff s join g.department d where d.depId = ?1")
    Slice<TwoPointGrading> findAllByDepartmentId(Long depId, Pageable pageable);

    //--------------------------------------------------Search in table-------------------------------------------------

    @Query(value="select g from TwoPointGrading g join fetch g.staff s where s.staffId = ?1 and g.name like %?2%")
    Slice<TwoPointGrading> findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select g from TwoPointGrading g join fetch g.staff s join g.department d where d.depId = ?1 and g.name like %?2%")
    Slice<TwoPointGrading> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //----------------------------------------------------------ADMIN---------------------------------------------------

    @Query(value="select g from TwoPointGrading g join fetch g.staff")
    Slice<TwoPointGrading> findAllAdmin(Pageable pageable);
}
