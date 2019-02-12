package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForAdminOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;

@Component
public class ResultOfStudentForAdminDtoTransformer {

    private SchemeMinDtoTransformer schemeMinDtoTransformer;

    private StudMinDtoTransformer studMinDtoTransformer;

    private CourseMinDtoTransformer courseMinDtoTransformer;

    private DepartmentDtoTransformer departmentDtoTransformer;

    @Autowired
    public void setSchemeMinDtoTransformer(SchemeMinDtoTransformer schemeMinDtoTransformer) {
        this.schemeMinDtoTransformer = schemeMinDtoTransformer;
    }

    @Autowired
    public void setStudMinDtoTransformer(StudMinDtoTransformer studMinDtoTransformer) {
        this.studMinDtoTransformer = studMinDtoTransformer;
    }

    @Autowired
    public void setCourseMinDtoTransformer(CourseMinDtoTransformer courseMinDtoTransformer) {
        this.courseMinDtoTransformer = courseMinDtoTransformer;
    }

    @Autowired
    public void setDepartmentDtoTransformer(DepartmentDtoTransformer departmentDtoTransformer) {
        this.departmentDtoTransformer = departmentDtoTransformer;
    }

    public ResultOfStudentForAdminOutDto toDto(@NonNull final ResultOfStudent entity) {
        ResultOfStudentForStaffOutDto result = new ResultOfStudentForStaffOutDto()
                .setResultId(entity.getResultId())
                .setCourse(courseMinDtoTransformer.toDto(entity.getScheme().getCourse()))
                .setScheme(schemeMinDtoTransformer.toDto(entity.getScheme()))
                .setStudent(studMinDtoTransformer.toDto(entity.getStudent()))
                .setGrade(entity.getGrade())
                .setPassed(entity.isPassed())
                .setPercent(entity.getPercent())
                .setSessionEnded(entity.getSessionEnded())
                .setSessionLasted(entity.getSessionLasted())
                .setTimeOuted(entity.isTimeOuted())
                .setLMS(entity.getLms().isPresent());

        return new ResultOfStudentForAdminOutDto()
                .setResult(result)
                .setDepartment(departmentDtoTransformer.toDto(entity.getScheme().getDepartment()));
    }
}
