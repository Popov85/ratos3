package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.AccessService;
import ua.edu.ratos.service.dto.out.AccessOutDto;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class AccessController {

    private final AccessService accessService;

    // For dep .staff. to get a set of available accesses
    @GetMapping(value = "/department/accesses-dropdown/all-accesses-by-ratos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccessOutDto> findAllAccessesForDropDown() {
        return accessService.findAllAccessesForDropDown();
    }
}
