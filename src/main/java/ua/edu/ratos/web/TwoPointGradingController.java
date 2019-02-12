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
import ua.edu.ratos.service.TwoPointGradingService;
import ua.edu.ratos.service.dto.in.TwoPointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class TwoPointGradingController {

    private TwoPointGradingService twoPointGradingService;

    @Autowired
    public void setTwoPointGradingService(TwoPointGradingService twoPointGradingService) {
        this.twoPointGradingService = twoPointGradingService;
    }

    @PostMapping(value = "/two-point-gradings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody TwoPointGradingInDto dto) {
        final Long twoId = twoPointGradingService.save(dto);
        log.debug("Saved TwoPointGrading, twoId = {}", twoId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(twoId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/two-point-gradings/{twoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TwoPointGradingOutDto> findOne(@PathVariable Long twoId) {
        TwoPointGradingOutDto dto = twoPointGradingService.findOneForEdit(twoId);
        log.debug("Retrieved TwoPointGrading = {}", dto);
        return ResponseEntity.ok(dto);
    }

    // Make sure to include twoId to DTO object
    @PutMapping(value = "/two-point-gradings/{twoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long twoId, @Valid @RequestBody TwoPointGradingInDto dto) {
        twoPointGradingService.update(dto);
        log.debug("Updated TwoPointGrading, twoId = {}", twoId);
    }

    @DeleteMapping("/two-point-gradings/{twoId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long twoId) {
        twoPointGradingService.deleteById(twoId);
        log.info("Deleted TwoPointGrading, twoId = {}", twoId);
    }

    //-----------------------------------------------------Staff table--------------------------------------------------

    @GetMapping(value = "/two-point-gradings/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<TwoPointGradingOutDto> findAllByStaffId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return twoPointGradingService.findAllByStaffId(pageable);
    }

    @GetMapping(value = "/two-point-gradings/by-staff", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<TwoPointGradingOutDto> findAllByStaffIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return twoPointGradingService.findAllByStaffIdAndNameLettersContains(letters, pageable);
    }

    @GetMapping(value = "/two-point-gradings/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<TwoPointGradingOutDto> findAllByDepartmentId(@PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return twoPointGradingService.findAllByDepartmentId(pageable);
    }

    @GetMapping(value = "/two-point-gradings/by-department", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<TwoPointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@RequestParam String letters, @PageableDefault(sort = {"name"}, value = 50) Pageable pageable) {
        return twoPointGradingService.findAllByDepartmentIdAndNameLettersContains(letters, pageable);
    }
}
