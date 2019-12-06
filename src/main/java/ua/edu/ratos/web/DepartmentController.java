package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.DepartmentService;
import ua.edu.ratos.service.dto.out.DepartmentMinOutDto;

import java.util.Set;

@RestController
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // TODO: CRUD for FAC-ADMIN!


    // FOR FAC-ADMIN, all departments of a faculty to which the admin belongs.
    @GetMapping(value="/fac-admin/departments-dropdown/all-by-faculty", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<DepartmentMinOutDto> findAllByFacIdForDropDown() {
        return departmentService.findAllByFacIdForDropDown();
    }

    // For ORG-ADMIN
    @GetMapping(value="/org-admin/departments-dropdown/all-by-faculty", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<DepartmentMinOutDto> findAllByFacIdForDropDown(@RequestParam final Long facId) {
        return departmentService.findAllByFacIdForDropDown(facId);
    }
}
