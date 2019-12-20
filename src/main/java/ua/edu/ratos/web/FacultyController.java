package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.FacultyService;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;

import java.util.Set;

@RestController
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;


    // TODO: CRUD for ORG-ADMIN!


    // FOR DEP staff, all faculties of an organisation to which the staff belongs.
    @GetMapping(value="/department/faculties-dropdown/all-fac-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<FacultyMinOutDto> findAllByOrgIdForDropDown() {
        return facultyService.findAllByOrgIdForDropDown();
    }

    // For GLOBAL-ADMIN
    @GetMapping(value="/global-admin/faculties-dropdown/all-fac-by-organisation", params = "orgId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<FacultyMinOutDto> findAllByOrgIdForDropDown(@RequestParam final Long orgId) {
        return facultyService.findAllByOrgIdForDropDown(orgId);
    }
}
