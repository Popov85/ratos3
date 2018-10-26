package ua.edu.ratos.service.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShiftServiceTest {

    private ShiftService shiftService;

    private List<Question> questions;

    private Map<Long, Question> questionsMap;

    List<Long> idsToShift;

    @Mock
    private SessionData sessionData;

    @Before
    public void init() {
        shiftService = new ShiftService();

        questions = new ArrayList<>(Arrays.asList(
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
        Mockito.<List<Question>>when(sessionData.getQuestions()).thenReturn(questions);
        Mockito.<Map<Long, Question>>when(sessionData.getQuestionsMap()).thenReturn(questionsMap);
        shiftService.doShift(idsToShift, sessionData);

        verify(sessionData, times(1)).getQuestions();
        verify(sessionData, times(1)).getQuestionsMap();

        // Expected list IDs :: {1, 3, 5, 2, 4}
        Assert.assertEquals(5, questions.size());
        Assert.assertEquals(1L, questions.get(0).getQuestionId().longValue());
        Assert.assertEquals(3L, questions.get(1).getQuestionId().longValue());
        Assert.assertEquals(5L, questions.get(2).getQuestionId().longValue());
        Assert.assertEquals(2L, questions.get(3).getQuestionId().longValue());
        Assert.assertEquals(4L, questions.get(4).getQuestionId().longValue());
    }

    private Question question(Long id, String title) {
        Question q = new QuestionMultipleChoice();
        q.setQuestionId(id);
        q.setQuestion(title);
        return q;
    }

}
