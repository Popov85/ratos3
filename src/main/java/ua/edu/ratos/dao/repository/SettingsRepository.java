package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Settings;

import java.util.Optional;
import java.util.Set;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    //------------------------------------------------------ONE for update----------------------------------------------
    @Query(value="select s from Settings s join fetch s.staff st join fetch st.user where s.setId = ?1")
    Optional<Settings> findOneForEdit(Long setId);

    //---------------------------------------------------------DEFAULT--------------------------------------------------
    @Query(value="select s from Settings s join fetch s.staff st join fetch st.user where s.isDefault = true")
    Set<Settings> findAllDefault();

    //---------------------------------------------------INSTRUCTOR table/drop-down-------------------------------------

    @Query(value="select s from Settings s join fetch s.staff st join fetch st.user join s.department d where s.isDefault = false and d.depId = ?1")
    Set<Settings> findAllByDepartmentId(Long depId);

    //------------------------------------------------------------ADMIN-------------------------------------------------
    @Query(value="select s from Settings s join fetch s.staff st join fetch st.user join fetch s.department", countQuery = "select count(s) from Settings s")
    Page<Settings> findAllAdmin(Pageable pageable);
}
