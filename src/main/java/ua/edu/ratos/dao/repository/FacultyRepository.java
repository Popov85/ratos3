package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    //----------------------------------------------REGISTRATION dropdown-----------------------------------------------

    @Query(value="select f from Faculty f join f.organisation o where o.orgId = ?1 order by f.name asc")
    Slice<Faculty> findAllByOrgId(Long orgId, Pageable pageable);

}
