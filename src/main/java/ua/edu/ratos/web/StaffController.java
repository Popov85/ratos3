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
import ua.edu.ratos.service.dto.in.StaffInDto;
import ua.edu.ratos.service.dto.in.StaffUpdInDto;
import ua.edu.ratos.service.dto.out.StaffOutDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

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

    @PutMapping(value = "/staff", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody StaffUpdInDto dto) {
        staffService.update(dto);
        log.debug("Updated Staff, staffId = {}", dto.getStaffId());
    }

    @PutMapping(value = "/staff/{staffId}/update-position")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePosition(@PathVariable Long staffId, @RequestParam Long positionId) {
        staffService.updatePosition(staffId, positionId);
        log.debug("Updated Staff's position, staffId = {}, new positionId = {}", staffId, positionId);
    }

    @PutMapping(value = "/staff/{staffId}/update-role")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateRole(@PathVariable Long staffId, @RequestParam String role) {
        staffService.updateRole(staffId, role);
        log.debug("Updated Staff's role, staffId = {}, new role = {}", staffId, role);
    }

    //-------------------------------------------------ONE--------------------------------------------------------------
    @GetMapping(value = "/staff/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffOutDto> findOne(@PathVariable Long staffId) {
        StaffOutDto dto = staffService.findOneForEdit(staffId);
        log.debug("Retrieved Staff = {}", dto);
        return ResponseEntity.ok(dto);
    }

    //-----------------------------------------------DEP admin table----------------------------------------------------
    @GetMapping(value = "/staff/all-by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<StaffOutDto> findAllByDepartmentId() {
        return staffService.findAllByDepartmentId();
    }

    @GetMapping(value = "/staff/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StaffOutDto> findAllByDepartmentId(@PageableDefault(sort = {"user.surname"}, value = 20) Pageable pageable) {
        return staffService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/staff/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StaffOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"user.surname"}, value = 20) Pageable pageable) {
        return staffService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
