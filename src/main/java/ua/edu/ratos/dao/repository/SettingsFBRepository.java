package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SettingsFB;

public interface SettingsFBRepository extends JpaRepository<SettingsFB, Long> {

    //-------------------------------------ONE for update----------------------------------
    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff where s.settingsId =?1")
    SettingsFB findOneForEdit(Long settingsId);
    

    //-------------------------------------INSTRUCTOR dropdown------------------------------

    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff st where st.staffId = ?1 order by s.name asc")
    Slice<SettingsFB> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff st join st.department d where d.depId = ?1 order by s.name asc")
    Slice<SettingsFB> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff st where st.staffId = ?1 and s.name like %?2% order by s.name asc")
    Slice<SettingsFB>  findAllByStaffIdAndNameLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff st join st.department d where d.depId = ?1 and s.name like %?2% order by s.name asc")
    Slice<SettingsFB> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //-------------------------------------------ADMIN----------------------------------------

    @Query(value="select s from SettingsFB s join fetch s.lang join fetch s.staff order by s.name asc")
    Slice<SettingsFB> findAllAdmin(Pageable pageable);
}
