package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.FourPointGradingService;
import ua.edu.ratos.service.dto.in.FourPointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.FourPointGradingOutDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class FourPointGradingController {

    private final FourPointGradingService fourPointGradingService;

    @PostMapping(value = "/four-point-gradings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody FourPointGradingInDto dto) {
        final Long fourId = fourPointGradingService.save(dto);
        log.debug("Saved FourPointGrading, fourId = {}", fourId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fourId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/four-point-gradings/{fourId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FourPointGradingOutDto> findOne(@PathVariable Long fourId) {
        FourPointGradingOutDto dto = fourPointGradingService.findOneForEdit(fourId);
        log.debug("Retrieved FourPointGrading = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include fourId to DTO object
    @PutMapping(value = "/four-point-gradings/{fourId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long fourId, @Valid @RequestBody FourPointGradingInDto dto) {
        fourPointGradingService.update(dto);
        log.debug("Updated FourPointGrading, fourId = {}", fourId);
    }

    @DeleteMapping("/four-point-gradings/{fourId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fourId) {
        fourPointGradingService.deleteById(fourId);
        log.info("Deleted FourPointGrading, fourId = {}", fourId);
    }

    //-----------------------------------------------------Default------------------------------------------------------
    @GetMapping(value = "/four-point-gradings/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<FourPointGradingOutDto> findAllDefault() {
        return fourPointGradingService.findAllDefault();
    }

    //-------------------------------------------------Staff table------------------------------------------------------
    @GetMapping(value = "/four-point-gradings/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FourPointGradingOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return fourPointGradingService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/four-point-gradings/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FourPointGradingOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return fourPointGradingService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/four-point-gradings/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FourPointGradingOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return fourPointGradingService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/four-point-gradings/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FourPointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return fourPointGradingService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
