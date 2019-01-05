package ua.edu.ratos.service.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionMCQDomain;

import java.util.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShiftServiceTest {

    private ShiftService shiftService;

    private List<QuestionDomain> questionDomains;

    private Map<Long, QuestionDomain> questionsMap;

    List<Long> idsToShift;

    @Mock
    private SessionData sessionData;

    @Before
    public void init() {
        shiftService = new ShiftService();

        questionDomains = new ArrayList<>(Arrays.asList(
                question(1L, "Q1"),
                question(2L, "Q2"),
                question(3L, "Q3"),
                question(4L, "Q4"),
                question(5L, "Q5")));

        questionsMap = new HashMap<>();
        questionsMap.put(1L, question(1L, "Q1"));
        questionsMap.put(2L, question(2L, "Q2"));
        questionsMap.put(3L, question(3L, "Q3"));
        questionsMap.put(4L, question(4L, "Q4"));
        questionsMap.put(5L, question(5L, "Q5"));

        idsToShift = new ArrayList<>(Arrays.asList(2L, 4L));
    }

    @Test
    public void doShiftTest() {
        Mockito.<List<QuestionDomain>>when(sessionData.getQuestionDomains()).thenReturn(questionDomains);
        Mockito.<Map<Long, QuestionDomain>>when(sessionData.getQuestionsMap()).thenReturn(questionsMap);
        shiftService.doShift(idsToShift, sessionData);

        verify(sessionData, times(1)).getQuestionDomains();
        verify(sessionData, times(1)).getQuestionsMap();

        // Expected list IDs :: {1, 3, 5, 2, 4}
        Assert.assertEquals(5, questionDomains.size());
        Assert.assertEquals(1L, questionDomains.get(0).getQuestionId().longValue());
        Assert.assertEquals(3L, questionDomains.get(1).getQuestionId().longValue());
        Assert.assertEquals(5L, questionDomains.get(2).getQuestionId().longValue());
        Assert.assertEquals(2L, questionDomains.get(3).getQuestionId().longValue());
        Assert.assertEquals(4L, questionDomains.get(4).getQuestionId().longValue());
    }

    private QuestionDomain question(Long id, String title) {
        QuestionDomain q = new QuestionMCQDomain();
        q.setQuestionId(id);
        q.setQuestion(title);
        return q;
    }

}
