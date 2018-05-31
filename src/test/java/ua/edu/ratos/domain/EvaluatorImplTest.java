package ua.edu.ratos.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.domain.model.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.model.question.QuestionMultipleChoice;
import ua.edu.ratos.service.EvaluatorImpl;
import ua.edu.ratos.service.dto.ResponseMultipleChoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EvaluatorImplTest {

    @Test
    public void checkTestOneRightRequired() {


        QuestionMultipleChoice question = new QuestionMultipleChoice();
        List<AnswerMultipleChoice> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Answer0", (short) 0, false));
        answers.add(createAnswer(1001l, "Answer1", (short) 0, false));
        answers.add(createAnswer(1002l, "Answer2", (short)100, true));
        answers.add(createAnswer(1003l, "Answer3", (short)0, false));

        question.setAnswers(answers);

        ResponseMultipleChoice response = new ResponseMultipleChoice();
        response.ids = Arrays.asList();
        assertEquals(0, response.evaluateWith(new EvaluatorImpl(question)));

  /*      // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All wrong
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong
        userAnswers = new Long[] { 1000l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong, one right
        userAnswers = new Long[] { 1000l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // Right
        userAnswers = new Long[] { 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());*/

    }

    private AnswerMultipleChoice createAnswer(Long id, String title, short percentage, boolean isRequired) {
        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswerId(id);
        answer.setAnswer(title);
        answer.setPercent(percentage);
        answer.setRequired(isRequired);
        return answer;
    }

/*    @Test
    public void calculateResultTestOneRightNotRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 0, false));
        answers.add(createAnswer(1001l, "Title1", 0, false));
        answers.add(createAnswer(1002l, "Title2", 100, false));
        answers.add(createAnswer(1003l, "Title3", 0, false));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All wrong
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong
        userAnswers = new Long[] { 1000l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong, one right
        userAnswers = new Long[] { 1000l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // Right
        userAnswers = new Long[] { 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

    }

    @Test
    public void calculateResultTestTwoRightRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 50, true));
        answers.add(createAnswer(1001l, "Title1", 0, false));
        answers.add(createAnswer(1002l, "Title2", 50, true));
        answers.add(createAnswer(1003l, "Title3", 0, false));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All wrong
        userAnswers = new Long[] { 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong, one right, without required
        userAnswers = new Long[] { 1002l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One right without one required
        userAnswers = new Long[] { 1000l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All right
        userAnswers = new Long[] { 1000l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

    }

    @Test
    public void calculateResultTestTwoRightNotRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 50, false));
        answers.add(createAnswer(1001l, "Title1", 0, false));
        answers.add(createAnswer(1002l, "Title2", 50, false));
        answers.add(createAnswer(1003l, "Title3", 0, false));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All wrong
        userAnswers = new Long[] { 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong, one right
        userAnswers = new Long[] { 1002l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One right
        userAnswers = new Long[] { 1000l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(50, questionResult.calculateResult());

        // All right
        userAnswers = new Long[] { 1000l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

    }

    @Test
    public void calculateResultTestTwoRightOneRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 50, false));
        answers.add(createAnswer(1001l, "Title1", 0, false));
        answers.add(createAnswer(1002l, "Title2", 50, true));
        answers.add(createAnswer(1003l, "Title3", 0, false));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All wrong
        userAnswers = new Long[] { 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());


        // One wrong, one right (not required)
        userAnswers = new Long[] { 1000l, 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One wrong, one right (required)
        userAnswers = new Long[] { 1001l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One right (not required)
        userAnswers = new Long[] { 1000l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One right (required)
        userAnswers = new Long[] { 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(50, questionResult.calculateResult());

        // All right
        userAnswers = new Long[] { 1000l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());
    }

    @Test
    public void calculateResultTestAllTrueRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 25, true));
        answers.add(createAnswer(1001l, "Title1", 25, true));
        answers.add(createAnswer(1002l, "Title2", 25, true));
        answers.add(createAnswer(1003l, "Title3", 25, true));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

        // One true without many required
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // Two true without many required
        userAnswers = new Long[] { 1000l, 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // Three true without one required
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

    }

    @Test
    public void calculateResultTestAllTrueNotRequired() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 25, false));
        answers.add(createAnswer(1001l, "Title1", 25, false));
        answers.add(createAnswer(1002l, "Title2", 25, false));
        answers.add(createAnswer(1003l, "Title3", 25, false));
        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

        // One true without many not required
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(25, questionResult.calculateResult());

        // Two true without many not required
        userAnswers = new Long[] { 1000l, 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(50, questionResult.calculateResult());

        // Three true without one not required
        userAnswers = new Long[] { 1000l, 1001l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(75, questionResult.calculateResult());

    }

    @Test
    public void calculateResultTestManyAnswers() {

        QuestionResult questionResult;

        Question question = new Question();

        List<Answer> answers = new ArrayList<>();
        answers.add(createAnswer(1000l, "Title0", 0, false));
        answers.add(createAnswer(1001l, "Title1", 33, true));
        answers.add(createAnswer(1002l, "Title2", 33, true));
        answers.add(createAnswer(1003l, "Title3", 0, false));
        answers.add(createAnswer(1004l, "Title4", 34, false));

        question.setAnswers(answers);

        // Empty list
        Long[] userAnswers = new Long[] {};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All answers
        userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l, 1004l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // One true without all required
        userAnswers = new Long[] { 1004l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All not true
        userAnswers = new Long[] { 1000l, 1003l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

        // All true
        userAnswers = new Long[] { 1001l, 1002l, 1004l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(100, questionResult.calculateResult());

        // All true required without one not required
        userAnswers = new Long[] { 1001l, 1002l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(66, questionResult.calculateResult());

        // One true required without one true required
        userAnswers = new Long[] { 1001l};
        questionResult = new QuestionResult(question, Arrays.asList(userAnswers));
        assertEquals(0, questionResult.calculateResult());

    }*/

}

