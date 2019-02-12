package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.LMSSelfRegistrationService;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;

import javax.validation.Valid;
import java.net.URI;

/**
 * Use this controller when launch request was successful, but the user was not recognised
 * (EITHER no email included or the included email is not found in the RATOS DB).
 * Hence, we provided the user a registration form.
 * The user has ROLE_LTI and some lmsId included.
 */
@Slf4j
@RestController
@RequestMapping("/sign-up")
public class LMSSelfRegistrationController {

    private LMSSelfRegistrationService lmsSelfRegistrationService;

    @Autowired
    public void setLmsSelfRegistrationService(LMSSelfRegistrationService lmsSelfRegistrationService) {
        this.lmsSelfRegistrationService = lmsSelfRegistrationService;
    }

    @PostMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody StudentInDto dto) {
        Long studId = lmsSelfRegistrationService.save(dto);
        log.debug("Saved Student from within LMS, studId = {}", studId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(studId).toUri();
        return ResponseEntity.created(location).build();
    }

    //------------------------------------------------DROPDOWN for registration form------------------------------------

    @GetMapping(value="/faculties-of-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FacultyMinOutDto> findAllFacultiesByOrgId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return lmsSelfRegistrationService.findAllFacultiesByOrgId(pageable);
    }

    @GetMapping(value="/classes-of-faculty", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<ClassMinOutDto> findAllClassesByFacId(@RequestParam Long facId, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return lmsSelfRegistrationService.findAllClassesByFacId(facId, pageable);
    }

}
