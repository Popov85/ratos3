package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(value = "SELECT r FROM Resource r join r.staff s where s.staffId=?1 order by r.lastUsed desc")
    Page<Resource> findByStaffId(Long staId, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join r.staff s where s.staffId=?1 and r.description like ?2% order by r.lastUsed desc")
    Page<Resource> findByStaffIdAndFirstLetters(Long staId, String starts, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join r.staff s join s.department d where d.depId=?1 order by r.lastUsed desc")
    Page<Resource> findByDepartmentId(Long depId, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join r.staff s join s.department d where d.depId=?1 and r.description like ?2% order by r.lastUsed desc")
    Page<Resource> findByDepartmentIdAndFirstLetters(Long depId, String starts, Pageable pageable);

}
