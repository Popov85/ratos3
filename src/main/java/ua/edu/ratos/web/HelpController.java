package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.in.HelpInDto;
import ua.edu.ratos.service.dto.out.HelpOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class HelpController {

    @Autowired
    private HelpService helpService;

    @PostMapping(value = "/question/help", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody HelpInDto dto) {
        final Long helpId = helpService.save(dto);
        log.debug("Saved Help = {} ", helpId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(helpId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/question/help/{helpId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long helpId, @Valid @RequestBody HelpInDto dto) {
        helpService.update(helpId, dto);
        log.debug("Updated Help ID = {} ", helpId);
    }

    @DeleteMapping("/question/help/{helpId}")
    public void delete(@PathVariable Long helpId) {
        helpService.deleteById(helpId);
        log.debug("Deleted Help ID = {}", helpId);
    }

    //-------------------GET-----------------

    @GetMapping(value = "/helps", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return helpService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/helps/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HelpOutDto> findAllByStaffId(Authentication auth) {
        Long userId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return helpService.findByStaffIdWithResources(userId);
    }

    @GetMapping(value = "/helps/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HelpOutDto> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Authentication auth) {
        Long userId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return helpService.findByStaffIdAndFirstNameLettersWithResources(userId, starts);
    }

    @GetMapping(value = "/helps/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HelpOutDto> findAllByDepartmentId(Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return helpService.findByDepartmentIdWithResources(depId);
    }

    @GetMapping(value = "/helps/by-department", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HelpOutDto> findAllByDepartmentIdAndLettersStarts(@RequestParam String starts, Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return helpService.findByDepartmentIdAndFirstNameLettersWithResources(depId, starts);
    }
}
