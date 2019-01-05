package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultDtoBuilder {

    @Autowired
    private ProgressDataService progressDataService;

    public ResultOutDto build( @NonNull final SessionData sessionData, @NonNull final ResultDomain resultDomain) {
        final SchemeDomain schemeDomain = sessionData.getSchemeDomain();
        final SettingsDomain settingsDomain = schemeDomain.getSettingsDomain();
        final ModeDomain modeDomain = schemeDomain.getModeDomain();

        ResultOutDto dto = new ResultOutDto(sessionData.getKey(), resultDomain.isPassed());

        if (settingsDomain.isDisplayMark()) dto.setGrade(resultDomain.getGrade());

        if (settingsDomain.isDisplayPercent()) dto.setPercent(resultDomain.getPercent());

        if (settingsDomain.isDisplayThemeResults()) dto.setThemeResults(resultDomain.getThemeResults());

        if (modeDomain.isResultDetails()) dto.setQuestionResults(getResultPerQuestion(sessionData));

        return dto;
    }

    private List<ResultPerQuestionOutDto> getResultPerQuestion(@NonNull final SessionData sessionData) {
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
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

    private String getQuestionTitle(@NonNull final Map<Long, QuestionDomain> questionsMap, @NonNull final Long questionId) {
        return questionsMap.get(questionId).getQuestion();
    }
}
