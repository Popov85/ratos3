package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.ThemeViewService;
import ua.edu.ratos.service.dto.entity.ThemeInDto;
import ua.edu.ratos.service.dto.view.ThemeOutDto;
import java.util.Set;

@Slf4j
@RestController
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeViewService themeViewService;


    @PostMapping("/theme")
    public Long save(@Validated({ThemeInDto.New.class}) @RequestBody ThemeInDto dto) {
        log.debug("Theme dto :: {} ", dto);
      /*  final Long generatedId = themeService.save(dto);
        log.debug("Saved theme ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/theme")
    public Long update(@Validated({ThemeInDto.Update.class}) @RequestBody ThemeInDto dto) {
        log.debug("Theme dto :: {} ", dto);
      /* themeService.update(dto);
        log.debug("Updated theme ID = {} ", dto.getHelpId());*/
        return 1L;
    }

    @DeleteMapping("/theme/{themeId}")
    public void delete(@PathVariable Long themeId) {
        log.debug("Theme to delete ID :: {}", themeId);
    }

    //------------------------GET----------------------

    @GetMapping("/theme/course/{courseId}")
    public Set<ThemeOutDto> findAllByCourseId(@PathVariable Long courseId) {
        final Set<ThemeOutDto> result = themeViewService.findAllByCourseId(courseId);
        return result;
    }

    @GetMapping("/theme/course2/{courseId}")
    public Set<ThemeOutDto> findAllByCourseIdAndThemeLettersContains(@PathVariable Long courseId, @RequestParam String contains) {
        final Set<ThemeOutDto> result = themeViewService.findAllByCourseIdAndThemeLettersContains(courseId, contains);
        return result;
    }


    @GetMapping("/theme/department/{depId}")
    public Set<ThemeOutDto> findAllByDepartmentId(@PathVariable Long depId) {
        final Set<ThemeOutDto> result = themeViewService.findAllByDepartmentId(depId, PageRequest.of(0, 50));
        return result;
    }

    @GetMapping("/theme/department2/{depId}")
    public Set<ThemeOutDto> findAllByDepartmentIdAndThemeLettersContains(@PathVariable Long depId, @RequestParam String contains) {
        final Set<ThemeOutDto> result = themeViewService.findAllByDepartmentIdAndThemeLettersContains(depId, contains);
        return result;
    }

    @GetMapping("/theme/organisation/{orgId}")
    public Set<ThemeOutDto> findAllByOrganisationId(@PathVariable Long orgId) {
        final Set<ThemeOutDto> result = themeViewService.findAllByOrganisationId(orgId, PageRequest.of(0, 50));
        return result;
    }

    @GetMapping("/theme/organisation2/{orgId}")
    public Set<ThemeOutDto> findAllByOrganisationIdAndThemeLettersContains(@PathVariable Long orgId, @RequestParam String contains) {
        final Set<ThemeOutDto> result = themeViewService.findAllByOrganisationIdAndThemeLettersContains(orgId, contains);
        return result;
    }

}
