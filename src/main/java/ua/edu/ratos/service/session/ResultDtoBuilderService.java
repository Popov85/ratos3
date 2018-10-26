package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.Mode;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.Settings;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.ResultOut;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultDtoBuilderService {

    @Autowired
    private ProgressDataService progressDataService;

    public ResultOutDto build( @NonNull SessionData sessionData, @NonNull final ResultOut resultOut) {
        final Scheme scheme = sessionData.getScheme();
        final Settings settings = scheme.getSettings();
        final Mode mode = scheme.getMode();

        ResultOutDto dto = new ResultOutDto(sessionData.getKey(), resultOut.isPassed());

        if (settings.isDisplayMark()) dto.setGrade(resultOut.getGrade());

        if (settings.isDisplayPercent()) dto.setPercent(resultOut.getPercent());

        if (settings.isDisplayThemeResults()) dto.setThemeResults(resultOut.getThemeResults());

        if (mode.isResultDetails()) dto.setQuestionResults(getResultPerQuestion(sessionData));

        return dto;
    }

    private List<ResultPerQuestionOutDto> getResultPerQuestion(@NonNull final SessionData sessionData) {
        final List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);
        return responsesEvaluated
                .stream()
                .map(responseEvaluated ->
                        new ResultPerQuestionOutDto(
                                responseEvaluated.getQuestion().getQuestionId(),
                                responseEvaluated.getQuestion().getQuestion(),
                                responseEvaluated.getResponse(), responseEvaluated.getScore()))
                .collect(Collectors.toList());
    }
}
