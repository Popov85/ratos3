package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    //----------------------------------------AUTHENTICATION (security)-------------------------------------
    @Query(value = "SELECT s FROM Staff s join fetch s.user u left join fetch u.roles join fetch s.department d join fetch d.faculty f join fetch f.organisation o where u.email = ?1")
    Staff findByIdForAuthentication(String email);


    //---------------------------------------------ONE for edit----------------------------------------------
    @Query(value="select s from Staff s join fetch s.user join fetch s.position where s.staffId = ?1")
    Staff findOneForEdit(Long staffId);


    //----------------------------------------DEPARTMENT ADMIN table-----------------------------------------

    @Query(value="select s from Staff s join fetch s.user u join s.department d where d.depId = ?1 order by u.surname asc",
            countQuery = "select count(s) from Staff s join s.user u join s.department d where d.depId=?1")
    Page<Staff> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select s from Staff s join fetch s.user u join s.department d where d.depId = ?1 and (u.name like %?2% or u.surname like %?2%) order by u.surname asc",
            countQuery = "select count(s) from Staff s join s.user u join s.department d where d.depId=?1 and (u.name like %?2% or u.surname like %?2%)")
    Page<Staff> findAllByDepartmentIdAndNameLettersContains(Long depId, String letters, Pageable pageable);

    //------------------------------------------------ADMIN---------------------------------------------------

    @Query(value="select s from Staff s join fetch s.user u join s.department order by u.surname asc", countQuery = "select count(s) from Staff s")
    Page<Staff> findAllAdmin(Pageable pageable);

}
