package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.service.ThemeViewService;
import ua.edu.ratos.service.dto.out.view.ThemeViewOutDto;
import java.util.Set;

/**
 * This controller supports creating a new Scheme,
 * by providing collection of themes with statistics
 * on how many questions and what questions types it contains in total.
 * This extended info is available at the time a user hovers mouse at dropdown list on a given theme.
 */
@RestController
@RequestMapping("/instructor")
public class ThemeViewController {

    private ThemeViewService themeViewService;

    @Autowired
    public void setThemeViewService(ThemeViewService themeViewService) {
        this.themeViewService = themeViewService;
    }

    // ------------------------------------------For Instructor-------------------------------------
    // ----------------------------------------Statistics on theme----------------------------------
    // Like: {1, Theme #1, [MCQ {10;0;0}, FBSQ {5;5;5}, FBMQ{10;10;5}, MQ {20;0;0}, SQ{15;0;0}], 85}

    @TrackTime
    @GetMapping(value = "/themes-view/{themeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThemeViewOutDto> findOneByThemeId(@PathVariable Long themeId) {
        return ResponseEntity.ok(themeViewService.findOneByThemeId(themeId));
    }

    // ----------------------------------------Scheme creating----------------------------------------
    // 1. Do not know course (disabled);
    // 2. Drop-down for themes of the department, search

    @TrackTime
    @GetMapping(value = "/themes-view/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeViewOutDto> findAllByDepartmentId() {
        return themeViewService.findAllByDepartmentId();
    }

    @TrackTime
    @GetMapping(value = "/themes-view/by-department", params = {"contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeViewOutDto> findAllByDepartmentIdAndThemeLettersContains(@RequestParam String contains) {
        return themeViewService.findAllByDepartmentIdAndThemeLettersContains(contains);
    }

    //--------------------------------------------RECOMMENDED-----------------------------------------
    // 1. Drop-down for course, search
    // 2. Drop-down for themes, search

    @TrackTime
    @GetMapping(value = "/themes-view/by-department-and-course", params = "courseId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeViewOutDto> findAllByDepartmentIdAndCourseId(@RequestParam Long courseId) {
        return themeViewService.findAllByDepartmentIdAndCourseId(courseId);
    }

    @TrackTime
    @GetMapping(value = "/themes-view/by-department-and-course", params = {"courseId", "contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ThemeViewOutDto> findAllByDepartmentIdIdAndCourseIdAndThemeLettersContains(@RequestParam Long courseId, @RequestParam String contains) {
        return themeViewService.findAllByDepartmentIdAndCourseIdAndThemeLettersContains(courseId, contains);
    }

}
