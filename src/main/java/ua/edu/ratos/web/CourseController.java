package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.CourseService;
import ua.edu.ratos.service.dto.in.CourseInDto;
import ua.edu.ratos.service.dto.out.CourseOutDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody CourseInDto dto) {
        final Long courseId = courseService.save(dto);
        log.debug("Saved Course, courseId = {}", courseId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(courseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/courses/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseOutDto> findOne(@PathVariable Long courseId) {
        CourseOutDto dto = courseService.findByIdForUpdate(courseId);
        log.debug("Retrieved Course = {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/courses/{courseId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long courseId, @RequestParam String name) {
        courseService.updateName(courseId, name);
        log.debug("Updated Course's name, courseId = {}, new name = {}", courseId, name);
    }

    @PutMapping(value = "/courses/{courseId}/access")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAccess(@PathVariable Long courseId, @RequestParam Long accessId) {
        courseService.updateAccess(courseId, accessId);
        log.debug("Updated Course's access, courseId = {}, new accessId = {}", courseId, accessId);
    }

    @DeleteMapping("/courses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long courseId) {
        courseService.deleteById(courseId);
        log.debug("Deleted Course, courseId = {}", courseId);
    }


    //---------------------------GET for TABLES---------------------------

    @GetMapping(value = "/courses/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CourseOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return courseService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/courses/by-staff", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CourseOutDto> findAllByStaffIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return courseService.findAllByStaffIdAndNameContains(letters, pageable);
    }

    @GetMapping(value = "/courses/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CourseOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return courseService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/courses/by-department", params = {"letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CourseOutDto> findAllByDepartmentIdAndNameContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 30) Pageable pageable) {
        return courseService.findAllByDepartmentIdAndNameContains(letters, pageable);
    }

}
