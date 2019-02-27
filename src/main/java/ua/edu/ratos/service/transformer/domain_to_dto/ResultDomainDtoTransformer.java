package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultDomainDtoTransformer {

    public ResultOutDto toDto(@NonNull final ResultDomain resultDomain) {

        String name = resultDomain.getUser().getName();
        String surname = resultDomain.getUser().getSurname();
        String user = name.concat(" ").concat(surname);

        String scheme = resultDomain.getScheme().getName();

        ResultOutDto dto = new ResultOutDto(user, scheme, resultDomain.isPassed());

        // add optional elements depending on settings

        final SettingsDomain settingsDomain = resultDomain.getScheme().getSettingsDomain();

        if (settingsDomain.isDisplayMark()) dto.setGrade(resultDomain.getGrade());

        if (settingsDomain.isDisplayPercent()) dto.setPercent(resultDomain.getPercent());

        if (settingsDomain.isDisplayThemeResults()) dto.setThemeResults(resultDomain.getThemeResults());

        if (settingsDomain.isDisplayQuestionResults())
            dto.setQuestionResults(getResultPerQuestion(resultDomain.getQuestionsMap(), resultDomain.getResponsesEvaluated()));

        return dto;
    }

    private List<ResultPerQuestionOutDto> getResultPerQuestion(@NonNull final Map<Long, QuestionDomain> questionsMap,
                                                               @NonNull final List<ResponseEvaluated> responsesEvaluated) {
        return responsesEvaluated.stream().map(responseEvaluated -> new ResultPerQuestionOutDto()
                .setQuestion(questionsMap.get(responseEvaluated.getQuestionId()).toDto())
                .setResponse(responseEvaluated.getResponse())
                .setScore(responseEvaluated.getScore()))
                .collect(Collectors.toList());
    }
}
