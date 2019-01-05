package ua.edu.ratos.service.session;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.session.grade.GradedResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class ResultBuilder {

    @Autowired
    private GradingService gradingService;

    @Autowired
    private ProgressDataService progressDataService;

    /**
     * Calculates the result object for the given dto
     * @param sessionData
     * @return the result of the given dto
     */
    public ResultDomain build(@NonNull final SessionData sessionData) {
        final ProgressData progressData = sessionData.getProgressData();

        // 1. Calculate percent
        final double score = progressData.getScore();
        final int quantity = sessionData.getQuestionDomains().size();
        final double percent = (score*100d)/quantity;

        // 2. Calculate grade and if-passed verdict
        final SchemeDomain schemeDomain = sessionData.getSchemeDomain();
        final GradedResult gradedResult = gradingService.grade(percent, schemeDomain);
        double grade = gradedResult.getGrade();
        final boolean passed = gradedResult.isPassed();

        return new ResultDomain(sessionData.getKey(), passed, percent, grade, getResultPerTheme(sessionData));
    }

    /**
     * It's 3-steps process
     * @param sessionData
     * @return
     */
    private List<ResultPerTheme> getResultPerTheme(@NonNull final SessionData sessionData) {
        List<ResultPerTheme> result = new ArrayList<>();
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        // 1. Conversion
        final List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);
        // 2. Grouping by theme
        final Map<Long, List<ResponseEvaluated>> groupedByTheme = responsesEvaluated
                .stream()
                .collect(Collectors.groupingBy(r -> getThemeId(questionsMap, r.getQuestionId())));
        // 3. Calculation
        final SchemeDomain schemeDomain = sessionData.getSchemeDomain();
        groupedByTheme.forEach((themeId,responsesList)->{
            final int quantity = responsesList.size();
            final double sum = responsesList.stream().mapToDouble(x -> x.getScore() / 100d).sum();
            double percent = (sum/quantity)*100d;
            final GradedResult gradedResult = gradingService.grade(percent, schemeDomain);
            double grade = gradedResult.getGrade();
            final boolean passed = gradedResult.isPassed();
            // themeDomain title retrieve from the first response in the list
            final String theme = getThemeName(questionsMap, responsesList.get(0).getQuestionId());
            result.add(new ResultPerTheme(themeId, theme, quantity, passed, percent, grade));
        });
        return result;
    }

    private Long getThemeId(@NonNull final Map<Long, QuestionDomain> questionsMap, @NonNull final Long questionId) {
        return questionsMap.get(questionId).getThemeDomain().getThemeId();
    }

    private String getThemeName(@NonNull final Map<Long, QuestionDomain> questionsMap, @NonNull final Long questionId) {
        return questionsMap.get(questionId).getThemeDomain().getName();
    }


}
