package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.StaffService;
import ua.edu.ratos.service.dto.in.EmailInDto;
import ua.edu.ratos.service.dto.in.RoleByDepInDto;
import ua.edu.ratos.service.dto.in.StaffInDto;
import ua.edu.ratos.service.dto.in.UserMinInDto;
import ua.edu.ratos.service.dto.out.StaffOutDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/dep-admin")
@AllArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping(value = "/staff", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody StaffInDto dto) {
        final Long staffForTableId = staffService.save(dto);
        log.debug("Saved Staff, staffId = {}", staffForTableId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(staffForTableId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/staff/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffOutDto> findOne(@PathVariable Long staffId) {
        StaffOutDto dto = staffService.findOneForEdit(staffId);
        log.debug("Retrieved Staff = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/staff/{staffId}/name-surname")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateNameAndSurname(@PathVariable Long staffId, @Valid @RequestBody UserMinInDto dto) {
        staffService.updateNameAndSurname(staffId, dto);
        log.debug("Updated Staff's name and surname, staffId = {}, new name and surname = {}", staffId, dto);
    }

    @PutMapping(value = "/staff/{staffId}/email")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateEmail(@PathVariable Long staffId, @Valid @RequestBody EmailInDto dto) {
        staffService.updateEmail(staffId, dto);
        log.debug("Updated Staff's email, staffId = {}, new email = {}", staffId, dto);
    }

    @PutMapping(value = "/staff/{staffId}/position")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePosition(@PathVariable Long staffId, @RequestParam Long positionId) {
        staffService.updatePosition(staffId, positionId);
        log.debug("Updated Staff's position, staffId = {}, new positionId = {}", staffId, positionId);
    }

    @PutMapping("/staff/{staffId}/inactive")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void disable(@PathVariable Long staffId) {
        staffService.deactivate(staffId);
        log.debug("Disabled Staff, staffId = {}", staffId);
    }

    //-----------------------------------------------ROLE OPERATIONS----------------------------------------------------
    @PostMapping(value = "/staff/{staffId}/roles/{roleId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRole(@PathVariable Long staffId, @PathVariable Long roleId, @Valid @RequestBody RoleByDepInDto dto) {
        staffService.addRoleByDepAdmin(staffId, dto);
        log.debug("Added Role to Staff, staffId = {}, roleId = {}", staffId, roleId);
    }

    @DeleteMapping(value = "/staff/{staffId}/roles/{roleId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeRole(@PathVariable Long staffId, @PathVariable Long roleId, @Valid @RequestBody RoleByDepInDto dto) {
        staffService.removeRoleByDepAdmin(staffId, dto);
        log.debug("Removed Role from Staff, staffId = {}, roleId = {}", staffId, roleId);
    }

    //-----------------------------------------------DEP admin table----------------------------------------------------
    @GetMapping(value = "/staff/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StaffOutDto> findAllByDepartmentId(@PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return staffService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/staff/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StaffOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return staffService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
