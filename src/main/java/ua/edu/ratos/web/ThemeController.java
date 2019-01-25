package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.dto.in.ThemeInDto;
import ua.edu.ratos.service.dto.out.ThemeExtendedOutDto;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class ThemeController {

    private ThemeService themeService;

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping(value = "/themes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ThemeInDto dto) {
        final Long themeId = themeService.save(dto);
        log.debug("Saved Theme, themeId = {}", themeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(themeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/themes/{themeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThemeOutDto> findOne(@PathVariable Long themeId) {
        ThemeOutDto dto = themeService.findByIdForUpdate(themeId);
        log.debug("Retrieved Theme = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/themes/{themeId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long themeId, @RequestParam String name) {
        themeService.updateName(themeId, name);
        log.debug("Updated Theme's name themeId = {}, new name = {}", themeId, name);
    }

    @PutMapping(value = "/themes/{themeId}/access")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAccess(@PathVariable Long themeId, @RequestParam Long accessId) {
        themeService.updateAccess(themeId, accessId);
        log.debug("Updated Theme's access, themeId = {}, new accessId = {}", themeId, accessId);
    }

    @PutMapping(value = "/themes/{themeId}/course")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCourse(@PathVariable Long themeId, @RequestParam Long courseId) {
        themeService.updateCourse(themeId, courseId);
        log.debug("Updated Theme's course, themeId = {}, new courseId = {}", themeId, courseId);
    }

    @DeleteMapping("/themes/{themeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long themeId) {
        themeService.deleteById(themeId);
        log.debug("Deleted Theme, themeId = {}", themeId);
    }

    //-----------------------------------GET for THEME TABLES----------------------------

    @GetMapping(value = "/themes/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/themes/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeOutDto> findAllByStaffIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllByStaffIdAndNameContains(letters, pageable);
    }

    @GetMapping(value = "/themes/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/themes/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeOutDto> findAllByDepartmentIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllByDepartmentIdAndNameContains(letters, pageable);
    }

    //-----------------------------------GET for QUESTIONS TABLES----------------------------

    @GetMapping(value = "/themes/questions-by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByStaffId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllForQuestionsTableByStaffId(pageable);
    }

    @GetMapping(value = "/themes/questions-by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByStaffIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllForQuestionsTableByStaffIdAndNameContains(letters, pageable);
    }

    @GetMapping(value = "/themes/questions-by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByDepartmentId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllForQuestionsTableByDepartmentId(pageable);
    }

    @GetMapping(value = "/themes/questions-by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtendedOutDto> findAllForQuestionsTableByDepartmentIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return themeService.findAllForQuestionsTableByDepartmentIdAndNameContains(letters, pageable);
    }

}
