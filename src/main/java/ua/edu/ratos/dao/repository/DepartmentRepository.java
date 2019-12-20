package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.service.dto.out.report.OrgFacDep;

import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // -----------------------------------------------------For DropDown------------------------------------------------

    @Query(value = "SELECT new Department(d.depId, d.name) FROM Department d join d.faculty f where f.facId =?1")
    Set<Department> findAllByFacIdForDropDown(Long facId);


    //-----------------------------------------------------Reports------------------------------------------------------

    @Query(value = "SELECT new ua.edu.ratos.service.dto.out.report.OrgFacDep(o.name, f.name, d.name) FROM Department d join d.faculty f join f.organisation o where f.facId =?1")
    Set<OrgFacDep> findAllByFacIdForReport(Long facId);

    @Query(value = "SELECT new ua.edu.ratos.service.dto.out.report.OrgFacDep(o.name, f.name, d.name) FROM Department d join d.faculty f join f.organisation o where o.orgId =?1")
    Set<OrgFacDep> findAllByOrgIdForReport(Long orgId);

    @Query(value = "SELECT new ua.edu.ratos.service.dto.out.report.OrgFacDep(o.name, f.name, d.name) FROM Department d join d.faculty f join f.organisation o")
    Set<OrgFacDep> findAllByRatosForReport();
}
