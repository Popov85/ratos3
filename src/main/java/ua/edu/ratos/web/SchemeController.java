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
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.SchemeThemeService;
import ua.edu.ratos.service.SchemeThemeSettingsService;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import ua.edu.ratos.service.dto.out.SchemeShortOutDto;
import ua.edu.ratos.service.dto.out.SchemeOutDto;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Scheme CRUD, for REST conventions see also:
 * @see <a href="https://stackoverflow.com/questions/2443324/best-practice-for-partial-updates-in-a-restful-service">link1</a>
 * @see <a href="https://stackoverflow.com/questions/10855388/http-method-to-use-for-adding-to-a-collection-in-a-restful-api">link2</a>
 */
@Slf4j
@RestController
@RequestMapping("/instructor")
@SuppressWarnings("MVCPathVariableInspection")
public class SchemeController {

    private SchemeService schemeService;

    private SchemeThemeService schemeThemeService;

    private SchemeThemeSettingsService schemeThemeSettingsService;

    @Autowired
    public void setSchemeService(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Autowired
    public void setSchemeThemeService(SchemeThemeService schemeThemeService) {
        this.schemeThemeService = schemeThemeService;
    }

    @Autowired
    public void setSchemeThemeSettingsService(SchemeThemeSettingsService schemeThemeSettingsService) {
        this.schemeThemeSettingsService = schemeThemeSettingsService;
    }

    //----------------------------------------------------CRUD----------------------------------------------------------

    @PostMapping(value = "/schemes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SchemeInDto dto) {
        final Long schemeId = schemeService.save(dto);
        log.debug("Saved Scheme, schemeId = {}", schemeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeId).toUri();
        // returns location like http://localhost:8090/instructor/schemes/21
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/schemes/{schemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeOutDto> findOne(@PathVariable Long schemeId) {
        SchemeOutDto dto = schemeService.findByIdForUpdate(schemeId);
        log.debug("Retrieved Scheme = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // ---------------------------------------------------UPDATE--------------------------------------------------------
    // Set of endpoints to immediately update any changes to Scheme object with AJAX

    @PutMapping("/schemes/{schemeId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long schemeId, @RequestParam String name) {
        schemeService.updateName(schemeId, name);
        log.debug("Updated Scheme's name, schemeId = {}, new name = {}", schemeId, name);
    }

    @PutMapping("/schemes/{schemeId}/settings")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSettings(@PathVariable Long schemeId, @RequestParam Long setId) {
        schemeService.updateSettings(schemeId, setId);
        log.debug("Updated Scheme's Settings, schemeId = {}, new setId = {}", schemeId, setId);
    }

    @PutMapping("/schemes/{schemeId}/strategy")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateStrategy(@PathVariable Long schemeId, @RequestParam Long strId) {
        schemeService.updateStrategy(schemeId, strId);
        log.debug("Updated Scheme's Strategy, schemeId = {}, new strId = {}", schemeId, strId);
    }

    @PutMapping("/schemes/{schemeId}/mode")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMode(@PathVariable Long schemeId, @RequestParam Long modeId) {
        schemeService.updateMode(schemeId, modeId);
        log.debug("Updated Scheme's Mode, schemeId = {}, new modeId = {}", schemeId, modeId);
    }

    @PutMapping("/schemes/{schemeId}/access")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAccess(@PathVariable Long schemeId, @RequestParam Long accessId) {
        schemeService.updateAccess(schemeId, accessId);
        log.debug("Updated Scheme's Access, schemeId = {}, new accessId = {}", schemeId, accessId);
    }

    @PutMapping("/schemes/{schemeId}/grading")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateGrading(@PathVariable Long schemeId, @RequestParam Long gradingId, @RequestParam Long gradingDetailsId) {
        schemeService.updateGrading(schemeId, gradingId, gradingDetailsId);
        log.debug("Updated Scheme's Grading, schemeId = {}, new gradId = {}, new detailsId = {}", schemeId, gradingId, gradingDetailsId);
    }

    @PutMapping("/schemes/{schemeId}/course")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCourse(@PathVariable Long schemeId, @RequestParam Long courseId) {
        schemeService.updateCourse(schemeId, courseId);
        log.debug("Updated Scheme's Course, schemeId = {}, new courseId = {}", schemeId, courseId);
    }

    @PutMapping("/schemes/{schemeId}/is-active")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateIsActive(@PathVariable Long schemeId, @RequestParam boolean isActive) {
        schemeService.updateIsActive(schemeId, isActive);
        log.debug("Updated Scheme's isActive flag, schemeId = {}, now the flag is = {}", schemeId, isActive);
    }

    @PutMapping("/schemes/{schemeId}/is-lms-only")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateIsLmsOnly(@PathVariable Long schemeId, @RequestParam boolean isLmsOnly) {
        schemeService.updateIsLmsOnly(schemeId, isLmsOnly);
        log.debug("Updated Scheme's isLmsOnly flag, schemeId = {}, now the flag is = {}", schemeId, isLmsOnly);
    }


    @DeleteMapping("/schemes/{schemeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long schemeId) {
        schemeService.deleteById(schemeId);
        log.debug("Deleted Scheme, schemeId = {}", schemeId);
    }


    //--------------------------------------------------------GROUPS----------------------------------------------------

    // If the groups being sent in the request body are intended to be added to a collection, rather than replace,
    // I would suggest POST. If you intend to replace the existing tags, use PUT.

    @PostMapping(value = "/schemes/{schemeId}/groups/{groupId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addGroup(@PathVariable Long schemeId, @PathVariable Long groupId) {
        schemeService.addGroup(schemeId, groupId);
        log.debug("Added Group to Scheme, schemeId = {}, groupId = {}", schemeId, groupId);
    }

    @DeleteMapping(value = "/schemes/{schemeId}/groups/{groupId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeGroup(@PathVariable Long schemeId, @PathVariable Long groupId) {
        schemeService.removeGroup(schemeId, groupId);
        log.debug("Removed Group from Scheme, schemeId = {}, groupId = {}", schemeId, groupId);
    }
    
    // -------------------------------------------------------THEMES----------------------------------------------------

    @PostMapping(value = "/schemes/{schemeId}/themes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTheme(@PathVariable Long schemeId, @Valid @RequestBody SchemeThemeInDto dto) {
        final Long schemeThemeId = schemeThemeService.save(schemeId,  dto);
        log.debug("Added Theme to Scheme, created schemeThemeId = {} ", schemeThemeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeThemeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/schemes/{schemeId}/themes{schemeThemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeThemeOutDto> findSchemeTheme(@PathVariable Long schemeThemeId) {
        SchemeThemeOutDto dto = schemeThemeService.findOne(schemeThemeId);
        log.debug("Retrieved SchemeTheme = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/schemes/{schemeId}/themes/{schemeThemeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeTheme(@PathVariable Long schemeId, @PathVariable Long schemeThemeId) {
        schemeService.removeTheme(schemeId, schemeThemeId);
        log.debug("Deleted schemeThemeId = {}", schemeThemeId);
    }

    @PutMapping("/schemes/{schemeId}/themes/re-order")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void reOrderThemes(@PathVariable Long schemeId, @RequestBody List<Long> schemeThemeIds) {
        schemeService.reOrderThemes(schemeId, schemeThemeIds);
        log.debug("Re-ordered Themes of schemeId = {}", schemeId);
    }

    // ------------------------------------------------------SETTINGS---------------------------------------------------

    @PostMapping(value = "/schemes/{schemeId}/themes/{schemeThemeId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSchemeThemeSettings(@Valid @RequestBody SchemeThemeSettingsInDto dto) {
        final Long schemeThemeSettingsId = schemeThemeSettingsService.save(dto);
        log.debug("Saved schemeThemeSettingsId = {} ", schemeThemeSettingsId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeThemeSettingsId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/schemes/{schemeId}/themes/{schemeThemeId}/settings/{schemeThemeSettingsId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSchemeThemeSettings(@PathVariable Long schemeThemeSettingsId, @Valid @RequestBody SchemeThemeSettingsInDto dto) {
        schemeThemeSettingsService.update(schemeThemeSettingsId, dto);
        log.debug("Updated schemeThemeSettingsId = {}", schemeThemeSettingsId);
    }

    @DeleteMapping(value = "/schemes/{schemeId}/themes/{schemeThemeId}/settings/{schemeThemeSettingsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeSchemeThemeSettings(@PathVariable Long schemeThemeId, @PathVariable Long schemeThemeSettingsId) {
        schemeThemeService.removeSettings(schemeThemeId, schemeThemeSettingsId);
        log.debug("Deleted schemeThemeSettingsId = {}", schemeThemeSettingsId);
    }

    //----------------------------------------------------Staff table---------------------------------------------------

    @GetMapping(value = "/schemes/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByStaffId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 30) Pageable pageable) {
        return schemeService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/schemes/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByDepartmentId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return schemeService.findAllByDepartmentId(pageable);
    }

    //--------------------------------------------------Search in table-------------------------------------------------

    @GetMapping(value = "/schemes/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByStaffIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllByStaffIdAndName(letters, contains, pageable);
    }

    @GetMapping(value = "/schemes/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByDepartmentIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllByDepartmentIdAndName(letters, contains, pageable);
    }

    //--------------------------------------------------Slice drop-down-------------------------------------------------

    @GetMapping(value = "/schemes-dropdown/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SchemeShortOutDto> findAllForDropDownByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return schemeService.findAllForDropDownByDepartmentId(pageable);
    }

    @GetMapping(value = "/schemes-dropdown/by-course", params = {"courseId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SchemeShortOutDto> findAllForDropDownByCourseId(@RequestParam Long courseId, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllForDropDownByCourseId(courseId, pageable);
    }

    //------------------------------------------------Search in drop-down-----------------------------------------------

    @GetMapping(value = "/schemes-dropdown/by-department",  params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SchemeShortOutDto> findAllForDropDownByDepartmentIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return schemeService.findAllForDropDownByDepartmentIdAndName(letters, contains, pageable);
    }

    @GetMapping(value = "/schemes-dropdown/by-course", params = {"courseId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SchemeShortOutDto> findAllForDropDownByCourseIdAndName(@RequestParam Long courseId, @RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllForDropDownByCourseIdAndName(courseId, letters, contains, pageable);
    }
}
