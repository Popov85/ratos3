package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Mode;
import java.util.Set;

public interface ModeRepository extends JpaRepository<Mode, Long> {

    @Query(value="select m from Mode m where m.defaultMode = true order by m.name desc")
    Set<Mode> findAllDefault();

    @Query(value="select m from Mode m join m.staff s where s.staffId = ?1 order by m.name desc")
    Set<Mode> findAllByStaffId(Long staffId);

    @Query(value="select m from Mode m join m.staff s where s.staffId = ?1 and m.name like %?2% order by m.name desc")
    Set<Mode> findAllByStaffIdAndModeNameLettersContains(Long staffId, String contains);

    @Query(value="select m from Mode m join m.staff s join s.department d where d.depId = ?1 order by m.name desc")
    Page<Mode> findByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select m from Mode m join m.staff s join s.department d where d.depId = ?1 and m.name like %?2% order by m.name desc")
    Set<Mode> findAllByDepartmentIdAndModeNameLettersContains(Long depId, String contains);

    @Query(value="select m from Mode m order by m.name desc")
    Page<Mode> findAll(Pageable pageable);
}
