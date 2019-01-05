package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.service.ModeService;
import ua.edu.ratos.service.dto.in.ModeInDto;
import ua.edu.ratos.service.dto.out.ModeOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class ModeController {

    @Autowired
    private ModeService modeService;

    @PostMapping(value = "/scheme/mode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ModeInDto dto) {
        final Long modeId = modeService.save(dto);
        log.debug("Saved Mode :: {} ", modeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(modeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/scheme/mode/{modeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long modeId, @Valid @RequestBody ModeInDto dto) {
        modeService.update(modeId, dto);
        log.debug("Updated Mode ID :: {} ", modeId);
    }

    @DeleteMapping("/scheme/mode/{modeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long modeId) {
        modeService.deleteById(modeId);
        log.info("Delete Mode ID :: {}", modeId);
    }

    /*-------------------GET----------------------*/

    @GetMapping(value = "/modes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return modeService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/modes/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModeOutDto> getAllByStaff(Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return modeService.findAllByStaffId(staffId);
    }

    @GetMapping(value = "/modes/by-staff", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModeOutDto> getAllByStaffAndName(@RequestParam String contains, Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return modeService.findAllByStaffIdAndModeNameLettersContains(staffId, contains);
    }

    @GetMapping(value = "/modes/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModeOutDto> getAllByDepartment(Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return modeService.findAllByDepartmentId(depId);
    }

    @GetMapping(value = "/modes/by-department", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModeOutDto> getAllByDepartmentAndName(@RequestParam String contains, Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return modeService.findAllByDepartmentIdAndModeNameLettersContains(depId, contains);
    }

}
