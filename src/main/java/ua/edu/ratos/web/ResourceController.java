package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.ResourceService;
import ua.edu.ratos.service.dto.in.ResourceInDto;
import ua.edu.ratos.service.dto.out.ResourceOutDto;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class ResourceController {

    private ResourceService resourceService;

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(value = "/resources", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ResourceInDto dto) {
        final Long resId = resourceService.save(dto);
        log.debug("Saved Resource, resId = {}", resId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(resId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/resources/{resId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceOutDto> findAllByStaffId(@PathVariable Long resId) {
        ResourceOutDto result = resourceService.findOneById(resId);
        log.debug("Retrieved Resource = {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/resources/{resId}/link")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLink(@PathVariable Long resId, @RequestParam String link) {
        resourceService.updateLink(resId, link);
        log.debug("Updated Resource's link, resId = {}, new link = {}", resId, link);
    }

    @PutMapping("/resources/{resId}/description")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateDescription(@PathVariable Long resId, @RequestParam String description) {
        resourceService.updateDescription(resId, description);
        log.debug("Updated Resource's description, resId = {}, new description = {}", resId, description);
    }

    @DeleteMapping("/resources/{resId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long resId) {
        resourceService.deleteById(resId);
        log.debug("Deleted Resource, resId = {}", resId);
    }

    //------------------------------------SELECT for table & dropdown---------------------------------------

    @GetMapping(value = "/resources/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAllByStaffId(@PageableDefault(sort = {"lastUsed"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resourceService.findByStaffId(pageable);
    }

    @GetMapping(value = "/resources/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAllByStaffIdAndDescriptionLettersContains(@RequestParam String letters, @PageableDefault(sort = {"description"}, value = 50) Pageable pageable) {
        return resourceService.findByStaffIdAndDescriptionLettersContains(letters, pageable);
    }

    @GetMapping(value = "/resources/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAllByDepartmentId(@PageableDefault(sort = {"lastUsed"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resourceService.findByDepartmentId(pageable);
    }

    @GetMapping(value = "/resources/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResourceOutDto> findAllByDepartmentIdAndDescriptionLettersContains(@RequestParam String letters, @PageableDefault(sort = {"description"}, value = 50) Pageable pageable) {
        return resourceService.findByDepartmentIdAndDescriptionLettersContains(letters, pageable);
    }
}
