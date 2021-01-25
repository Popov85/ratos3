package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForStaffOutDto;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.transformer.SchemeWithCourseMinMapper;
import ua.edu.ratos.service.transformer.StudMinMapper;
import ua.edu.ratos.service.utils.DataFormatter;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ResultOfStudentForStaffDtoTransformer {

    private final SchemeWithCourseMinMapper schemeWithCourseMinMapper;

    private final ResultOfStudentPerThemeDtoTransformer resultPerThemeDtoTransformer;

    private final StudMinMapper studMinMapper;

    private final GameService gameService;


    public ResultOfStudentForStaffOutDto toDto(@NonNull final ResultOfStudent entity) {
        return new ResultOfStudentForStaffOutDto()
                .setResultId(entity.getResultId())
                .setScheme(schemeWithCourseMinMapper.toDto(entity.getScheme()))
                .setStudent(studMinMapper.toDto(entity.getStudent()))
                .setGrade(DataFormatter.getPrettyDouble(entity.getGrade()))
                .setPercent(DataFormatter.getPrettyDouble(entity.getPercent()))
                .setPassed(entity.isPassed())
                .setSessionEnded(entity.getSessionEnded())
                .setSessionLasted(entity.getSessionLasted())
                .setTimeouted(entity.isTimeOuted())
                .setCancelled(entity.isCancelled())
                .setPoints(entity.isPoints() ? gameService.getPoints(entity.getPercent()): null)
                .setLMS(entity.getLms().isPresent())
                .setDetails(entity.getResultDetails()!=null)
                .setThemeResults(entity.getResultTheme()
                        .stream()
                        .map(resultPerThemeDtoTransformer::toDto)
                        .collect(Collectors.toList()));
    }
}
