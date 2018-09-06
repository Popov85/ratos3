package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.entity.SchemeInDto;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/scheme")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;

    @PostMapping("/")
    public Long save(@Validated({SchemeInDto.New.class}) @RequestBody SchemeInDto dto) {
        log.debug("Scheme dto :: {} ", dto);
      /*  final Long generatedId = schemeService.save(dto);
        log.debug("Saved scheme ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/")
    public Long update(@Validated({SchemeInDto.Update.class}) @RequestBody SchemeInDto dto) {
        log.debug("Scheme dto :: {} ", dto);
      /* schemeService.update(dto);
        log.debug("Updated scheme ID = {} ", dto.getSchemeId());*/
        return 1L;
    }

    @PutMapping("/{schemeId}")
    public void reOrder(@PathVariable Long schemeId, @RequestBody List<Long> schemeThemeIds) {
        schemeService.reOrder(schemeId, schemeThemeIds);
        log.debug("Themes were reordered :: {}", schemeThemeIds);
    }

    @DeleteMapping("/{schemeId}")
    public void delete(@PathVariable Long schemeId) {
        log.debug("Scheme to delete ID :: {}", schemeId);
    }

    @DeleteMapping("/{schemeId}/{themeIndex}")
    public void deleteTheme(@PathVariable Long schemeId, @PathVariable Integer themeIndex) {
        schemeService.deleteByIndex(schemeId, themeIndex);
        log.debug("Theme's index to delete :: {}", themeIndex);
    }


}
