package ua.edu.ratos.service.domain.question;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.answer.AnswerSQDomain;
import ua.edu.ratos.service.domain.response.ResponseSQ;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QuestionSQDomainParameterizedTest {

    private QuestionSQDomain question;

    @Parameterized.Parameter(0)
    public ResponseSQ response;

    @Parameterized.Parameter(1)
    public int expected;

    @Parameterized.Parameter(2)
    public Class<? extends Exception> expectedException;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        question = new QuestionSQDomain();
        question.setQuestionId(1L);
        Set<AnswerSQDomain> answers = new HashSet<>();
        answers.add(createAnswer(1000l, (short) 0));
        answers.add(createAnswer(1001l, (short) 1));
        answers.add(createAnswer(1002l, (short) 2));
        answers.add(createAnswer(1003l, (short) 3));
        answers.add(createAnswer(1004l, (short) 4));
        question.setAnswers(answers);
    }

    @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1} exception {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, 0, NullPointerException.class},
                {new ResponseSQ(1L, null), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1001l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1002l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1003l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1004l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1001L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1002L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1003L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1004L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1001L, 1002L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1001L, 1003L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1001L, 1004L})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1004l, 1003l, 1002l, 1001l, 1000l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{2000l, 2001l, 2002l, 2003l, 2004l})), 0, null},
                {new ResponseSQ(1L, Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l, 1004l})), 100, null}
        });
    }

    @Test(timeout = 1000L)
    public void evaluateTest() {
        //setup expected exception
        if (expectedException != null) {
            thrown.expect(expectedException);
        }
        assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response));
    }

    private AnswerSQDomain createAnswer(Long answerId, short order) {
        AnswerSQDomain answer = new AnswerSQDomain();
        answer.setAnswerId(answerId);
        PhraseDomain phraseDomain = new PhraseDomain();
        answer.setPhraseDomain(phraseDomain);
        answer.setOrder(order);
        return answer;
    }
}
