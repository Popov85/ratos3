package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Faculty;

import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    //--------------------------------------------------For DropDown----------------------------------------------------

    @Query(value="select new Faculty(f.facId, f.name) from Faculty f join f.organisation o where o.orgId = ?1")
    Set<Faculty> findAllByOrgIdForDropDown(Long orgId);
}
