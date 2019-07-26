package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.SettingsService;
import ua.edu.ratos.service.dto.in.SettingsInDto;
import ua.edu.ratos.service.dto.out.SettingsOutDto;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class SettingsController {

    private SettingsService settingsService;

    @Autowired
    public void setSettingsService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping(value = "/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody SettingsInDto dto) {
        final Long setId = settingsService.save(dto);
        log.debug("Saved Settings, setId = {}", setId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(setId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/settings/{setId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SettingsOutDto> findOne(@PathVariable Long setId) {
        SettingsOutDto dto = settingsService.findOneForEdit(setId);
        log.debug("Retrieved Settings = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include setId to DTO object
    @PutMapping(value = "/settings/{setId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long setId, @Validated @RequestBody SettingsInDto dto) {
        settingsService.update(dto);
        log.debug("Updated Settings, setId = {}", setId);
    }

    @DeleteMapping("/settings/{setId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long setId) {
        settingsService.deleteById(setId);
        log.info("Deleted Settings, setId = {}", setId);
    }

    //--------------------------------------------------Staff table-----------------------------------------------------

    @GetMapping(value="/settings/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/settings/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value="/settings/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/settings/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsService.findAllByDepartmentIdAndSettingsNameLettersContains(letters, pageable);
    }

}
