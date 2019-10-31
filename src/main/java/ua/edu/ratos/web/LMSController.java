package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.LMSService;
import ua.edu.ratos.service.dto.in.LMSInDto;
import ua.edu.ratos.service.dto.out.LMSOutDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/org-admin")
@AllArgsConstructor
public class LMSController {

    private final LMSService lmsService;

    @PostMapping(value = "/lms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody LMSInDto dto) {
        final Long lmsId = lmsService.save(dto);
        log.debug("Saved LMS, lmsId = {}", lmsId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lmsId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/lms/{lmsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LMSOutDto> findOne(@PathVariable Long lmsId) {
        LMSOutDto dto = lmsService.findOneForEdit(lmsId);
        log.debug("Retrieved LMS = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/lms/{lmsId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long lmsId, @RequestParam String name) {
        lmsService.updateName(lmsId, name);
        log.debug("Updated LMS's name, lmsId = {}, new name = {}", lmsId, name);
    }

    @PutMapping("/lms/{lmsId}/credentials")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long lmsId, @RequestParam String key, @RequestParam String secret) {
        lmsService.updateCredentials(lmsId, key, secret);
        log.debug("Updated LMS's credentials, lmsId = {}, new key = {}, new secret = {}", lmsId, key, secret);
    }

    @DeleteMapping("/lms/{lmsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long lmsId) {
        lmsService.delete(lmsId);
        log.debug("Deleted LMS, lmsId = {}", lmsId);
    }

    //-----------------------------------------------ORG ADMIN table----------------------------------------------------
    @GetMapping(value = "/lms/by-org", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<LMSOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return lmsService.findAllByOrgId(pageable);
    }

    @GetMapping(value = "/lms/by-org", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<LMSOutDto> findAllByStaffId(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return lmsService.findAllByOrgIdAndNameLettersContains(letters, pageable);
    }
}
