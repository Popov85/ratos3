package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.security.RatosUser;
import ua.edu.ratos.service.ResourceService;
import ua.edu.ratos.service.dto.in.ResourceInDto;
import ua.edu.ratos.service.dto.out.ResourceOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(value = "/resource", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ResourceInDto dto) {
        final Long resId = resourceService.save(dto);
        log.debug("Saved Resource :: {} ", resId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(resId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/resource/{resId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long resId, @Valid @RequestBody ResourceInDto dto) {
        resourceService.update(resId, dto);
        log.debug("Updated Resource ID :: {} ", resId);
    }

    @DeleteMapping("/resource/{resId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long resId) {
        resourceService.deleteById(resId);
        log.debug("Deleted Resource ID :: {}", resId);
    }

    /*----------------------SELECT-------------------------*/

    @GetMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<ResourceOutDto> result = resourceService.findAll(PageRequest.of(page, size));
        log.debug("Found Resources page = {}, size = {} ", page, size);
        return result;
    }

    @GetMapping(value = "/resources/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResourceOutDto> findAllByStaffId(Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        List<ResourceOutDto> result = resourceService.findByStaffId(staffId);
        log.debug("Found Resources by staff ID= {}, size = {} ", staffId, result.size());
        return result;
    }

    @GetMapping(value = "/resources/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResourceOutDto> findAllByStaffIdAndLetters(@RequestParam String starts,  Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        List<ResourceOutDto> result = resourceService.findByStaffIdAndFirstLetters(staffId, starts);
        log.debug("Found Resources by staff ID= {} and first letters in description, size = {}, letters= {} ", staffId, result.size(), starts);
        return result;
    }

    @GetMapping(value = "/resources/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResourceOutDto> findAllByDepartmentId(Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        List<ResourceOutDto> result = resourceService.findByDepartmentId(depId);
        log.debug("Found Resources by department ID= {}, size = {} ", depId, result.size());
        return result;
    }

    @GetMapping(value = "/resources/by-department", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResourceOutDto> findAllByDepartmentIdAndLetters(@RequestParam String starts,  Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        List<ResourceOutDto> result = resourceService.findByDepartmentIdAndFirstLetters(depId, starts);
        log.debug("Found Resources by department ID= {} and first letters in description, size = {}, letters = {} ", depId, result.size(), starts);
        return result;
    }
}
