package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.LMSCourseService;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;
import ua.edu.ratos.service.dto.out.LMSCourseOutDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class LMSCourseController {

    private LMSCourseService lmsCourseService;

    @Autowired
    public void setLmsCourseService(LMSCourseService lmsCourseService) {
        this.lmsCourseService = lmsCourseService;
    }

    @PostMapping(value = "/lms-courses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody LMSCourseInDto dto) {
        final Long courseId = lmsCourseService.save(dto);
        log.debug("Saved LMSCourse, courseId = {}", courseId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/lms-courses/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LMSCourseOutDto> findOne(@PathVariable Long courseId) {
        LMSCourseOutDto dto = lmsCourseService.findByIdForUpdate(courseId);
        log.debug("Retrieved LMSCourse = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/lms-courses/{courseId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long courseId, @RequestParam String name) {
        lmsCourseService.updateName(courseId, name);
        log.debug("Updated LMSCourse's name, courseId = {}, new name = {}", courseId, name);
    }

    @PutMapping(value = "/lms-courses/{courseId}/access")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAccess(@PathVariable Long courseId, @RequestParam Long accessId) {
        lmsCourseService.updateAccess(courseId, accessId);
        log.debug("Updated LMSCourse's access, courseId = {}, new accessId = {}", courseId, accessId);
    }

    @DeleteMapping("/lms-courses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long courseId) {
        lmsCourseService.deleteById(courseId);
        log.debug("Deleted Course, courseId = {}", courseId);
    }


    //--------------------------------------------------Staff table-----------------------------------------------------

    @GetMapping(value = "/lms-courses/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LMSCourseOutDto> findAllByStaffId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return lmsCourseService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/lms-courses/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LMSCourseOutDto> findAllByStaffIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return lmsCourseService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/lms-courses/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LMSCourseOutDto> findAllByDepartmentId(@PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return lmsCourseService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/lms-courses/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LMSCourseOutDto> findAllByDepartmentIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"created"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return lmsCourseService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }

    //-----------------------------------------------Staff drop-down----------------------------------------------------

    @GetMapping(value = "/lms-courses/by-staff-dropdown", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<LMSCourseOutDto> findAllForDropDownByStaffId(@PageableDefault(sort = {"name"}, value = 100) Pageable pageable) {
        return lmsCourseService.findAllForDropDownByStaffId(pageable);
    }

    @GetMapping(value = "/lms-courses/by-department-dropdown", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<LMSCourseOutDto> findAllForDropDownByDepartmentId(@PageableDefault(sort = {"name"}, value = 100) Pageable pageable) {
        return lmsCourseService.findAllForDropDownByDepartmentId(pageable);
    }

}
