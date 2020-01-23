package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.LMSService;
import ua.edu.ratos.service.dto.in.LMSInDto;
import ua.edu.ratos.service.dto.out.LMSMinOutDto;
import ua.edu.ratos.service.dto.out.LMSOutDto;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
public class LMSController {

    private final LMSService lmsService;

    @PostMapping(value = "/org-admin/lms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long save(@Valid @RequestBody LMSInDto dto) {
        final Long lmsId = lmsService.save(dto);
        log.debug("Saved LMS, lmsId = {}", lmsId);
        return lmsId;
    }

    @PutMapping(value = "/org-admin/lms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@Valid @RequestBody LMSInDto dto) {
        lmsService.update(dto);
        log.debug("Updated LMS, lmsId = {}", dto.getLmsId());
    }

    @PatchMapping("/org-admin/lms/{lmsId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long lmsId, @RequestParam String name) {
        lmsService.updateName(lmsId, name);
        log.debug("Updated LMS's name, lmsId = {}, new name = {}", lmsId, name);
    }

    @PatchMapping("/org-admin/lms/{lmsId}/credentials")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long lmsId, @RequestParam String key, @RequestParam String secret) {
        lmsService.updateCredentials(lmsId, key, secret);
        log.debug("Updated LMS's credentials, lmsId = {}, new key = {}, new secret = {}", lmsId, key, secret);
    }

    @DeleteMapping("/org-admin/lms/{lmsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long lmsId) {
        lmsService.delete(lmsId);
        log.debug("Deleted LMS, lmsId = {}", lmsId);
    }

    //-----------------------------------------------Staff drop-down----------------------------------------------------
    @GetMapping(value = "/department/lms-dropdown/all-lms-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<LMSMinOutDto> findAllForDropdownByOrganisation() {
        return lmsService.findAllForDropdownByOrganisation();
    }

    //-----------------------------------------------Org admin table----------------------------------------------------
    @GetMapping(value = "/org-admin/lms-table/all-lms-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<LMSOutDto> findAllForTableByOrganisation() {
        return lmsService.findAllForTableByOrganisation();
    }

    @GetMapping(value = "/global-admin/lms-table/all-lms-by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<LMSOutDto> findAllForTableByOrganisationId(@RequestParam final Long orgId) {
        return lmsService.findAllForTableByOrganisationId(orgId);
    }

}
