package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.grading.SchemeThemeSettingsService;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import java.net.URI;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = "/instructor/scheme/scheme-theme-settings")
public class SchemeThemeSettingsController {

    @Autowired
    private SchemeThemeSettingsService schemeThemeSettingsService;

    @PostMapping(value = "/" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated({SchemeThemeSettingsInDto.NewAndUpdate.class}) @RequestBody SchemeThemeSettingsInDto dto) {
        final Long schemeThemeSettingsId = schemeThemeSettingsService.save(dto);
        log.debug("Saved SchemeThemeSettings :: {} ", schemeThemeSettingsId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(schemeThemeSettingsId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{setId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long setId,  @Validated({SchemeThemeSettingsInDto.NewAndUpdate.class}) @RequestBody SchemeThemeSettingsInDto dto) {
        schemeThemeSettingsService.update(setId, dto);
        log.debug("Updated SchemeThemeSettings ID :: {} ", setId);
    }

    @DeleteMapping("/{setId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long setId) {
        schemeThemeSettingsService.deleteById(setId);
        log.debug("Deleted SchemeThemeSettings ID  :: {}", setId);
    }

    /*-------------------GET-----------------*/

    @GetMapping(value = "/by-scheme-theme", params = "schemeThemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeThemeSettings> findAllBySchemeThemeId(@RequestParam Long schemeThemeId) {
        return schemeThemeSettingsService.findAllBySchemeThemeId(schemeThemeId);
    }
}
