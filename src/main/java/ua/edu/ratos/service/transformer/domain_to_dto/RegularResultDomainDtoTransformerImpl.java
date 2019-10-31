package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;
import ua.edu.ratos.service.session.EvaluatorPostProcessor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
@Qualifier("regular")
public class RegularResultDomainDtoTransformerImpl implements ResultDomainDtoTransformer {

    private ResultPerThemeDtoTransformer resultPerThemeDtoTransformer;

    private EvaluatorPostProcessor evaluatorPostProcessor;

    @Autowired
    public void setResultPerThemeDtoTransformer(ResultPerThemeDtoTransformer resultPerThemeDtoTransformer) {
        this.resultPerThemeDtoTransformer = resultPerThemeDtoTransformer;
    }

    @Autowired
    public void setEvaluatorPostProcessor(EvaluatorPostProcessor evaluatorPostProcessor) {
        this.evaluatorPostProcessor = evaluatorPostProcessor;
    }

    @Override
    public ResultOutDto toDto(@NonNull final ResultDomain r) {
        if (r.isCancelled())
            throw new RuntimeException("Invalid API usage, use another impl. for cancelled sessions!");
        UserDomain u = r.getUser();
        String user = u.getName().concat(" ").concat(u.getSurname());
        String scheme = r.getScheme().getName();
        // create DTO
        ResultOutDto dto = new ResultOutDto(user, scheme, r.isTimeOuted(), r.isPassed());
        // add optional elements depending on settings
        final OptionsDomain o = r.getScheme().getOptionsDomain();
        if (o.isDisplayResultMark()) dto.setGrade(round(r.getGrade()));
        if (o.isDisplayResultScore()) dto.setPercent(round(r.getPercent()));
        if (o.isDisplayTimeSpent()) dto.setTimeSpent(toTimestamp(r.getTimeSpent()));
        if (o.isDisplayResultOnThemes()) dto.setThemeResults(toResultsPerThemeDto(r));
        if (o.isDisplayResultOnQuestions()) dto.setQuestionResults(toResultsPerQuestionDto(r));
        // add gamification points if any
        if (r.getPoints().isPresent()) dto.setPoints(r.getPoints().get());
        return dto;
    }

    private List<ResultPerThemeOutDto> toResultsPerThemeDto(@NonNull final ResultDomain rd) {
        return rd.getThemeResults()
                .stream()
                .map(tr -> resultPerThemeDtoTransformer.toDto(tr))
                .collect(Collectors.toList());
    }

    // Consider(!): perhaps better to take for base questions in session map, and not responses?
    // In case of timeout, a student will see only subset(!) of questions in the session,
    // to which responses were provided
    private List<ResultPerQuestionOutDto> toResultsPerQuestionDto(@NonNull final ResultDomain rd) {
        List<ResultPerQuestionOutDto> collect = rd.getResponsesEvaluated()
                .stream()
                .map(re -> toResultPerQuestionDto(rd, re))
                .collect(Collectors.toList());
        return collect;
    }

    private ResultPerQuestionOutDto toResultPerQuestionDto(@NonNull final ResultDomain rd,
                                                           @NonNull final ResponseEvaluated re) {
        ModeDomain m = rd.getScheme().getModeDomain();
        SettingsDomain s = rd.getScheme().getSettingsDomain();
        Map<Long, QuestionDomain> map = rd.getQuestionsMap();
        QuestionDomain q = map.get(re.getQuestionId());

        Double bounty = (q.getLevel() != 1) ? evaluatorPostProcessor.getBounty(q.getLevel(), s) : null;
        Double penalty = (re.isPenalty()) ? evaluatorPostProcessor.getPenalty() : null;

        ResultPerQuestionOutDto result = new ResultPerQuestionOutDto()
                .setQuestion(q.toDto())
                .setScore(re.getScore())
                .setBounty(bounty)
                .setPenalty(penalty)
                .setResponse(re.getResponse());
        // Include right answer only if the current mode allows it
        if (m.isRightAnswer()) result.setCorrectAnswer(q.getCorrectAnswer());
        return result;
    }

    private String round(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String result = df.format(value);
        // If fraction is 0, let's omit it in the resulting value
        String[] split = result.split(".");
        if (split.length>1 && split[1] == "0") return split[0];
        return result;
    }

    private String toTimestamp(long secSpent) {
        return Duration.of(secSpent, SECONDS).toString();
    }

}
