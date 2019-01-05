package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.grading.SchemeService;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/instructor/scheme")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SchemeInDto dto) {
        final Long schemeId = schemeService.save(dto);
        log.debug("Saved Scheme :: {} ", schemeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(schemeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{schemeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long schemeId,  @Valid @RequestBody SchemeInDto dto) {
        schemeService.update(schemeId, dto);
        log.debug("Updated Scheme ID :: {} ", schemeId);
    }

    @DeleteMapping("/{schemeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long schemeId) {
        schemeService.deleteById(schemeId);
        log.debug("Deleted Scheme ID :: {}", schemeId);
    }


    @PutMapping("/{schemeId}")
    public void reOrder(@PathVariable Long schemeId, @RequestBody List<Long> schemeThemeIds) {
        schemeService.reOrder(schemeId, schemeThemeIds);
        log.debug("Re-ordered themes:: {}", schemeThemeIds);
    }

    @DeleteMapping("/{schemeId}/{themeIndex}")
    public void deleteTheme(@PathVariable Long schemeId, @PathVariable Integer themeIndex) {
        schemeService.deleteByIndex(schemeId, themeIndex);
        log.debug("Disassociated Theme currentIndex = {} with given SchemeDomain ID={}", themeIndex, schemeId);
    }

    /*-----------------SELECT---------------------*/



}
