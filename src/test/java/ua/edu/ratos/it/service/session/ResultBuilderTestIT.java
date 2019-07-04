package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionMCQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseMCQ;
import ua.edu.ratos.service.domain.ResultPerTheme;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.session.GameService;
import ua.edu.ratos.service.session.GradingService;
import ua.edu.ratos.service.session.ProgressDataService;
import ua.edu.ratos.service.session.ResultBuilder;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.transformer.entity_to_domain.UserDomainTransformer;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultBuilderTestIT {

    private final static List<Integer> SCORES = Arrays.asList(100, 100, 0, 50, 100, 50, 100, 0, 0, 0);

    @Autowired
    private ResultBuilder resultBuilder;

    @MockBean
    private SessionData sessionData;

    @MockBean
    private ProgressDataService progressDataService;

    @MockBean
    private GradingService gradingService;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserDomainTransformer userDomainTransformer;

    @MockBean
    private UserRepository userRepository;


    private ProgressData progressData;

    private List<QuestionDomain> questionDomains;

    private List<BatchEvaluated> batchesEvaluated;

    private List<ResponseEvaluated> responsesEvaluated;

    private Map<Long, QuestionDomain> questionsMap;

    @Before
    public void init() {
        questionDomains = new ArrayList<>();
        questionsMap = new HashMap<>();
        responsesEvaluated = new ArrayList<>();
        batchesEvaluated = new ArrayList<>();

        ThemeDomain t1 = getTheme(1L, "T1");
        ThemeDomain t2 = getTheme(2L, "T2");
        ThemeDomain t3 = getTheme(3L, "T3");
        ThemeDomain t4 = getTheme(4L, "T4");

        // ThemeDomain 1
        initBatchEvaluated(1L, "Q1", t1, SCORES.get(0));
        initBatchEvaluated(2L, "Q2", t1, SCORES.get(1));
        initBatchEvaluated(3L, "Q3", t1, SCORES.get(2));
        // ThemeDomain 2
        initBatchEvaluated(4L, "Q4", t2, SCORES.get(3));
        initBatchEvaluated(5L, "Q5", t2, SCORES.get(4));
        initBatchEvaluated(6L, "Q6", t2, SCORES.get(5));
        // ThemeDomain 3
        initBatchEvaluated(7L, "Q7", t3, SCORES.get(6));
        // ThemeDomain 4
        initBatchEvaluated(8L, "Q8", t3, SCORES.get(7));
        initBatchEvaluated(9L, "Q9", t4, SCORES.get(8));
        initBatchEvaluated(10L, "Q10", t4, SCORES.get(9));

        progressData = new ProgressData();
        progressData.setScore(5d);
        progressData.setBatchesEvaluated(batchesEvaluated);
    }


    @Test
    public void buildTest() {

        when(sessionData.getUserId()).thenReturn(1L);
        when(sessionData.getSchemeDomain()).thenReturn(getSchemeDomain());
        when(sessionData.getQuestionsMap()).thenReturn(questionsMap);
        when(sessionData.getProgressData()).thenReturn(progressData);
        when(sessionData.getLMSId()).thenReturn(Optional.of(new Long(1L)));

        when(progressDataService.getCurrentActualScore(sessionData)).thenReturn(85.0);
        when(progressDataService.toResponseEvaluated(sessionData)).thenReturn(responsesEvaluated);
        when(gradingService.grade(eq(1L), any(GradingDomain.class), eq(85.0))).thenReturn(new GradedResult(true, 5));
        when(gameService.getPoints(any(SessionData.class), eq(85.0))).thenReturn(Optional.of(new Integer(10)));
        when(userDomainTransformer.toDomain(any(User.class))).thenReturn(new UserDomain(1L, "TestUser", "TestUser"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        final ResultDomain resultDomain = resultBuilder.build(sessionData, false, false);
        final List<ResultPerTheme> themeResults = resultDomain.getThemeResults();

        final ResultPerTheme resultPerTheme1 = themeResults.get(0);
        final double percentT1 = resultPerTheme1.getPercent();
        Assert.assertEquals(66.6, percentT1, 0.1);
        final int quantityT1 = resultPerTheme1.getQuantity();
        Assert.assertEquals(3, quantityT1);


        final ResultPerTheme resultPerTheme2 = themeResults.get(1);
        final double percentT2 = resultPerTheme2.getPercent();
        Assert.assertEquals(66.6, percentT2, 0.1);
        final int quantityT2 = resultPerTheme2.getQuantity();
        Assert.assertEquals(3, quantityT2);


        final ResultPerTheme resultPerTheme3 = themeResults.get(2);
        final double percentT3 = resultPerTheme3.getPercent();
        Assert.assertEquals(50.0, percentT3, 0.1);
        final int quantityT3 = resultPerTheme3.getQuantity();
        Assert.assertEquals(2, quantityT3);


        final ResultPerTheme resultPerTheme4 = themeResults.get(3);
        final double percentT4 = resultPerTheme4.getPercent();
        Assert.assertEquals(0.0, percentT4, 0.1);
        final int quantityT4 = resultPerTheme4.getQuantity();
        Assert.assertEquals(2, quantityT4);
    }

    private void initBatchEvaluated(Long questionId, String question, ThemeDomain t, int score) {
        final QuestionDomain q = getQuestion(questionId, question, t);
        final Response r = getResponse(questionId, 1L);
        ResponseEvaluated re = new ResponseEvaluated(questionId, r, score, (byte)1,false);
        BatchEvaluated b = getBatchEvaluated(questionId, re);
        questionDomains.add(q);
        questionsMap.put(questionId, q);
        responsesEvaluated.add(re);
        batchesEvaluated.add(b);
    }

    private BatchEvaluated getBatchEvaluated(Long questionId, ResponseEvaluated responseEvaluated) {
        final HashMap<Long, ResponseEvaluated> responsesEvaluated = new HashMap<>();
        responsesEvaluated.put(questionId, responseEvaluated);
        return new BatchEvaluated(responsesEvaluated, new ArrayList<>(),  100, false);
    }

    private QuestionDomain getQuestion(Long questionId, String question, ThemeDomain themeDomain) {
        QuestionDomain q = new QuestionMCQDomain();
        q.setQuestionId(questionId);
        q.setQuestion(question);
        q.setThemeDomain(themeDomain);
        return q;
    }

    private Response getResponse(Long questionId, Long answerId) {
        Response r = new ResponseMCQ(questionId, new HashSet<>(Arrays.asList(answerId)));
        return r;
    }

    private ThemeDomain getTheme(Long themeId, String theme) {
        ThemeDomain t = new ThemeDomain();
        t.setThemeId(themeId);
        t.setName(theme);
        return t;
    }

    private SchemeDomain getSchemeDomain() {
        SchemeDomain schemeDomain = new SchemeDomain();
        schemeDomain.setSchemeId(1L);
        GradingDomain gradingDomain = new GradingDomain();
        gradingDomain.setGradingId(1L);

        SettingsDomain settingsDomain = new SettingsDomain();
        settingsDomain.setLevel2Coefficient(1.2f);
        settingsDomain.setLevel3Coefficient(1.5f);
        gradingDomain.setName("four-point");
        schemeDomain.setGradingDomain(gradingDomain);
        schemeDomain.setSettingsDomain(settingsDomain);
        return schemeDomain;
    }
}