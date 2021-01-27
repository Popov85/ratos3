package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForReportOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.transformer.DepartmentMapper;
import ua.edu.ratos.service.transformer.SchemeWithCourseMinMapper;
import ua.edu.ratos.service.transformer.StudMinMapper;
import ua.edu.ratos.service.utils.DataFormatter;

@Deprecated
@Component
@AllArgsConstructor
public class ResultOfStudentForReportDtoTransformer {

    private final DepartmentMapper departmentMapper;

    private final SchemeWithCourseMinMapper schemeWithCourseMinMapper;

    private final StudMinMapper studMinMapper;

    private final GameService gameService;

    public ResultOfStudentForReportOutDto toDto(@NonNull final ResultOfStudent entity) {
        ResultOfStudentForReportOutDto resultOfStudentForReportOutDto = new ResultOfStudentForReportOutDto();
        resultOfStudentForReportOutDto.setResultId(entity.getResultId());
        resultOfStudentForReportOutDto.setScheme(schemeWithCourseMinMapper.toDto(entity.getScheme()));
        resultOfStudentForReportOutDto.setStudent(studMinMapper.toDto(entity.getStudent()));
        resultOfStudentForReportOutDto.setGrade(DataFormatter.getPrettyDouble(entity.getGrade()));
        resultOfStudentForReportOutDto.setPercent(DataFormatter.getPrettyDouble(entity.getPercent()));
        resultOfStudentForReportOutDto.setPassed(entity.isPassed());
        resultOfStudentForReportOutDto.setSessionEnded(entity.getSessionEnded());
        resultOfStudentForReportOutDto.setSessionLasted(entity.getSessionLasted());
        resultOfStudentForReportOutDto.setTimeouted(entity.isTimeOuted());
        resultOfStudentForReportOutDto.setCancelled(entity.isCancelled());
        resultOfStudentForReportOutDto.setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()): null);
        resultOfStudentForReportOutDto.setLMS(entity.getLms().isPresent());
        resultOfStudentForReportOutDto.setDepartment(departmentMapper.toDto(entity.getDepartment()));
        return resultOfStudentForReportOutDto;
    }
}
