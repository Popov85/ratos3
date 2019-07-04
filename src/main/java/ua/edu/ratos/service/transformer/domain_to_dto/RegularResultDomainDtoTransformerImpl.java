package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;
import ua.edu.ratos.service.session.EvaluatorPostProcessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Qualifier("regular")
public class RegularResultDomainDtoTransformerImpl implements ResultDomainDtoTransformer {

    private EvaluatorPostProcessor evaluatorPostProcessor;

    @Autowired
    public void setEvaluatorPostProcessor(EvaluatorPostProcessor evaluatorPostProcessor) {
        this.evaluatorPostProcessor = evaluatorPostProcessor;
    }

    @Override
    public ResultOutDto toDto(@NonNull final ResultDomain resultDomain) {
        if (resultDomain.isCancelled())
            throw new RuntimeException("Invalid API usage, use another impl. for cancelled sessions!");

        String name = resultDomain.getUser().getName();
        String surname = resultDomain.getUser().getSurname();
        String user = name.concat(" ").concat(surname);

        String scheme = resultDomain.getScheme().getName();

        ResultOutDto dto = new ResultOutDto(user, scheme, resultDomain.isPassed());

        // add gamification points if any
        dto.setPoints(resultDomain.getPoints().get());
        // sign if this session has been timeouted
        dto.setIsTimeouted(resultDomain.isTimeOuted());

        // add optional elements depending on settings
        final SettingsDomain settingsDomain = resultDomain.getScheme().getSettingsDomain();
        if (settingsDomain.isDisplayMark()) dto.setGrade(resultDomain.getGrade());
        if (settingsDomain.isDisplayPercent()) dto.setPercent(resultDomain.getPercent());
        if (settingsDomain.isDisplayThemeResults()) dto.setThemeResults(resultDomain.getThemeResults());
        if (settingsDomain.isDisplayQuestionResults())
            dto.setQuestionResults(getResultPerQuestion(resultDomain.getQuestionsMap(), resultDomain.getResponsesEvaluated(), settingsDomain));
        return dto;
    }

    private List<ResultPerQuestionOutDto> getResultPerQuestion(@NonNull final Map<Long, QuestionDomain> questionsMap,
                                                               @NonNull final List<ResponseEvaluated> responsesEvaluated,
                                                               @NonNull final SettingsDomain settingsDomain) {
        return responsesEvaluated
                .stream()
                .map(r -> new ResultPerQuestionOutDto()
                        .setQuestion(questionsMap.get(r.getQuestionId()).toDto())
                        .setResponse(r.getResponse())
                        .setScore(r.getScore())
                        .setBounty((r.getLevel() != 1) ? evaluatorPostProcessor.getBounty(r.getLevel(), settingsDomain) : null)
                        .setPenalty((r.isPenalty()) ? evaluatorPostProcessor.getPenalty() : null))
                .collect(Collectors.toList());
    }

}
