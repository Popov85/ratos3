package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.Mode;
import ua.edu.ratos.service.ModeService;
import ua.edu.ratos.service.dto.entity.ModeInDto;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/instructor/scheme/mode")
public class ModeController {

    @Autowired
    private ModeService modeService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ModeInDto dto) {
        final Long modeId = modeService.save(dto);
        log.debug("Saved Mode :: {} ", modeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(modeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long modeId, @Valid @RequestBody ModeInDto dto) {
        modeService.update(modeId, dto);
        log.debug("Updated Mode ID :: {} ", modeId);
    }


    @DeleteMapping("/{modeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long modeId) {
        modeService.deleteById(modeId);
        log.info("Delete Mode ID :: {}", modeId);
    }

    /*-------------------GET----------------------*/

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mode> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return modeService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mode> getAllByStaff(Principal principal) {
        return modeService.findAllByStaffId(1L);
    }

    @GetMapping(value = "/by-staff", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Mode> getAllByStaffAndName(@RequestParam String contains) {
        return modeService.findAllByStaffIdAndModeNameLettersContains(1L, contains);
    }

    @GetMapping(value = "/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mode> getAllByDepartment(Principal principal) {
        return modeService.findAllByDepartmentId(1L);
    }

    @GetMapping(value = "/by-department", params = "contains", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Mode> getAllByDepartmentAndName(@RequestParam String contains) {
        return modeService.findAllByDepartmentIdAndModeNameLettersContains(1L, contains);
    }

}
