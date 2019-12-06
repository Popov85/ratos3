package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.utils.DataFormatter;

@Component
@AllArgsConstructor
public class ResultOfStudentForStaffDtoTransformer {

    private final SchemeWithCourseMinDtoTransformer schemeWithCourseMinDtoTransformer;

    private final StudMinDtoTransformer studMinDtoTransformer;

    private final GameService gameService;


    public ResultOfStudentForStaffOutDto toDto(@NonNull final ResultOfStudent entity) {
        return new ResultOfStudentForStaffOutDto()
                .setResultId(entity.getResultId())
                .setScheme(schemeWithCourseMinDtoTransformer.toDto(entity.getScheme()))
                .setStudent(studMinDtoTransformer.toDto(entity.getStudent()))
                .setGrade(DataFormatter.getPrettyDouble(entity.getGrade()))
                .setPercent(DataFormatter.getPrettyDouble(entity.getPercent()))
                .setPassed(entity.isPassed())
                .setSessionEnded(entity.getSessionEnded())
                .setSessionLasted(entity.getSessionLasted())
                .setTimeouted(entity.isTimeOuted())
                .setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()): null)
                .setLMS(entity.getLms().isPresent());
    }
}
