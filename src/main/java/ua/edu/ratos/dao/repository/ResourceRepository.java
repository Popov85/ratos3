package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Resource;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    //----------------------------------------------SELECT one for update-----------------------------------------------
    @Query(value = "SELECT r FROM Resource r join fetch r.staff s where r.resourceId=?1")
    Optional<Resource> findOneForEdit(Long resId);

    //------------------------------------------------Instructor table--------------------------------------------------
    @Query(value = "SELECT r FROM Resource r join fetch r.staff s where s.staffId=?1",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s where s.staffId=?1")
    Page<Resource> findByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join fetch r.staff s join s.department d where d.depId=?1",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s join s.department d where d.depId=?1")
    Page<Resource> findByDepartmentId(Long depId, Pageable pageable);

    //----------------------------------------------------Table search--------------------------------------------------
    @Query(value = "SELECT r FROM Resource r join fetch r.staff s where s.staffId=?1 and r.description like ?2%",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s where s.staffId=?1 and r.description like ?2%")
    Page<Resource> findByStaffIdAndDescriptionStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join fetch r.staff s where s.staffId=?1 and r.description like %?2%",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s where s.staffId=?1 and r.description like %?2%")
    Page<Resource> findByStaffIdAndDescriptionLettersContains(Long staffId, String contains, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join fetch r.staff s join s.department d where d.depId=?1 and r.description like ?2%",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s join s.department d where d.depId=?1 and r.description like ?2%")
    Page<Resource> findByDepartmentIdAndDescriptionStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT r FROM Resource r join fetch r.staff s join s.department d where d.depId=?1 and r.description like %?2%",
            countQuery = "SELECT count(r) FROM Resource r join r.staff s join s.department d where d.depId=?1 and r.description like %?2%")
    Page<Resource> findByDepartmentIdAndDescriptionLettersContains(Long depId, String contains, Pageable pageable);

    //--------------------------------------------------------ADMIN-----------------------------------------------------
    @Query(value="SELECT r FROM Resource r join fetch r.staff", countQuery = "SELECT count(r) FROM Resource r")
    Page<Resource> findAll(Pageable pageable);
}
