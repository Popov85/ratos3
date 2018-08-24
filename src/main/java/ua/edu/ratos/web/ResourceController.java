package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.ResourceService;
import ua.edu.ratos.service.dto.entity.ResourceInDto;

@Slf4j
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/resource")
    public Long save(@Validated({ResourceInDto.New.class}) @RequestBody ResourceInDto dto) {
        log.debug("Resource dto :: {} ", dto);
      /*  final Long generatedId = resourceService.save(dto);
        log.debug("Saved resource ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/resource")
    public Long update(@Validated({ResourceInDto.Update.class}) @RequestBody ResourceInDto dto) {
        log.debug("Resource dto :: {} ", dto);
      /* resourceService.update(dto);
        log.debug("Updated resource ID = {} ", dto.getResourceId());*/
        return 1L;
    }

    @DeleteMapping("/resource/{resourceId}")
    public void delete(@PathVariable Long helpId) {
        log.debug("Resource to delete ID :: {}", helpId);
    }
}
