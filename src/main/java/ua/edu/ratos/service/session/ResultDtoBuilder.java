package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.*;
import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.dto.ResultOutDto;
import ua.edu.ratos.service.session.dto.ResultPerQuestionOutDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultDtoBuilder {

    @Autowired
    private ProgressDataService progressDataService;

    public ResultOutDto build( @NonNull SessionData sessionData, @NonNull final Result result) {
        final Scheme scheme = sessionData.getScheme();
        final Settings settings = scheme.getSettings();
        final Mode mode = scheme.getMode();

        ResultOutDto dto = new ResultOutDto(sessionData.getKey(), result.isPassed());

        if (settings.isDisplayMark()) dto.setGrade(result.getGrade());

        if (settings.isDisplayPercent()) dto.setPercent(result.getPercent());

        if (settings.isDisplayThemeResults()) dto.setThemeResults(result.getThemeResults());

        if (mode.isResultDetails()) dto.setQuestionResults(getResultPerQuestion(sessionData));

        return dto;
    }

    private List<ResultPerQuestionOutDto> getResultPerQuestion(@NonNull final SessionData sessionData) {
        Map<Long, Question> questionsMap = sessionData.getQuestionsMap();
        final List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);
        return responsesEvaluated
                .stream()
                .map(responseEvaluated ->
                        new ResultPerQuestionOutDto(
                                responseEvaluated.getQuestionId(),
                                getQuestionTitle(questionsMap, responseEvaluated.getQuestionId()),
                                responseEvaluated.getResponse(), responseEvaluated.getScore()))
                .collect(Collectors.toList());
    }

    private String getQuestionTitle(@NonNull final Map<Long, Question> questionsMap, @NonNull final Long questionId) {
        return questionsMap.get(questionId).getQuestion();
    }
}
