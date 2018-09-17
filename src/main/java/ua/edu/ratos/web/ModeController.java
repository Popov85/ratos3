package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.domain.entity.Mode;
import ua.edu.ratos.service.ModeService;
import ua.edu.ratos.service.dto.entity.ModeInDto;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/mode")
public class ModeController {

    @Autowired
    private ModeService modeService;

    @PostMapping("/")
    public Long save(@Validated({ModeInDto.New.class}) @RequestBody ModeInDto modeDto) {
        final Long generatedId = modeService.save(modeDto);
        log.debug("Saved Mode ID = {} ", generatedId);
        return generatedId;
    }

    @PutMapping("/")
    public void update(@Validated({ModeInDto.New.class}) @RequestBody ModeInDto modeDto) {
        modeService.update(modeDto);
        log.debug("Updated mode ID = {} ", modeDto.getModeId());
    }

    @GetMapping("/all")
    public List<Mode> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return modeService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all/staff")
    public List<Mode> getAllByStaff() {
        return modeService.findAllByStaffId(1L);
    }

    @GetMapping("/all/department")
    public List<Mode> getAllByDepartment() {
        return modeService.findAllByDepartmentId(1L);
    }

    @GetMapping("/all/department/search")
    public Set<Mode> getAllByDepartmentAndName(String contains) {
        return modeService.findAllByDepartmentIdAndModeNameLettersContains(1L, contains);
    }

    @GetMapping("/all/staff/search")
    public Set<Mode> getAllByStaffAndName(String contains) {
        return modeService.findAllByStaffIdAndModeNameLettersContains(1L, contains);
    }


    @DeleteMapping("/{modeId}")
    public void delete(@PathVariable Long modeId) {
        modeService.deleteById(modeId);
        log.info("Mode to delete ID :: {}", modeId);
    }
}
