package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Faculty;

import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    //----------------------------------------------REGISTRATION dropdown-----------------------------------------------

    @Query(value="select f from Faculty f join f.organisation o where o.orgId = ?1")
    Set<Faculty> findAllByOrgId(Long orgId);

    @Query(value="select f from Faculty f join f.organisation o where o.orgId = ?1")
    Slice<Faculty> findAllByOrgId(Long orgId, Pageable pageable);

}
