package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.ThemeViewService;
import ua.edu.ratos.service.dto.in.ThemeInDto;
import ua.edu.ratos.service.dto.out.view.ThemeOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/instructor/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeViewService themeViewService;


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ThemeInDto dto) {
        final Long themeId = themeService.save(dto);
        log.debug("Saved ThemeDomain :: {} ", themeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(themeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{themeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long themeId, @Valid @RequestBody ThemeInDto dto) {
        themeService.update(themeId, dto);
        log.debug("Updated ThemeDomain ID :: {} ", themeId);
    }

    @DeleteMapping("/{themeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long themeId) {
        themeService.deleteById(themeId);
        log.debug("Deleted ThemeDomain ID :: {}", themeId);
    }

    //------------------------GET----------------------

    @GetMapping(value = "/by-course", params = "courseId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByCourseId(@RequestParam Long courseId) {
        return themeViewService.findAllByCourseId(courseId);
    }

    @GetMapping(value = "/by-course", params = {"courseId", "contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByCourseIdAndThemeLettersContains(@RequestParam Long courseId, @RequestParam String contains) {
        return themeViewService.findAllByCourseIdAndThemeLettersContains(courseId, contains);
    }

    @GetMapping(value = "/by-department", params = {"depId", "page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByDepartmentId(@RequestParam Long depId, @RequestParam int page, @RequestParam int size) {
        return themeViewService.findAllByDepartmentId(depId, PageRequest.of(page, size));
    }

    @GetMapping(value = "/by-department", params = {"depId", "contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByDepartmentIdAndThemeLettersContains(@RequestParam Long depId, @RequestParam String contains) {
        return themeViewService.findAllByDepartmentIdAndThemeLettersContains(depId, contains);
    }

    @GetMapping(value = "/by-organisation", params = {"orgId", "page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByOrganisationId(@RequestParam Long orgId, @RequestParam int page, @RequestParam int size) {
        return themeViewService.findAllByOrganisationId(orgId, PageRequest.of(page, size));
    }

    @GetMapping(value = "/by-organisation", params = {"orgId", "contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeOutDto> findAllByOrganisationIdAndThemeLettersContains(@RequestParam Long orgId, @RequestParam String contains) {
        return themeViewService.findAllByOrganisationIdAndThemeLettersContains(orgId, contains);
    }

}
