package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.SettingsFBService;
import ua.edu.ratos.service.dto.in.SettingsFBInDto;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class SettingsFBController {

    private final SettingsFBService settingsFBService;

    @PostMapping(value = "/settings-fb", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody SettingsFBInDto dto) {
        final Long setId = settingsFBService.save(dto);
        log.debug("Saved SettingsFB, setId = {}", setId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(setId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/settings-fb/{setId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SettingsFBOutDto> findOne(@PathVariable Long setId) {
        SettingsFBOutDto dto = settingsFBService.findOneForEdit(setId);
        log.debug("Retrieved SettingsFB = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include setId to DTO object
    @PutMapping("/settings-fb/{setId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long setId, @Validated @RequestBody SettingsFBInDto dto) {
        settingsFBService.update(dto);
        log.debug("Updated SettingsFB, setId = {}", setId);
    }

    @DeleteMapping("/settings-fb/{setId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long setId) {
        settingsFBService.deleteById(setId);
        log.info("Deleted SettingsFB, setId = {}", setId);
    }

    //-------------------------------------------GET instructor dropdown-----------------------------------------
    @GetMapping(value="/settings-fb/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SettingsFBOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsFBService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/settings-fb/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SettingsFBOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsFBService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value="/settings-fb/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SettingsFBOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsFBService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/settings-fb/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SettingsFBOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsFBService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
