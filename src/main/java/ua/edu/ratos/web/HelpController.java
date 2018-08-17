package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.entity.HelpInDto;


@Slf4j
@RestController
public class HelpController {

    @Autowired
    private HelpService helpService;

    @PostMapping("/help")
    public Long save(@Validated({HelpInDto.New.class}) @RequestBody HelpInDto dto) {
        log.debug("Help dto :: {} ", dto);
      /*  final Long generatedId = helpService.save(dto);
        log.debug("Saved help ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/help")
    public Long update(@Validated({HelpInDto.Update.class}) @RequestBody HelpInDto dto) {
        log.debug("Help dto :: {} ", dto);
      /* helpService.update(dto);
        log.debug("Updated help ID = {} ", dto.getHelpId());*/
        return 1L;
    }

    @DeleteMapping("/help/{helpId}")
    public void delete(@PathVariable Long helpId) {
        log.debug("Help to delete ID :: {}", helpId);
    }
}
