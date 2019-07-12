package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.dao.repository.ResultOfStudentRepository;
import ua.edu.ratos.dao.repository.specs.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaAdminInDto;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaSelfInDto;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaStaffInDto;
import ua.edu.ratos.service.dto.out.ResultSearchSuiteSelfOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForAdminOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentSelfOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.*;
import java.util.List;
import static ua.edu.ratos.dao.repository.specs.ResultOfStudentSelfSpecs.byStudent;
import static ua.edu.ratos.dao.repository.specs.ResultOfStudentStaffSpecs.*;

@Service
public class ResultOfStudentsService {

    private ResultOfStudentRepository resultOfStudentRepository;

    private ResultOfStudentForStaffDtoTransformer resultOfStudentForStaffDtoTransformer;

    private ResultOfStudentForAdminDtoTransformer resultOfStudentForAdminDtoTransformer;

    private ResultOfStudentSelfDtoTransformer resultOfStudentSelfDtoTransformer;

    private SecurityUtils securityUtils;

    private DepartmentMinDtoTransformer departmentMinDtoTransformer;

    private CourseMinDtoTransformer courseMinDtoTransformer;

    private SchemeMinDtoTransformer schemeMinDtoTransformer;

    @Autowired
    public void setResultOfStudentRepository(ResultOfStudentRepository resultOfStudentRepository) {
        this.resultOfStudentRepository = resultOfStudentRepository;
    }

    @Autowired
    public void setResultOfStudentForStaffDtoTransformer(ResultOfStudentForStaffDtoTransformer resultOfStudentForStaffDtoTransformer) {
        this.resultOfStudentForStaffDtoTransformer = resultOfStudentForStaffDtoTransformer;
    }

    @Autowired
    public void setResultOfStudentForAdminDtoTransformer(ResultOfStudentForAdminDtoTransformer resultOfStudentForAdminDtoTransformer) {
        this.resultOfStudentForAdminDtoTransformer = resultOfStudentForAdminDtoTransformer;
    }

    @Autowired
    public void setResultOfStudentSelfDtoTransformer(ResultOfStudentSelfDtoTransformer resultOfStudentSelfDtoTransformer) {
        this.resultOfStudentSelfDtoTransformer = resultOfStudentSelfDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setDepartmentMinDtoTransformer(DepartmentMinDtoTransformer departmentMinDtoTransformer) {
        this.departmentMinDtoTransformer = departmentMinDtoTransformer;
    }

    @Autowired
    public void setCourseMinDtoTransformer(CourseMinDtoTransformer courseMinDtoTransformer) {
        this.courseMinDtoTransformer = courseMinDtoTransformer;
    }

    @Autowired
    public void setSchemeMinDtoTransformer(SchemeMinDtoTransformer schemeMinDtoTransformer) {
        this.schemeMinDtoTransformer = schemeMinDtoTransformer;
    }

    //----------------------------------------LAB/INSTRUCTOR by department & criteria-----------------------------------

    @Transactional(readOnly = true)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        Specification<ResultOfStudent> specs = ofDepartment(securityUtils.getAuthDepId());
        return resultOfStudentRepository.findAll(specs, pageable).map(resultOfStudentForStaffDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ResultOfStudentForStaffOutDto> findAllByDepartmentIdAndSpecs(@NonNull final ResultOfStudentCriteriaStaffInDto dto, @NonNull final Pageable pageable) {
        Specification<ResultOfStudent> specs = ofDepartment(securityUtils.getAuthDepId()).and(hasSpecs(dto));
        return resultOfStudentRepository.findAll(specs, pageable).map(resultOfStudentForStaffDtoTransformer::toDto);
    }

    //-------------------------------------------------------ADMIN------------------------------------------------------
    // any higher admin {fac/org/global}
    @Transactional(readOnly = true)
    public Page<ResultOfStudentForAdminOutDto> findAllByDepartmentIdAndSpecs(@NonNull final ResultOfStudentCriteriaAdminInDto dto, @NonNull final Pageable pageable) {
        Specification<ResultOfStudent> specs = ofDepartment(dto.getDepId()).and(hasSpecs(dto));
        return resultOfStudentRepository.findAll(specs, pageable).map(resultOfStudentForAdminDtoTransformer::toDto);
    }

    //------------------------------------------------------STUDENT-----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<ResultOfStudentSelfOutDto> findAllByStudentId(@NonNull final Pageable pageable) {
        Specification<ResultOfStudent> specs = byStudent(securityUtils.getAuthUserId());
        return resultOfStudentRepository.findAll(specs, pageable).map(resultOfStudentSelfDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ResultOfStudentSelfOutDto> findAllByStudentIdAndSpecs(@NonNull final ResultOfStudentCriteriaSelfInDto dto, @NonNull final Pageable pageable) {
        Specification<ResultOfStudent> specs = byStudent(securityUtils.getAuthUserId()).and(ResultOfStudentSelfSpecs.hasSpecs(dto));
        return resultOfStudentRepository.findAll(specs, pageable).map(resultOfStudentSelfDtoTransformer::toDto);
    }
    //-----------------------------------------------STUDENT search drop-down-------------------------------------------

    // For self-results search from personal page by {dep/course/scheme}
    @Transactional(readOnly = true)
    public ResultSearchSuiteSelfOutDto findAllSearchParamsFromStudentsResult() {
        Specification<ResultOfStudent> specs = byStudent(securityUtils.getAuthUserId());
        // Up to 10000 results/per student are expected by the end of study, 1000-3000 average
        List<ResultOfStudent> result = resultOfStudentRepository.findAll(specs);
        ResultSearchSuiteSelfOutDto dto = new ResultSearchSuiteSelfOutDto();
        for (ResultOfStudent r : result) {
            dto.addDep(departmentMinDtoTransformer.toDto(r.getDepartment()));
            dto.addCourse(courseMinDtoTransformer.toDto(r.getScheme().getCourse()));
            dto.addScheme(schemeMinDtoTransformer.toDto(r.getScheme()));
        }
        return dto;
    }
}
