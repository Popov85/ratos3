package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.StudentService;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.StudOutDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/lab/students", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody StudentInDto dto) {
        final Long studentId = studentService.save(dto);
        log.debug("Saved Student, studentId = {}", studentId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(studentId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/lab/students/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudOutDto> findOne(@PathVariable Long studentId) {
        StudOutDto dto = studentService.findOneForEdit(studentId);
        log.debug("Retrieved Student = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/fac-admin/students/{studentId}/inactive")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long studentId) {
        studentService.deactivate(studentId);
        log.debug("Deactivated Student, studentId = {}", studentId);
    }

    //---------------------------------------------Staff group management table-----------------------------------------

    @GetMapping(value = "/lab/students/by-organisation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudOutDto> findAllByDepartmentId(@PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return studentService.findAllByOrganisationId(pageable);
    }

    @GetMapping(value = "/lab/students/by-organisation", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<StudOutDto> findAllByOrganisationIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return studentService.findAllByOrganisationIdAndNameLettersContains(letters, pageable);
    }
}
