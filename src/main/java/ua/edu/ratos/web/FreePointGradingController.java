package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.FreePointGradingService;
import ua.edu.ratos.service.dto.in.FreePointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class FreePointGradingController {

    private FreePointGradingService freePointGradingService;

    @Autowired
    public void setFreePointGradingService(FreePointGradingService freePointGradingService) {
        this.freePointGradingService = freePointGradingService;
    }

    @PostMapping(value = "/free-point-gradings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody FreePointGradingInDto dto) {
        final Long freeId = freePointGradingService.save(dto);
        log.debug("Saved FreePointGrading, freeId = {}", freeId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(freeId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/free-point-gradings/{freeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreePointGradingOutDto> findOne(@PathVariable Long freeId) {
        FreePointGradingOutDto dto = freePointGradingService.findOneForEdit(freeId);
        log.debug("Retrieved FreePointGrading = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include freeId to DTO object
    @PutMapping(value = "/free-point-gradings/{freeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long freeId, @Valid @RequestBody FreePointGradingInDto dto) {
        freePointGradingService.update(dto);
        log.debug("Updated FreePointGrading, freeId = {}", freeId);
    }

    @DeleteMapping("/free-point-gradings/{freeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long freeId) {
        freePointGradingService.deleteById(freeId);
        log.info("Deleted FreePointGrading, freeId = {}", freeId);
    }

    //------------------------------------------------Staff table-------------------------------------------------------

    @GetMapping(value = "/free-point-gradings/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FreePointGradingOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return freePointGradingService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/free-point-gradings/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FreePointGradingOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return freePointGradingService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/free-point-gradings/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FreePointGradingOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return freePointGradingService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/free-point-gradings/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FreePointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return freePointGradingService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
