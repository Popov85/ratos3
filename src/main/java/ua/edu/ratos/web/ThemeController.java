package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.dto.in.ThemeInDto;
import ua.edu.ratos.service.dto.out.ThemeExtOutDto;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import ua.edu.ratos.service.dto.out.ThemeMapOutDto;
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

    //--------------------------------------------Staff theme-questions table-------------------------------------------

    @GetMapping(value = "/themes-questions/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByStaffId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return themeService.findAllForQuestionsTableByStaffId(pageable);
    }

    @GetMapping(value = "/themes-questions/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return themeService.findAllForQuestionsTableByDepartmentId(pageable);
    }

    //---------------------------------------------------Table search---------------------------------------------------

    @GetMapping(value = "/themes-questions/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByStaffIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 20) Pageable pageable) {
        return themeService.findAllForQuestionsTableByStaffIdAndName(letters, contains, pageable);
    }

    @GetMapping(value = "/themes-questions/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeExtOutDto> findAllForQuestionsTableByDepartmentIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 20) Pageable pageable) {
        return themeService.findAllForQuestionsTableByDepartmentIdAndName(letters, contains, pageable);
    }

    //-------------------------------------------Dropdown scheme creating support---------------------------------------

    @GetMapping(value = "/themes-questions-dropdown/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ThemeExtOutDto> findAllForDropDownByStaffId(@PageableDefault(sort = {"name"}, value = 20) Pageable pageable) {
        return themeService.findAllForDropDownByStaffId(pageable);
    }

    @GetMapping(value = "/themes-questions-dropdown/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ThemeExtOutDto> findAllForDropDownByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return themeService.findAllForDropDownByDepartmentId(pageable);
    }

    //----------------------------------------------------Dropdown search-----------------------------------------------

    @GetMapping(value = "/themes-questions-dropdown/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ThemeExtOutDto> findAllForDropDownByStaffIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 20) Pageable pageable) {
        return themeService.findAllForDropDownByStaffIdAndName(letters, contains, pageable);
    }

    @GetMapping(value = "/themes-questions-dropdown/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ThemeExtOutDto> findAllForDropDownByDepartmentIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 20) Pageable pageable) {
        return themeService.findAllForDropDownByDepartmentIdAndName(letters, contains, pageable);
    }


    //---------------------------------------------Scheme creating support with levels----------------------------------

    /**
     * Use this endpoint for scheme creating support to obtain all existing types and levels in this theme.
     * It works rather fast (100-500ms) with questions per theme = 200-500 pieces,
     * but requires to fetch all questions from theme for calculations.
     * So it will presumably work slower with questions per theme >1000. Be careful to use it in this case!
     * @param themeId theme you want to add to scheme
     * @return DTO with details on question types and levels present in this theme.
     * Front-end has to prevent a user from setting values greater than these limits.
     */
    @GetMapping(value = "/themes-map/{themeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ThemeMapOutDto getQuestionTypeLevelMapByThemeId(@PathVariable Long themeId) {
        return themeService.getQuestionTypeLevelMapByThemeId(themeId);
    }

}
