package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.answer.AnswerMQDomain;
import ua.edu.ratos.service.domain.response.ResponseMQ;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@RunWith(Enclosed.class)
public class QuestionMQDomainParameterizedTest {

    private static final String[] SAMPLES0 = new String[] {"phrase0", "zero phraseDomain"};
    private static final String[] SAMPLES1 = new String[] {"phrase1", "first phraseDomain"};
    private static final String[] SAMPLES2 = new String[] {"phrase2", "second phraseDomain"};
    private static final String[] SAMPLES3 = new String[] {"phrase3", "third phraseDomain"};
    private static final String[] SAMPLES4 = new String[] {"phrase4", "fourth phraseDomain"};


    @RunWith(Parameterized.class)
    public static class PartialResponseAllowed {
        private QuestionMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMQ response;

        @Parameterized.Parameter(1)
        public double expected;

        @Parameterized.Parameter(2)
        public Class<? extends Exception> expectedException;

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Before
        public void init() {
            question = new QuestionMQDomain();
            question.setQuestionId(1L);
            AnswerMQDomain answer0 = createAnswer(1000l, 2000l, 2001l, SAMPLES0);
            AnswerMQDomain answer1 = createAnswer(1001l, 2002l, 2003l, SAMPLES1);
            AnswerMQDomain answer2 = createAnswer(1002l, 2004l, 2005l, SAMPLES2);
            AnswerMQDomain answer3 = createAnswer(1003l, 2006l, 2007l, SAMPLES3);
            AnswerMQDomain answer4 = createAnswer(1004l, 2008l, 2009l, SAMPLES4);
            question.setAnswers(new HashSet(Arrays.asList(answer0, answer1, answer2, answer3, answer4)));
            question.setPartialResponseAllowed(false);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1} exception {2}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null, 0, NullPointerException.class},
                    {new ResponseMQ(1L, null), 0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(new ResponseMQ.Triple[]{}))), 0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2003l),//-
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2007l),
                            new ResponseMQ.Triple(1004l, 2008l, 2009l)))),
                            0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2001l),
                            new ResponseMQ.Triple(1001l, 2002l, 2001l),//-
                            new ResponseMQ.Triple(1002l, 2004l, 2009l),//-
                            new ResponseMQ.Triple(1003l, 2006l, 2005l),//-
                            new ResponseMQ.Triple(1004l, 2008l, 2007l)))),//-
                            0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2001l, 2000l),//-
                            new ResponseMQ.Triple(1001l, 2003l, 2002l),//-
                            new ResponseMQ.Triple(1002l, 2005l, 2004l),//-
                            new ResponseMQ.Triple(1003l, 2007l, 2006l),//-
                            new ResponseMQ.Triple(1004l, 2009l, 2008l)))),//-
                            0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2001l, 3000l),//-
                            new ResponseMQ.Triple(1001l, 2003l, 3002l),//-
                            new ResponseMQ.Triple(1002l, 2005l, 3004l),//-
                            new ResponseMQ.Triple(1003l, 2007l, 3006l),//-
                            new ResponseMQ.Triple(1004l, 2009l, 3008l)))),//-
                            0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 3001l, 2000l),//-
                            new ResponseMQ.Triple(1001l, 3003l, 2002l),//-
                            new ResponseMQ.Triple(1002l, 3005l, 2004l),//-
                            new ResponseMQ.Triple(1003l, 3007l, 2006l),//-
                            new ResponseMQ.Triple(1004l, 3009l, 2008l)))),//-
                            0, null},

                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2001l),
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2007l),
                            new ResponseMQ.Triple(1004l, 2008l, 2009l)))),
                            100, null},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() {
            //setup expected exception
            if (expectedException != null) {
                thrown.expect(expectedException);
            }
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }

    }


    @RunWith(Parameterized.class)
    public static class PartialResponseNotAllowed {
        private QuestionMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseMQ response;

        @Parameterized.Parameter(1)
        public double expected;

        @Parameterized.Parameter(2)
        public Class<? extends Exception> expectedException;

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Before
        public void init() {
            question = new QuestionMQDomain();
            question.setQuestionId(1L);
            AnswerMQDomain answer0 = createAnswer(1000l, 2000l, 2001l, SAMPLES0);
            AnswerMQDomain answer1 = createAnswer(1001l, 2002l, 2003l, SAMPLES1);
            AnswerMQDomain answer2 = createAnswer(1002l, 2004l, 2005l, SAMPLES2);
            AnswerMQDomain answer3 = createAnswer(1003l, 2006l, 2007l, SAMPLES3);
            AnswerMQDomain answer4 = createAnswer(1004l, 2008l, 2009l, SAMPLES4);
            question.setAnswers(new HashSet(Arrays.asList(answer0, answer1, answer2, answer3, answer4)));
            question.setPartialResponseAllowed(true);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1} exception {2}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null, 0, NullPointerException.class},
                    {new ResponseMQ(1L, null), 0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(new ResponseMQ.Triple[]{}))), 0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2001l, 2000l),//-
                            new ResponseMQ.Triple(1001l, 2002l, 2001l),//-
                            new ResponseMQ.Triple(1002l, 2004l, 2009l),//-
                            new ResponseMQ.Triple(1003l, 2006l, 2005l),//-
                            new ResponseMQ.Triple(1004l, 2008l, 2007l)))),//-
                            0, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2001l),
                            new ResponseMQ.Triple(1001l, 2002l, 2001l),//-
                            new ResponseMQ.Triple(1002l, 2004l, 2009l),//-
                            new ResponseMQ.Triple(1003l, 2006l, 2005l),//-
                            new ResponseMQ.Triple(1004l, 2008l, 2007l)))),//-
                            20, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2003l),//-
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2009l),//-
                            new ResponseMQ.Triple(1004l, 2008l, 2007l)))),//-
                            40, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2003l),//-
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2007l),
                            new ResponseMQ.Triple(1004l, 2009l, 2008l)))),//-
                            60, null},
                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2001l),
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2007l),
                            new ResponseMQ.Triple(1004l, 2009l, 2008l)))),//-
                            80, null},

                    {new ResponseMQ(1L, new HashSet(Arrays.asList(
                            new ResponseMQ.Triple(1000l, 2000l, 2001l),
                            new ResponseMQ.Triple(1001l, 2002l, 2003l),
                            new ResponseMQ.Triple(1002l, 2004l, 2005l),
                            new ResponseMQ.Triple(1003l, 2006l, 2007l),
                            new ResponseMQ.Triple(1004l, 2008l, 2009l)))),
                            100, null},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() {
            //setup expected exception
            if (expectedException != null) {
                thrown.expect(expectedException);
            }
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    private static AnswerMQDomain createAnswer(Long answerId, Long leftPhraseId, Long rightPhraseId, String[] samples) {
        AnswerMQDomain answer = new AnswerMQDomain();
        answer.setAnswerId(answerId);

        PhraseDomain phraseDomain0Left = new PhraseDomain();
        phraseDomain0Left.setPhraseId(leftPhraseId);
        phraseDomain0Left.setPhrase(samples[0]);
        answer.setLeftPhraseDomain(phraseDomain0Left);

        PhraseDomain phraseDomain0Right = new PhraseDomain();
        phraseDomain0Right.setPhraseId(rightPhraseId);
        phraseDomain0Right.setPhrase(samples[1]);
        answer.setRightPhraseDomain(phraseDomain0Right);
        return answer;
    }
}
