package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.SchemeThemeSettingsService;
import ua.edu.ratos.service.dto.entity.SchemeThemeSettingsInDto;

@Slf4j
@RestController
@RequestMapping(path = "/scheme-theme-settings")
public class SchemeThemeSettingsController {

    @Autowired
    private SchemeThemeSettingsService schemeThemeSettingsService;

    @PostMapping("/")
    public Long save(@Validated({SchemeThemeSettingsInDto.New.class}) @RequestBody SchemeThemeSettingsInDto dto) {
        log.debug("Settings dto :: {} ", dto);
      /*  final Long generatedId = schemeThemeSettingsService.save(dto);
        log.debug("Saved settings ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/")
    public Long update(@Validated({SchemeThemeSettingsInDto.Update.class}) @RequestBody SchemeThemeSettingsInDto dto) {
        log.debug("Settings dto :: {} ", dto);
      /* schemeThemeSettingsService.update(dto);
        log.debug("Updated settings ID = {} ", dto.getSetId());*/
        return 1L;
    }

    @DeleteMapping("/{setId}")
    public void delete(@PathVariable Long setId) {
        log.debug("Settings to delete ID :: {}", setId);
    }
}
