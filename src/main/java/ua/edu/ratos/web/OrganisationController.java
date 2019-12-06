package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.OrganisationService;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
public class OrganisationController {

    private final OrganisationService organisationService;

    // TODO: CRUD for GLOBAL-ADMIN!


    //--------------------------------------------------Drop-downs------------------------------------------------------

    @GetMapping(value="/global-admin/organisations-dropdown/all-by-ratos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<OrganisationMinOutDto> findAllOrganisationsForDropDown() {
        Set<OrganisationMinOutDto> result = organisationService.findAllOrganisationsForDropDown();
        log.debug("Organisations = {}", result);
        return result;
    }
}
