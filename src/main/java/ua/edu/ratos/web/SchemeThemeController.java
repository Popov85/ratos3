package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.SchemeThemeService;
import ua.edu.ratos.service.dto.entity.SchemeThemeInDto;

@Slf4j
@RestController
@RequestMapping(path = "/scheme-theme")
public class SchemeThemeController {

    @Autowired
    private SchemeThemeService schemeThemeService;

    @PostMapping("/")
    public Long save(@Validated({SchemeThemeInDto.class}) @RequestBody SchemeThemeInDto dto) {
        log.debug("SchemeTheme dto :: {} ", dto);
      /*  final Long generatedId = schemeThemeService.save(dto);
        log.debug("Saved schemeTheme ID = {} ", generatedId);*/
        return 1L;
    }
}
