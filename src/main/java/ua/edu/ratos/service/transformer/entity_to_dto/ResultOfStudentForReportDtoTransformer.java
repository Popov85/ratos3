package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForReportOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.utils.DataFormatter;

@Component
@AllArgsConstructor
public class ResultOfStudentForReportDtoTransformer {

    private final DepartmentDtoTransformer departmentDtoTransformer;

    private final SchemeWithCourseMinDtoTransformer schemeWithCourseMinDtoTransformer;

    private final StudMinDtoTransformer studMinDtoTransformer;

    private final GameService gameService;

    public ResultOfStudentForReportOutDto toDto(@NonNull final ResultOfStudent entity) {
        ResultOfStudentForReportOutDto resultOfStudentForReportOutDto = new ResultOfStudentForReportOutDto();
        resultOfStudentForReportOutDto.setResultId(entity.getResultId());
        resultOfStudentForReportOutDto.setScheme(schemeWithCourseMinDtoTransformer.toDto(entity.getScheme()));
        resultOfStudentForReportOutDto.setStudent(studMinDtoTransformer.toDto(entity.getStudent()));
        resultOfStudentForReportOutDto.setGrade(DataFormatter.getPrettyDouble(entity.getGrade()));
        resultOfStudentForReportOutDto.setPercent(DataFormatter.getPrettyDouble(entity.getPercent()));
        resultOfStudentForReportOutDto.setPassed(entity.isPassed());
        resultOfStudentForReportOutDto.setSessionEnded(entity.getSessionEnded());
        resultOfStudentForReportOutDto.setSessionLasted(entity.getSessionLasted());
        resultOfStudentForReportOutDto.setTimeouted(entity.isTimeOuted());
        resultOfStudentForReportOutDto.setCancelled(entity.isCancelled());
        resultOfStudentForReportOutDto.setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()): null);
        resultOfStudentForReportOutDto.setLMS(entity.getLms().isPresent());
        resultOfStudentForReportOutDto.setDepartment(departmentDtoTransformer.toDto(entity.getDepartment()));
        return resultOfStudentForReportOutDto;
    }
}
