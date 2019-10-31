package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.in.HelpInDto;
import ua.edu.ratos.service.dto.out.HelpOutDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class HelpController {

    private final HelpService helpService;

    @PostMapping(value = "/helps", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody HelpInDto dto) {
        final Long helpId = helpService.save(dto);
        log.debug("Saved Help, helpId = {}", helpId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(helpId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/helps/{helpId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HelpOutDto> findOne(@PathVariable Long helpId) {
        HelpOutDto dto = helpService.findOneForUpdates(helpId);
        log.debug("Retrieved Help = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/helps/{helpId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long helpId, @RequestParam String name) {
        helpService.updateName(helpId, name);
        log.debug("Updated Help's name, helpId = {}, new name = {}", helpId, name);
    }

    @PutMapping(value = "/helps/{helpId}/help")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateHelp(@PathVariable Long helpId, @RequestParam String help) {
        helpService.updateHelp(helpId, help);
        log.debug("Updated Help's help, helpId = {}, new help text = {}", helpId, help);
    }

    @PutMapping(value = "/helps/{helpId}/resource")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateResource(@PathVariable Long helpId, @RequestParam Long resId) {
        helpService.updateResource(helpId, resId);
        log.debug("Updated Help's resource, helpId = {}, new resId = {}", helpId, resId);
    }

    @DeleteMapping("/helps/{helpId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long helpId) {
        helpService.deleteById(helpId);
        log.debug("Deleted Help, helpId = {}", helpId);
    }

    //-----------------------------------------------------Staff--------------------------------------------------------
    @GetMapping(value = "/helps/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return helpService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/helps/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return helpService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/helps/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return helpService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/helps/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return helpService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
