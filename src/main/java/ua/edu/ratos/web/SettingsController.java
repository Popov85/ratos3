package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.domain.entity.Settings;
import ua.edu.ratos.service.SettingsService;
import ua.edu.ratos.service.dto.entity.SettingsInDto;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping("/")
    public Long save(@Validated({SettingsInDto.New.class}) @RequestBody SettingsInDto dto) {
        final Long generatedId = settingsService.save(dto);
        log.debug("Saved Settings ID = {} ", generatedId);
        return generatedId;
    }

    @PutMapping("/")
    public void update(@Validated({SettingsInDto.New.class}) @RequestBody SettingsInDto dto) {
        settingsService.update(dto);
        log.debug("Updated Settings ID = {} ", dto.getSetId());
    }

    @GetMapping("/all")
    public List<Settings> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return settingsService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all/staff")
    public Set<Settings> getAllByStaff() {
        return settingsService.findAllByStaffId(1L);
    }

    @GetMapping("/all/department")
    public List<Settings> getAllByDepartment() {
        return settingsService.findAllByDepartmentId(1L);
    }

    @GetMapping("/all/department/search")
    public Set<Settings> getAllByDepartmentAndName(String contains) {
        return settingsService.findAllByDepartmentIdAndSettingsNameLettersContains(1L, contains);
    }

    @GetMapping("/all/staff/search")
    public Set<Settings> getAllByStaffAndName(String contains) {
        return settingsService.findAllByStaffIdAndSettingsNameLettersContains(1L, contains);
    }


    @DeleteMapping("/{setId}")
    public void delete(@PathVariable Long setId) {
        settingsService.deleteById(setId);
        log.info("Settings to delete ID :: {}", setId);
    }

}
