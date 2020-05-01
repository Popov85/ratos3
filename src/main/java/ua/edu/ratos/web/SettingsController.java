package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @PostMapping(value = "/instructor/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody SettingsInDto dto) {
        final Long setId = settingsService.save(dto);
        log.debug("Saved Settings, setId = {}", setId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(setId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/instructor/settings/{setId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SettingsOutDto> findOne(@PathVariable Long setId) {
        SettingsOutDto dto = settingsService.findOneForEdit(setId);
        log.debug("Retrieved Settings = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include setId to DTO object
    @PutMapping(value = "/instructor/settings/{setId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long setId, @Validated @RequestBody SettingsInDto dto) {
        settingsService.update(dto);
        log.debug("Updated Settings, setId = {}", setId);
    }

    @DeleteMapping("/instructor/settings/{setId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long setId) {
        settingsService.deleteById(setId);
        log.info("Deleted Settings, setId = {}", setId);
    }

    //-----------------------------------------------------Default------------------------------------------------------
    @GetMapping(value = "/department/settings/all-default-settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SettingsOutDto> findAllDefault() {
        return settingsService.findAllDefault();
    }

    //----------------------------------------------Staff table/drop-down-----------------------------------------------

    @GetMapping(value="/department/settings/all-settings-by-department-with-default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SettingsOutDto> findAllByDepartmentWithDefault() {
        return settingsService.findAllByDepartmentWithDefault();
    }

}
