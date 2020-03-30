package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.service.domain.answer.AnswerMCQDomain;
import ua.edu.ratos.service.domain.response.ResponseMCQ;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RunWith(Enclosed.class)
public class QuestionMCQDomainParameterizedTest {

    @RunWith(Parameterized.class)
    public static class OneCorrectRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();

            answers.add(createAnswer(1000L, "Answer0", (short) 0, false));
            answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
            answers.add(createAnswer(1002L, "Answer2", (short)100, true));
            answers.add(createAnswer(1003L, "Answer3", (short)0, false));

            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    @RunWith(Parameterized.class)
    public static class OneCorrectNotRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 0, false));
            answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
            answers.add(createAnswer(1002L, "Answer2", (short)100,  false));
            answers.add(createAnswer(1003L, "Answer3", (short)0, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    @RunWith(Parameterized.class)
    public static class TwoCorrectRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 50, true));
            answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
            answers.add(createAnswer(1002L, "Answer2", (short)50,  true));
            answers.add(createAnswer(1003L, "Answer3", (short)0, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L,1002L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class TwoCorrectNotRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 50, false));
            answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
            answers.add(createAnswer(1002L, "Answer2", (short)50,  false));
            answers.add(createAnswer(1003L, "Answer3", (short)0, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L,1002L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class TwoCorrectOneRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 50, true));
            answers.add(createAnswer(1001L, "Answer1", (short) 0, false));
            answers.add(createAnswer(1002L, "Answer2", (short)50,  false));
            answers.add(createAnswer(1003L, "Answer3", (short)0, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L,1002L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class AllCorrectRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 25, true));
            answers.add(createAnswer(1001L, "Answer1", (short) 25, true));
            answers.add(createAnswer(1002L, "Answer2", (short) 25,  true));
            answers.add(createAnswer(1003L, "Answer3", (short) 25, true));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l, 1003l}))), 0},

                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1002L, 1003L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    @RunWith(Parameterized.class)
    public static class AllCorrectNotRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000L, "Answer0", (short) 25, false));
            answers.add(createAnswer(1001L, "Answer1", (short) 25, false));
            answers.add(createAnswer(1002L, "Answer2", (short) 25,  false));
            answers.add(createAnswer(1003L, "Answer3", (short) 25, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 25},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 25},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 25},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1003l}))), 25},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1002l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1003l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l, 1003l}))), 50},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l}))), 75},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1003l}))), 75},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1002l, 1003l}))), 75},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l, 1003l}))), 75},

                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1002L, 1003L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response),0.01);
        }
    }


    @RunWith(Parameterized.class)
    public static class MultipleCorrectMultipleRequired {

        private QuestionMCQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMCQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionMCQDomain();
            question.setQuestionId(1L);
            Set<AnswerMCQDomain> answers = new HashSet<>();
            answers.add(createAnswer(1000l, "Title0", (short) 0, false));
            answers.add(createAnswer(1001l, "Title1", (short)33, true));
            answers.add(createAnswer(1002l, "Title2", (short)33, true));
            answers.add(createAnswer(1003l, "Title3", (short)0, false));
            answers.add(createAnswer(1004l, "Title4", (short)34, false));
            question.setAnswers(answers);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseMCQ(1L, null), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1004l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1002L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1002l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1001l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001l, 1002l, 1003l}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1002L, 1003L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1002L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1003L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1002L, 1003L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001L, 1002L, 1003L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1000L, 1001L, 1002L, 1003L, 1004L}))), 0},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001L, 1002L}))), 66},
                    {new ResponseMCQ(1L, new HashSet(Arrays.asList(new Long[]{1001L, 1002L, 1004L}))), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    private static AnswerMCQDomain createAnswer(Long id, String title, short percentage, boolean isRequired) {
        AnswerMCQDomain answer = new AnswerMCQDomain();
        answer.setAnswerId(id);
        answer.setAnswer(title);
        answer.setPercent(percentage);
        answer.setRequired(isRequired);
        return answer;
    }
}
