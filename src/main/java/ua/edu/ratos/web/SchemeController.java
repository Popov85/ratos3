package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ua.edu.ratos.service.dto.out.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Scheme CRUD, for REST conventions see also:
 * @see <a href="https://stackoverflow.com/questions/2443324/best-practice-for-partial-updates-in-a-restful-service">link1</a>
 * @see <a href="https://stackoverflow.com/questions/10855388/http-method-to-use-for-adding-to-a-collection-in-a-restful-api">link2</a>
 */
@Slf4j
@RestController
@SuppressWarnings("MVCPathVariableInspection")
@AllArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;

    private final SchemeThemeService schemeThemeService;

    private final SchemeThemeSettingsService schemeThemeSettingsService;

    //----------------------------------------------------CRUD----------------------------------------------------------
    @PostMapping(value = "/instructor/schemes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SchemeInDto dto) {
        final Long schemeId = schemeService.save(dto);
        log.debug("Saved Scheme, schemeId = {}", schemeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/instructor/schemes/{schemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeOutDto> findOne(@PathVariable Long schemeId) {
        SchemeOutDto dto = schemeService.findByIdForUpdate(schemeId);
        log.debug("Retrieved Scheme = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // ---------------------------------------------------UPDATE--------------------------------------------------------
    // Set of endpoints to immediately update any changes to Scheme object with AJAX
    @PutMapping("/instructor/schemes/{schemeId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long schemeId, @RequestParam String name) {
        schemeService.updateName(schemeId, name);
        log.debug("Updated Scheme's name, schemeId = {}, new name = {}", schemeId, name);
    }

    @PutMapping("/instructor/schemes/{schemeId}/settings")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSettings(@PathVariable Long schemeId, @RequestParam Long setId) {
        schemeService.updateSettings(schemeId, setId);
        log.debug("Updated Scheme's Settings, schemeId = {}, new setId = {}", schemeId, setId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/strategy")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateStrategy(@PathVariable Long schemeId, @RequestParam Long strId) {
        schemeService.updateStrategy(schemeId, strId);
        log.debug("Updated Scheme's Strategy, schemeId = {}, new strId = {}", schemeId, strId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/mode")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMode(@PathVariable Long schemeId, @RequestParam Long modeId) {
        schemeService.updateMode(schemeId, modeId);
        log.debug("Updated Scheme's Mode, schemeId = {}, new modeId = {}", schemeId, modeId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/access")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAccess(@PathVariable Long schemeId, @RequestParam Long accessId) {
        schemeService.updateAccess(schemeId, accessId);
        log.debug("Updated Scheme's Access, schemeId = {}, new accessId = {}", schemeId, accessId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/grading")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateGrading(@PathVariable Long schemeId, @RequestParam Long gradingId, @RequestParam Long gradingDetailsId) {
        schemeService.updateGrading(schemeId, gradingId, gradingDetailsId);
        log.debug("Updated Scheme's Grading, schemeId = {}, new gradId = {}, new detailsId = {}", schemeId, gradingId, gradingDetailsId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/course")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCourse(@PathVariable Long schemeId, @RequestParam Long courseId) {
        schemeService.updateCourse(schemeId, courseId);
        log.debug("Updated Scheme's Course, schemeId = {}, new courseId = {}", schemeId, courseId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/is-active")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateIsActive(@PathVariable Long schemeId, @RequestParam boolean isActive) {
        schemeService.updateIsActive(schemeId, isActive);
        log.debug("Updated Scheme's isActive flag, schemeId = {}, now the flag is = {}", schemeId, isActive);
    }

    @PutMapping("/instructor/schemes/{schemeId}/is-lms-only")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateIsLmsOnly(@PathVariable Long schemeId, @RequestParam boolean isLmsOnly) {
        schemeService.updateIsLmsOnly(schemeId, isLmsOnly);
        log.debug("Updated Scheme's isLmsOnly flag, schemeId = {}, now the flag is = {}", schemeId, isLmsOnly);
    }

    @DeleteMapping("/instructor/schemes/{schemeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long schemeId) {
        schemeService.deleteById(schemeId);
        log.debug("Deleted Scheme, schemeId = {}", schemeId);
    }

    //--------------------------------------------------------GROUPS----------------------------------------------------
    // If the groups being sent in the request body are intended to be added to a collection, rather than replace,
    // I would suggest POST. If you intend to replace the existing tags, use PUT.
    @PostMapping(value = "/instructor/schemes/{schemeId}/groups/{groupId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addGroup(@PathVariable Long schemeId, @PathVariable Long groupId) {
        schemeService.addGroup(schemeId, groupId);
        log.debug("Added Group to Scheme, schemeId = {}, groupId = {}", schemeId, groupId);
    }

    @DeleteMapping(value = "/instructor/schemes/{schemeId}/groups/{groupId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeGroup(@PathVariable Long schemeId, @PathVariable Long groupId) {
        schemeService.removeGroup(schemeId, groupId);
        log.debug("Removed Group from Scheme, schemeId = {}, groupId = {}", schemeId, groupId);
    }
    
    // -------------------------------------------------------THEMES----------------------------------------------------
    @PostMapping(value = "/instructor/schemes/{schemeId}/themes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTheme(@PathVariable Long schemeId, @Valid @RequestBody SchemeThemeInDto dto) {
        final Long schemeThemeId = schemeThemeService.save(schemeId,  dto);
        log.debug("Added Theme to Scheme, created schemeThemeId = {} ", schemeThemeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeThemeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/instructor/schemes/{schemeId}/themes{schemeThemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeThemeOutDto> findSchemeTheme(@PathVariable Long schemeThemeId) {
        SchemeThemeOutDto dto = schemeThemeService.findOne(schemeThemeId);
        log.debug("Retrieved SchemeTheme = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/instructor/schemes/{schemeId}/themes/{schemeThemeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeTheme(@PathVariable Long schemeId, @PathVariable Long schemeThemeId) {
        schemeService.removeTheme(schemeId, schemeThemeId);
        log.debug("Deleted schemeThemeId = {}", schemeThemeId);
    }

    @PutMapping("/instructor/schemes/{schemeId}/themes/re-order")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void reOrderThemes(@PathVariable Long schemeId, @RequestBody List<Long> schemeThemeIds) {
        schemeService.reOrderThemes(schemeId, schemeThemeIds);
        log.debug("Re-ordered Themes of schemeId = {}", schemeId);
    }

    // ------------------------------------------------------SETTINGS---------------------------------------------------
    @PostMapping(value = "/instructor/schemes/{schemeId}/themes/{schemeThemeId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSchemeThemeSettings(@Valid @RequestBody SchemeThemeSettingsInDto dto) {
        final Long schemeThemeSettingsId = schemeThemeSettingsService.save(dto);
        log.debug("Saved schemeThemeSettingsId = {} ", schemeThemeSettingsId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(schemeThemeSettingsId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/instructor/schemes/{schemeId}/themes/{schemeThemeId}/settings/{schemeThemeSettingsId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSchemeThemeSettings(@PathVariable Long schemeThemeSettingsId, @Valid @RequestBody SchemeThemeSettingsInDto dto) {
        schemeThemeSettingsService.update(schemeThemeSettingsId, dto);
        log.debug("Updated schemeThemeSettingsId = {}", schemeThemeSettingsId);
    }

    @DeleteMapping(value = "/instructor/schemes/{schemeId}/themes/{schemeThemeId}/settings/{schemeThemeSettingsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeSchemeThemeSettings(@PathVariable Long schemeThemeId, @PathVariable Long schemeThemeSettingsId) {
        schemeThemeService.removeSettings(schemeThemeId, schemeThemeSettingsId);
        log.debug("Deleted schemeThemeSettingsId = {}", schemeThemeSettingsId);
    }

    //----------------------------------------------------Staff table---------------------------------------------------
    @GetMapping(value = "/instructor/schemes/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByStaffId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 30) Pageable pageable) {
        return schemeService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/instructor/schemes/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByDepartmentId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return schemeService.findAllByDepartmentId(pageable);
    }

    //--------------------------------------------------Search in table-------------------------------------------------
    @GetMapping(value = "/instructor/schemes/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByStaffIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllByStaffIdAndName(letters, contains, pageable);
    }

    @GetMapping(value = "/instructor/schemes/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllByDepartmentIdAndName(@RequestParam String letters, @RequestParam boolean contains, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return schemeService.findAllByDepartmentIdAndName(letters, contains, pageable);
    }

    //-------------------------------------------Staff min drop-down----------------------------------------------------

    @GetMapping(value="/department/schemes-dropdown/all-by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByStaffId() {
        return schemeService.findAllForDropdownByStaffId();
    }

    @GetMapping(value="/department/schemes-dropdown/all-by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByDepartmentId() { return schemeService.findAllForDropdownByDepartmentId(); }

    @GetMapping(value="/department/schemes-dropdown/all-by-course", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByCourseId(@RequestParam final Long courseId) { return schemeService.findAllForDropdownByCourseId(courseId); }

    @GetMapping(value="/fac-admin/schemes-dropdown/all-by-faculty", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByFacultyId() { return schemeService.findAllForDropdownByFacultyId(); }

    @GetMapping(value="/fac-admin/schemes-dropdown/all-by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByDepartmentId(@RequestParam final Long depId) { return schemeService.findAllForDropdownByDepartmentId(depId); }





    @GetMapping(value="/org-admin/schemes-dropdown/all-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByOrganisationId() { return schemeService.findAllForDropdownByOrganisationId(); }

    @GetMapping(value="/org-admin/schemes-dropdown/all-by-faculty", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByFacultyId(@RequestParam final Long facId) { return schemeService.findAllForDropdownByFacultyId(facId); }

    @GetMapping(value="/global-admin/schemes-dropdown/all-by-ratos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByRatosInstance() { return schemeService.findAllForDropdown(); }

    @GetMapping(value="/global-admin/schemes-dropdown/all-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchemeMinOutDto> findAllForDropDownByOrganisationId(@RequestParam final Long orgId) { return schemeService.findAllForDropdownByOrganisationId(orgId); }


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
