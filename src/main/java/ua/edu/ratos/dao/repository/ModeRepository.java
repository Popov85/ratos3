package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value="select m from Mode m where m.defaultMode = true")
    Set<Mode> findAllDefault();

    //-------------------------------------------------------INSTRUCTOR table-------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user join m.department d where m.defaultMode = false and d.depId = ?1")
    Set<Mode> findAllByDepartmentId(Long depId);

    //-----------------------------------------------------------ADMIN--------------------------------------------------
    @Query(value="select m from Mode m join fetch m.staff s join fetch s.user", countQuery = "select count(m) from Mode m")
    Page<Mode> findAll(Pageable pageable);
}
