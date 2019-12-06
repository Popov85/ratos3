package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.repository.specs.SpecsFilter;
import ua.edu.ratos.service.ResultOfStudentsService;
import ua.edu.ratos.service.dto.out.criteria.*;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class ResultOfStudentController {

    private final ResultOfStudentsService resultOfStudentsService;

    //-----------------------------------------------------Student------------------------------------------------------
    @TrackTime
    @GetMapping(value = "/student/self-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentSelfOutDto> findAllByStudentId(@PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByStudentId(pageable);
    }

    @TrackTime
    @PostMapping(value = "/student/self-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentSelfOutDto> findAllByStudentIdAndSpec(@RequestBody  Map<String, SpecsFilter> specs, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByStudentIdAndSpecs(specs, pageable);
    }

    //-------------------------------------------------------Staff------------------------------------------------------
    @TrackTime
    @GetMapping(value = "/department/student-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentId(@PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentId(pageable);
    }

    @TrackTime
    @PostMapping(value = "/department/student-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentIdAndSpec(@RequestBody  Map<String, SpecsFilter> specs, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentIdAndSpecs(specs, pageable);
    }

    // -----------------------------------------------------Fac-admin(+)------------------------------------------------

    @TrackTime
    @GetMapping(value = "/fac-admin/student-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentId(@RequestParam final Long depId, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentIdAdmin(depId, pageable);
    }

    @TrackTime
    @PostMapping(value = "/fac-admin/student-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentIdAndSpec(@RequestParam final Long depId, @RequestBody  Map<String, SpecsFilter> specs, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 20) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentIdAndSpecsAdmin(depId, specs, pageable);
    }
}
