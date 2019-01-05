package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.grading.SchemeThemeService;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(path = "/instructor/scheme/scheme-theme")
public class SchemeThemeController {

    @Autowired
    private SchemeThemeService schemeThemeService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SchemeThemeInDto dto) {
        final Long schemeThemeId = schemeThemeService.save(dto);
        log.debug("Associated ThemeDomain with SchemeDomain :: {} ", schemeThemeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(schemeThemeId).toUri();
        return ResponseEntity.created(location).build();
    }
}
