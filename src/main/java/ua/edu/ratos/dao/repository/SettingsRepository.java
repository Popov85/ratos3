package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Settings;
import java.util.Set;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    @Query(value="select s from Settings s where s.isDefault = true order by s.name desc")
    Set<Settings> findAllDefault();

    @Query(value="select s from Settings s join s.staff st where st.staffId = ?1 order by s.name desc")
    Set<Settings> findAllByStaffId(Long staffId);

    @Query(value="select s from Settings s join s.staff st where st.staffId = ?1 and s.name like %?2% order by s.name desc")
    Set<Settings> findAllByStaffIdAndSettingsNameLettersContains(Long staffId, String contains);

    @Query(value="select s from Settings s join s.staff st join st.department d where d.depId = ?1 order by s.name desc")
    Page<Settings> findByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select s from Settings s join s.staff st join st.department d where d.depId = ?1 and s.name like %?2% order by s.name desc")
    Set<Settings> findAllByDepartmentIdAndSettingsNameLettersContains(Long depId, String contains);

    @Query(value="select s from Settings s order by s.name desc")
    Page<Settings> findAll(Pageable pageable);
}
