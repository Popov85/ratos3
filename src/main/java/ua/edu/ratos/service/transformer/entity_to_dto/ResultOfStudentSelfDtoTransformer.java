package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentSelfOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.transformer.DepartmentMinMapper;
import ua.edu.ratos.service.transformer.SchemeWithCourseMinMapper;

@Deprecated
@Component
@AllArgsConstructor
public class ResultOfStudentSelfDtoTransformer {

    private final DepartmentMinMapper departmentMinMapper;

    private final SchemeWithCourseMinMapper schemeWithCourseMinMapper;

    private final GameService gameService;

    public ResultOfStudentSelfOutDto toDto(@NonNull final ResultOfStudent entity) {
        return new ResultOfStudentSelfOutDto()
                .setResultId(entity.getResultId())
                .setDepartment(departmentMinMapper.toDto(entity.getDepartment()))
                .setScheme(schemeWithCourseMinMapper.toDto(entity.getScheme()))
                .setGrade(entity.getGrade())
                .setPassed(entity.isPassed())
                .setPercent(entity.getPercent())
                .setSessionEnded(entity.getSessionEnded())
                .setSessionLasted(entity.getSessionLasted())
                .setTimeOuted(entity.isTimeOuted())
                .setCancelled(entity.isCancelled())
                .setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()): null)
                .setLMS(entity.getLms().isPresent());
    }
}
