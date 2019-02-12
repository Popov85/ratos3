package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentSelfOutDto;

@Component
public class ResultOfStudentSelfDtoTransformer {

    private DepartmentMinDtoTransformer departmentMinDtoTransformer;

    private SchemeMinDtoTransformer schemeMinDtoTransformer;

    private CourseMinDtoTransformer courseMinDtoTransformer;

    @Autowired
    public void setDepartmentMinDtoTransformer(DepartmentMinDtoTransformer departmentMinDtoTransformer) {
        this.departmentMinDtoTransformer = departmentMinDtoTransformer;
    }

    @Autowired
    public void setSchemeMinDtoTransformer(SchemeMinDtoTransformer schemeMinDtoTransformer) {
        this.schemeMinDtoTransformer = schemeMinDtoTransformer;
    }

    @Autowired
    public void setCourseMinDtoTransformer(CourseMinDtoTransformer courseMinDtoTransformer) {
        this.courseMinDtoTransformer = courseMinDtoTransformer;
    }

    public ResultOfStudentSelfOutDto toDto(@NonNull final ResultOfStudent entity) {
        return new ResultOfStudentSelfOutDto()
                .setResultId(entity.getResultId())
                .setDepartment(departmentMinDtoTransformer.toDto(entity.getDepartment()))
                .setCourse(courseMinDtoTransformer.toDto(entity.getScheme().getCourse()))
                .setScheme(schemeMinDtoTransformer.toDto(entity.getScheme()))
                .setGrade(entity.getGrade())
                .setPassed(entity.isPassed())
                .setPercent(entity.getPercent())
                .setSessionEnded(entity.getSessionEnded())
                .setSessionLasted(entity.getSessionLasted())
                .setTimeOuted(entity.isTimeOuted())
                .setLMS(entity.getLms().isPresent());
    }
}
