package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Department;

import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // -----------------------------------------------------For DropDown------------------------------------------------

    @Query(value = "SELECT new Department(d.depId, d.name) FROM Department d join d.faculty f where f.facId =?1")
    Set<Department> findAllByFacIdForDropDown(Long facId);
}
