package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.dto.entity.ThemeInDto;


@Slf4j
@RestController
public class ThemeController {

    @Autowired
    private ThemeService themeService;

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

}
