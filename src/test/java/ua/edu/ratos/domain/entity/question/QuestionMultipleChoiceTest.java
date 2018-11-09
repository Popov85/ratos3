package ua.edu.ratos.domain.entity.question;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.service.dto.response.ResponseMultipleChoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class QuestionMultipleChoiceTest {

    private QuestionMultipleChoice question;

    private List<AnswerMultipleChoice> answers;

    @Before
    public void init() {
        question = new QuestionMultipleChoice();
        question.setQuestionId(1L);
        answers = new ArrayList<>();
    }

    @Test
    public void evaluateOneCorrectRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 0, false));
        answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002L, "Answer2", (short)100, true));
        answers.add(createAnswer(1003L, "Answer3", (short)0, false));

        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, one right
        userAnswers = new Long[] {1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Right
        userAnswers = new Long[] {1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateOneCorrectNotRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 0, false));
        answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002L, "Answer2", (short)100,  false));
        answers.add(createAnswer(1003L, "Answer3", (short)0, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, one right
        userAnswers = new Long[] {1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Right
        userAnswers = new Long[] {1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateTwoCorrectRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 50, true));
        answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002L, "Answer2", (short)50,  true));
        answers.add(createAnswer(1003L, "Answer3", (short)0, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] {1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, one right
        userAnswers = new Long[] {1000l, 1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Right (both)
        userAnswers = new Long[] {1000l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateTwoCorrectNotRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 50, false));
        answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002L, "Answer2", (short)50,  false));
        answers.add(createAnswer(1003L, "Answer3", (short)0, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] {1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, one right
        userAnswers = new Long[] {1000l, 1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(50, question.evaluate(response));

        // Right (both)
        userAnswers = new Long[] {1000l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateTwoCorrectOneRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 50, true));
        answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002L, "Answer2", (short)50,  false));
        answers.add(createAnswer(1003L, "Answer3", (short)0, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] {1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, one right
        userAnswers = new Long[] {1000l, 1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right required
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(50, question.evaluate(response));

        // One right not required
        userAnswers = new Long[] {1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Right (both)
        userAnswers = new Long[] {1000l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateAllCorrectRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 25, true));
        answers.add(createAnswer(1001L, "Answer1", (short) 25, true));
        answers.add(createAnswer(1002L, "Answer2", (short) 25,  true));
        answers.add(createAnswer(1003L, "Answer3", (short) 25, true));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right required
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Two right required
        userAnswers = new Long[] {1000l, 1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Three right required
        userAnswers = new Long[] {1000l, 1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        //  Right: all answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateAllCorrectNotRequiredTest() {

        answers.add(createAnswer(1000L, "Answer0", (short) 25, false));
        answers.add(createAnswer(1001L, "Answer1", (short) 25, false));
        answers.add(createAnswer(1002L, "Answer2", (short) 25,  false));
        answers.add(createAnswer(1003L, "Answer3", (short) 25, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right not required
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(25, question.evaluate(response));

        // Two right not required
        userAnswers = new Long[] {1000l, 1001l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(50, question.evaluate(response));

        // Three right not required
        userAnswers = new Long[] {1000l, 1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(75, question.evaluate(response));

        //  Right: all answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateMultipleCorrectMultipleRequiredTest() {

        answers.add(createAnswer(1000l, "Title0", (short) 0, false));
        answers.add(createAnswer(1001l, "Title1", (short)33, true));
        answers.add(createAnswer(1002l, "Title2", (short)33, true));
        answers.add(createAnswer(1003l, "Title3", (short)0, false));
        answers.add(createAnswer(1004l, "Title4", (short)34, false));
        question.setAnswers(answers);

        Long[] userAnswers;
        ResponseMultipleChoice response;

        // Empty response
        userAnswers = new Long[] {};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l, 1004l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // All wrong
        userAnswers = new Long[] {1001l, 1003l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong
        userAnswers = new Long[] {1000l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One wrong, two right required
        userAnswers = new Long[] {1000l, 1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Two right required
        userAnswers = new Long[] {1001l, 1002l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(66, question.evaluate(response));

        // One right required (second required is not answered), one not required
        userAnswers = new Long[] {1001l, 1004l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // One right not required (both other required is not answered)
        userAnswers = new Long[] {1004l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(0, question.evaluate(response));

        // Right (two required, one not required)
        userAnswers = new Long[] {1001l, 1002l, 1004l};
        response = new ResponseMultipleChoice(1L, new HashSet(Arrays.asList(userAnswers)));
        assertEquals(100, question.evaluate(response));
    }

    private AnswerMultipleChoice createAnswer(Long id, String title, short percentage, boolean isRequired) {
        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswerId(id);
        answer.setAnswer(title);
        answer.setPercent(percentage);
        answer.setRequired(isRequired);
        return answer;
    }
}
