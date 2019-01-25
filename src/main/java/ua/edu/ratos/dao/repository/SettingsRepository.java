package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Settings;
import java.util.Set;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    //------------------------------------ONE for update-----------------------------------

    @Query(value="select s from Settings s join fetch s.staff where s.setId = ?1")
    Settings findOneForEdit(Long setId);

    //----------------------------------------DEFAULT---------------------------------------

    @Query(value="select s from Settings s join fetch s.staff where s.isDefault = true order by s.name asc")
    Set<Settings> findAllDefault();

    //-------------------------------------INSTRUCTOR table---------------------------------

    @Query(value="select s from Settings s join fetch s.staff st where st.staffId = ?1 order by s.name asc",
            countQuery = "select count(s) from Settings s join s.staff st where st.staffId =?1")
    Page<Settings> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select s from Settings s join fetch s.staff st where st.staffId = ?1 and s.name like %?2% order by s.name asc",
            countQuery = "select count(s) from Settings s join s.staff st where st.staffId =?1 and s.name like %?2%")
    Page<Settings> findAllByStaffIdAndNameLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value="select s from Settings s join fetch s.staff st join st.department d where d.depId = ?1 order by s.name asc",
            countQuery = "select count(s) from Settings s join s.staff st join st.department d where d.depId =?1")
    Page<Settings> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select s from Settings s join fetch s.staff st join st.department d where d.depId = ?1 and s.name like %?2% order by s.name asc",
            countQuery = "select count(s) from Settings s join s.staff st join st.department d where d.depId =?1 and s.name like %?2%")
    Page<Settings> findAllByDepartmentIdAndNameLettersContains(Long depId, String contains, Pageable pageable);

    //------------------------------------------ADMIN---------------------------------------

    @Query(value="select s from Settings s join fetch s.staff order by s.name asc",
            countQuery = "select count(s) from Settings s")
    Page<Settings> findAll(Pageable pageable);

}
