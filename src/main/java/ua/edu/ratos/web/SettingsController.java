package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.service.SettingsService;
import ua.edu.ratos.service.dto.in.SettingsInDto;
import ua.edu.ratos.service.dto.out.SettingsOutDto;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping("/scheme/settings")
    public ResponseEntity<?> save(@Validated({SettingsInDto.New.class}) @RequestBody SettingsInDto dto) {
        final Long generatedId = settingsService.save(dto);
        log.debug("Saved Settings ID = {} ", generatedId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(generatedId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/scheme/settings/{setId}")
    public void update(@PathVariable Long setId, @Validated({SettingsInDto.New.class}) @RequestBody SettingsInDto dto) {
        settingsService.update(setId, dto);
        log.debug("Updated Settings ID = {} ", dto.getSetId());
    }

    @DeleteMapping("/scheme/settings/{setId}")
    public void delete(@PathVariable Long setId) {
        settingsService.deleteById(setId);
        log.info("Deleted Settings ID = {}", setId);
    }


    //------------------------GET----------------------


    @GetMapping(value="/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return settingsService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value="/settings/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SettingsOutDto> getAllByStaff(Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return settingsService.findAllByStaffId(staffId);
    }

    @GetMapping(value = "/settings/by-staff", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SettingsOutDto>getAllByStaffAndName(@RequestParam String contains, Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return settingsService.findAllByStaffIdAndSettingsNameLettersContains(staffId, contains);
    }

    @GetMapping(value="/settings/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SettingsOutDto> getAllByDepartment(Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return settingsService.findAllByDepartmentId(depId);
    }

    @GetMapping(value = "/settings/by-department", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SettingsOutDto> getAllByDepartmentAndName(@RequestParam String contains, Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return settingsService.findAllByDepartmentIdAndSettingsNameLettersContains(depId, contains);
    }

}
