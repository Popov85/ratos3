package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.ModeService;
import ua.edu.ratos.service.dto.in.ModeInDto;
import ua.edu.ratos.service.dto.out.ModeOutDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class ModeController {

    private final ModeService modeService;

    @PostMapping(value = "/modes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ModeInDto dto) {
        final Long modeId = modeService.save(dto);
        log.debug("Saved Mode, modeId = {}", modeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(modeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/modes/{modeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModeOutDto> findOne(@PathVariable Long modeId) {
        ModeOutDto dto = modeService.findOneForEdit(modeId);
        log.debug("Retrieved Mode = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include modeId to DTO object
    @PutMapping(value = "/modes/{modeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long modeId, @Valid @RequestBody ModeInDto dto) {
        modeService.update(dto);
        log.debug("Updated Mode, modeId = {}", modeId);
    }

    @DeleteMapping("/modes/{modeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long modeId) {
        modeService.deleteById(modeId);
        log.info("Delete Mode, modeId = {}", modeId);
    }

    //-----------------------------------------------------Default------------------------------------------------------
    @GetMapping(value = "/modes/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ModeOutDto> findAllDefault() {
        return modeService.findAllDefault();
    }

    //---------------------------------------------------Staff table----------------------------------------------------
    @GetMapping(value = "/modes/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> findAllForTableByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForTableByStaffId(pageable);
    }

    @GetMapping(value = "/modes/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> findAllForTableByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForTableByStaffIdAndModeNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/modes/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> findAllForTableByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForTableByDepartmentId(pageable);
    }

    @GetMapping(value = "/modes/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> findAllForTableByDepartmentIdAndNameLettersContains(@RequestParam String letters,@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForTableByDepartmentIdAndModeNameLettersContains(letters, pageable);
    }

    //--------------------------------------------------Staff dropdown list---------------------------------------------
    @GetMapping(value = "/modes/by-staff-dropdown", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ModeOutDto> findAllForDropDownByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForDropDownByStaffIdAndModeNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/modes/by-department-dropdown", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ModeOutDto> findAllForDropDownByDepartmentIdAndNameLettersContains(@RequestParam String letters,@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAllForDropDownByDepartmentIdAndModeNameLettersContains(letters, pageable);
    }
}
