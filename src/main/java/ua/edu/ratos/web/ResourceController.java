package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.service.ResourceService;
import ua.edu.ratos.service.dto.entity.ResourceInDto;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ResourceInDto dto) {
        final Long resId = resourceService.save(dto);
        log.debug("Saved Resource :: {} ", resId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(resId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{resId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long resId, @Valid @RequestBody ResourceInDto dto) {
        resourceService.update(resId, dto);
        log.debug("Updated Resource ID :: {} ", resId);
    }

    @DeleteMapping("/{resId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long resId) {
        resourceService.deleteById(resId);
        log.debug("Deleted Resource ID :: {}", resId);
    }

    /*----------------------SELECT-------------------------*/

    @GetMapping(value = "/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> findAllByStaffId(Principal principal) {
        return resourceService.findByStaffId(1L);
    }

    @GetMapping(value = "/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> findAllByStaffIdAndLetters(@RequestParam String starts,  Principal principal) {
        return resourceService.findByStaffIdAndFirstLetters(1L, starts);
    }

    @GetMapping(value = "/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> findAllByDepartmentId(Principal principal) {
        return resourceService.findByDepartmentId(1L);
    }

    @GetMapping(value = "/by-department", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> findAllByDepartmentIdAndLetters(@RequestParam String starts,  Principal principal) {
        return resourceService.findByDepartmentIdAndFirstLetters(1L, starts);
    }
}
