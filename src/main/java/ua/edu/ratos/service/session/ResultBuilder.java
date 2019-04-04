package ua.edu.ratos.service.session;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.transformer.entity_to_domain.UserDomainTransformer;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Getter
public class ResultBuilder {

    private AppProperties appProperties;

    private GradingService gradingService;

    private ProgressDataService progressDataService;

    private GameService gameService;

    private UserRepository userRepository;

    private UserDomainTransformer userDomainTransformer;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setGradingService(GradingService gradingService) {
        this.gradingService = gradingService;
    }

    @Autowired
    public void setProgressDataService(ProgressDataService progressDataService) {
        this.progressDataService = progressDataService;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserDomainTransformer(UserDomainTransformer userDomainTransformer) {
        this.userDomainTransformer = userDomainTransformer;
    }

    /**
     * Builds the result object by performing all its fields calculations.
     * It is only launched at the valid end of a learning session.
     * @param sessionData sessionData
     * @return result domain object
     */
    @Transactional
    public ResultDomain build(@NonNull final SessionData sessionData, boolean timeOuted, boolean cancelled) {
        // 1. Calculate percent
        final double percent = progressDataService.getCurrentScore(sessionData);
        // 2. Calculate grade and is passed verdict
        final SchemeDomain schemeDomain = sessionData.getSchemeDomain();
        final GradingDomain gradingDomain = schemeDomain.getGradingDomain();
        final GradedResult gradedResult = gradingService.grade(schemeDomain.getSchemeId(), gradingDomain, percent);

        // 3. User info
        Long userId = sessionData.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found!"));
        UserDomain userDomain = userDomainTransformer.toDomain(user);

        // 4. Additional params
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();
        List<ResponseEvaluated> responsesEvaluated = progressDataService.toResponseEvaluated(sessionData);

        ResultDomain result = new ResultDomain()
                .setUser(userDomain)
                .setScheme(schemeDomain)
                .setPercent(percent)
                .setPassed(gradedResult.isPassed())
                .setGrade(gradedResult.getGrade())
                .setTimeOuted(timeOuted)
                .setCancelled(cancelled)
                .setTimeSpent(sessionData.getProgressData().getTimeSpent())
                .setThemeResults(getResultPerTheme(questionsMap, responsesEvaluated))
                .setQuestionsMap(questionsMap)
                .setResponsesEvaluated(responsesEvaluated)
                .setLmsId(sessionData.getLMSId().orElse(null));

        // Gamification points (if any) according to the settings.
        // It is needed for end-user after session results inside DTO object
        // without actually waiting for completion of full long DB operations.
        if (gameService.isGameOn()) {
            AppProperties.Game gameProps = appProperties.getGame();
            if ((!cancelled && !timeOuted)
                    || (cancelled && gameProps.isProcessCancelledResults())
                    || (timeOuted && gameProps.isProcessTimeoutedResults())) {
                result.setPoints(gameService.getPoints(sessionData, percent).get());
            }
        }
        return result;
    }


    /**
     * It's 2-steps process: group responses (including nullable) by theme
     * and create a list of results per theme
     * @param questionsMap
     * @param responsesEvaluated
     * @return list of results per each theme within scheme
     */
    private List<ResultPerTheme> getResultPerTheme(@NonNull final Map<Long, QuestionDomain> questionsMap, @NonNull final List<ResponseEvaluated> responsesEvaluated) {
        // Grouping by theme
        final Map<ThemeDomain, List<ResponseEvaluated>> groupedByTheme = responsesEvaluated.stream()
                .collect(Collectors.groupingBy(r -> questionsMap.get(r.getQuestionId()).getThemeDomain()));
        // Calculation
        return groupedByTheme.entrySet().stream().map(e -> {
            List<ResponseEvaluated> responses = e.getValue();
            final int quantity = responses.size();
            // sum scores for each theme
            final double sum = responses.stream().mapToDouble(r -> r.getScore() / 100d).sum();
            double percent = (sum / quantity) * 100d;
            return new ResultPerTheme(e.getKey(), quantity, percent);
        }).collect(Collectors.toList());

    }
}
