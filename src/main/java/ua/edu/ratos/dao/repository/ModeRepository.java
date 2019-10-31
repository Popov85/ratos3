package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Mode;

import java.util.Optional;
import java.util.Set;

public interface ModeRepository extends JpaRepository<Mode, Long> {

    //------------------------------------------------------ONE for update----------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff st join fetch st.user where m.modeId =?1")
    Optional<Mode> findOneForEdit(Long modeId);

    //------------------------------------------------------DEFAULT dropdown--------------------------------------------
    @Query(value="select m from Mode m where m.defaultMode = true order by m.name asc")
    Set<Mode> findAllDefault();

    //-------------------------------------------------------INSTRUCTOR table-------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user where s.staffId = ?1",
            countQuery = "select count(m) from Mode m join m.staff s where s.staffId=?1")
    Page<Mode> findAllForTableByStaffId(Long staffId, Pageable pageable);

    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user join m.department d where d.depId = ?1",
            countQuery = "select count(m) from Mode m join m.staff s join m.department d where d.depId=?1")
    Page<Mode> findAllForTableByDepartmentId(Long depId, Pageable pageable);

    //-------------------------------------------------------Search in table--------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user where s.staffId = ?1 and m.name like %?2%",
            countQuery = "select count(m) from Mode m join m.staff s where s.staffId=?1 and m.name like %?2%")
    Page<Mode> findAllForTableByStaffIdAndModeNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user join m.department d where d.depId = ?1 and m.name like %?2%",
            countQuery = "select count(m) from Mode m join m.staff s join m.department d where d.depId=?1 and m.name like %?2%")
    Page<Mode> findAllForTableByDepartmentIdAndModeNameLettersContains(Long depId, String letters, Pageable pageable);

    //--------------------------------------------------------DropDown--------------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user where s.staffId = ?1")
    Slice<Mode> findAllForDropDownByStaffId(Long staffId, Pageable pageable);

    @Query(value="select m from Mode m join fetch m.staff s join m.department d where d.depId = ?1")
    Slice<Mode> findAllForDropDownByDepartmentId(Long depId, Pageable pageable);

    //------------------------------------------------------SEARCH dropdown---------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user where s.staffId = ?1 and m.name like %?2%")
    Slice<Mode> findAllForDropDownByStaffIdAndModeNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user join m.department d where d.depId = ?1 and m.name like %?2%")
    Slice<Mode> findAllForDropDownByDepartmentIdAndModeNameLettersContains(Long depId, String letters, Pageable pageable);

    //-----------------------------------------------------------ADMIN--------------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user", countQuery = "select count(m) from Mode m")
    Page<Mode> findAll(Pageable pageable);
}
