package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.service.ResultOfStudentsService;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaAdminInDto;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaSelfInDto;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaStaffInDto;
import ua.edu.ratos.service.dto.out.ResultSearchSuiteSelfOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForAdminOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentSelfOutDto;

@RestController
@AllArgsConstructor
public class ResultOfStudentController {

    private final ResultOfStudentsService resultOfStudentsService;

    //----------------------------------------------------DEP staff-----------------------------------------------------
    @TrackTime
    @GetMapping(value = "/department/student-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentId(@PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentId(pageable);
    }

    @TrackTime
    @PostMapping(value = "/department/student-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentIdAndSpec(@RequestBody ResultOfStudentCriteriaStaffInDto dto, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentIdAndSpecs(dto, pageable);
    }

    // -----------------------------------------------------ADMIN-------------------------------------------------------
    // for any higher fac-admin+
    @TrackTime
    @PostMapping(value = "/fac-admin/student-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentForAdminOutDto> findAllByDepartmentIdAndSpec(@RequestBody ResultOfStudentCriteriaAdminInDto dto, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resultOfStudentsService.findAllByDepartmentIdAndSpecs(dto, pageable);
    }

    //-----------------------------------------------------Student------------------------------------------------------
    @TrackTime
    @GetMapping(value = "/student/student-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentSelfOutDto> findAllByStudentId(@PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resultOfStudentsService.findAllByStudentId(pageable);
    }

    @TrackTime
    @PostMapping(value = "/student/self-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ResultOfStudentSelfOutDto> findAllByDepartmentIdAndSpec(@RequestBody ResultOfStudentCriteriaSelfInDto dto, @PageableDefault(sort = {"sessionEnded"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return resultOfStudentsService.findAllByStudentIdAndSpecs(dto, pageable);
    }

    //--------------------------------------------------Student search--------------------------------------------------
    @TrackTime
    @PostMapping(value = "/student/search-params", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultSearchSuiteSelfOutDto findAllSearchParamsFromStudentResults() {
        return resultOfStudentsService.findAllSearchParamsFromStudentsResult();
    }

}
