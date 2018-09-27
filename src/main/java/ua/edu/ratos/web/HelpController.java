package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/instructor/question/help")
public class HelpController {

    @Autowired
    private HelpService helpService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody HelpInDto dto) {
        final Long helpId = helpService.save(dto);
        log.debug("Saved Help :: {} ", helpId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(helpId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{helpId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long helpId, @Valid @RequestBody HelpInDto dto) {
        helpService.update(helpId, dto);
        log.debug("Updated Help ID :: {} ", helpId);
    }

    @DeleteMapping("/{helpId}")
    public void delete(@PathVariable Long helpId) {
        helpService.deleteById(helpId);
        log.debug("Deleted Help ID :: {}", helpId);
    }

    /*-------------------GET-----------------*/

    @GetMapping(value = "/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Help> findAllByStaffId(Principal principal) {
        return helpService.findByStaffIdWithResources(1L);
    }

    @GetMapping(value = "/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Help> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Principal principal) {
        return helpService.findByStaffIdAndFirstNameLettersWithResources(1L, starts);
    }

    @GetMapping(value = "/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Help> findAllByDepartmentId(Principal principal) {
        return helpService.findByDepartmentIdWithResources(1L);
    }

    @GetMapping(value = "/by-department", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Help> findAllByDepartmentIdAndLettersStarts(@RequestParam String starts, Principal principal) {
        return helpService.findByDepartmentIdAndFirstNameLettersWithResources(1L, starts);
    }

}
