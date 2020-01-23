package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.*;
import ua.edu.ratos.service.dto.out.*;

@Slf4j
@RestController
@RequestMapping("/global-admin")
@AllArgsConstructor
public class AdminController {

    private final SchemeService schemeService;
    private final ThemeService themeService;
    private final CourseService courseService;
    private final ResourceService resourceService;
    private final PhraseService phraseService;
    private final HelpService helpService;
    private final ModeService modeService;
    private final SettingsService settingsService;
    private final SettingsFBService settingsFBService;
    private final LMSService lmsService;
    private final StaffService staffService;
    private final StudentService studentService;

    @GetMapping(value = "/schemes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SchemeShortOutDto> findAllSchemes(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return schemeService.findAll(pageable);
    }

    @GetMapping(value = "/themes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ThemeOutDto> findAllThemes(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return themeService.findAll(pageable);
    }

    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CourseOutDto> findAllCourses(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return courseService.findAll(pageable);
    }

    @GetMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAllResources(@PageableDefault(sort = {"description"}, value = 50) Pageable pageable) {
       return resourceService.findAll(pageable);
    }

    @GetMapping(value = "/phrases", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PhraseOutDto> findAllPhrases(@PageableDefault(sort = {"phrase"}, value = 50) Pageable pageable) {
        return phraseService.findAll(pageable);
    }

    @GetMapping(value = "/helps", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HelpOutDto> findAllHelps(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return helpService.findAll(pageable);
    }

    @GetMapping(value = "/modes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ModeOutDto> findAllModes(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return modeService.findAll(pageable);
    }

    @GetMapping(value="/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SettingsOutDto> findAllSettings(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsService.findAll(pageable);
    }
    @GetMapping(value="/settings-fb", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SettingsFBOutDto> findAllSettingsFB(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return settingsFBService.findAllAdmin(pageable);
    }

    @GetMapping(value = "/lms", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LMSOutDto> findAllLMS(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return lmsService.findAllAdmin(pageable);
    }

    @GetMapping(value = "/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StaffOutDto> findAllStaff(@PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return staffService.findAllAdmin(pageable);
    }

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudOutDto> findAllStudents(@PageableDefault(sort = {"surname"}, value = 50) Pageable pageable) {
        return studentService.findAllAdmin(pageable);
    }
}
