package ua.edu.ratos.service.session;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.service.session.domain.ResultPerTheme;
import ua.edu.ratos.service.session.domain.ProgressData;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.ResultOut;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.grade.GradedResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class ResultBuilderService {

    @Autowired
    private GradingService gradingService;

    @Autowired
    private ProgressDataService progressDataService;

    /**
     * Calculates the result object for the given session
     * @param sessionData
     * @return the result of the given session
     */
    public ResultOut build(@NonNull final SessionData sessionData) {
        final ProgressData progressData = sessionData.getProgressData();

        // 1. Calculate percent
        final double score = progressData.getScore();
        final int quantity = sessionData.getQuestions().size();
        final double percent = (score*100d)/quantity;

        // 2. Calculate grade and passed verdict
        final Scheme scheme = sessionData.getScheme();
        final GradedResult gradedResult = gradingService.grade(percent, scheme);
        final Number grade = gradedResult.getGrade();
        final boolean passed = gradedResult.isPassed();

        return new ResultOut(sessionData.getKey(), passed, percent, grade, getResultPerTheme(sessionData));
    }

    /**
     * it's 3-step process
     * @param sessionData
     * @return
     */
    private List<ResultPerTheme> getResultPerTheme(@NonNull final SessionData sessionData) {
        List<ResultPerTheme> result = new ArrayList<>();
        // 1. Conversion
        final List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);
        // 2. Grouping by theme
        final Map<Long, List<ResponseEvaluated>> groupedByTheme = responsesEvaluated
                .stream()
                .collect(Collectors.groupingBy(r -> r.getQuestion().getTheme().getThemeId()));
        // 3. Calculation
        final Scheme scheme = sessionData.getScheme();
        groupedByTheme.forEach((themeId,responsesList)->{
            final int quantity = responsesList.size();
            final double sum = responsesList.stream().mapToDouble(x -> x.getScore() / 100d).sum();
            double percent = (sum/quantity)*100d;
            final GradedResult gradedResult = gradingService.grade(percent, scheme);
            final Number grade = gradedResult.getGrade();
            final boolean passed = gradedResult.isPassed();
            // theme title retrieve from the first response in the list
            final String theme = responsesList.get(0).getQuestion().getTheme().getName();
            result.add(new ResultPerTheme(themeId, theme, quantity, passed, percent, grade));
        });
        return result;
    }

}
