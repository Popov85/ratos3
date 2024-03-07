package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBMQDomain;
import ua.edu.ratos.service.domain.response.ResponseFBMQ;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@RunWith(Enclosed.class)
public class QuestionFBMQDomainParameterizedTest {

    private static final String PHRASE0 = "first phrase";
    private static final String[] ACCEPTED_PHRASES0 = new String[] {"phrase1", "phrase#1", "phrase one"};
    private static final String PHRASE1 = "second phrase";
    private static final String[] ACCEPTED_PHRASES1 = new String[] {"phrase2", "phrase#2", "phrase two"};
    private static final String PHRASE2 = "third phrase";
    private static final String[] ACCEPTED_PHRASES2 = new String[] {"phrase3", "phrase#3", "phrase three"};


    @RunWith(Parameterized.class)
    public static class CaseSensitiveNoTyposAllowedPartialResponseNotAllowed {

        private QuestionFBMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBMQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionFBMQDomain();
            question.setQuestionId(1L);

            question.setAnswers(new HashSet<>(Arrays.asList(
                    createAnswer(1000l, PHRASE0, ACCEPTED_PHRASES0),
                    createAnswer(1001l, PHRASE1, ACCEPTED_PHRASES1),
                    createAnswer(1002l, PHRASE2, ACCEPTED_PHRASES2))));

            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(true);
            settings.setTypoAllowed(false);

            question.getAnswers().forEach(a->a.setSettings(settings));
            question.setPartialResponseAllowed(false);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBMQ(1L, null), 0},
                    {new ResponseFBMQ(1L, new HashSet<>()), 0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase22"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase22"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "Phrase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "prase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "phrase4")))),//-
                            0},

                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "phrase#3")))),
                            100},

            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }


    @RunWith(Parameterized.class)
    public static class CaseSensitiveNoTyposAllowedPartialResponseAllowed {

        private QuestionFBMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBMQ response;

        @Parameterized.Parameter(1)
        public double expected;

        @Before
        public void setUp() {
            question = new QuestionFBMQDomain();
            question.setQuestionId(1L);

            question.setAnswers(new HashSet<>(Arrays.asList(
                    createAnswer(1000l, PHRASE0, ACCEPTED_PHRASES0),
                    createAnswer(1001l, PHRASE1, ACCEPTED_PHRASES1),
                    createAnswer(1002l, PHRASE2, ACCEPTED_PHRASES2))));

            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(true);
            settings.setTypoAllowed(false);

            question.getAnswers().forEach(a->a.setSettings(settings));
            question.setPartialResponseAllowed(true);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBMQ(1L, null), 0},
                    {new ResponseFBMQ(1L, new HashSet<>()), 0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase22"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "1phrase1"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase wo"),//-
                            new ResponseFBMQ.Pair(1002l, "Phrase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase100"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase tw1"),//-
                            new ResponseFBMQ.Pair(1002l, "prase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase"),//-
                            new ResponseFBMQ.Pair(1002l, "phrase4")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase22"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            33.33d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            66.66d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "phrase#3")))),
                            100},

            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class CaseSensitiveTyposAllowedPartialResponseAllowed {

        private QuestionFBMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBMQ response;

        @Parameterized.Parameter(1)
        public double expected;

        @Before
        public void setUp() {
            question = new QuestionFBMQDomain();
            question.setQuestionId(1L);

            question.setAnswers(new HashSet<>(Arrays.asList(
                    createAnswer(1000l, PHRASE0, ACCEPTED_PHRASES0),
                    createAnswer(1001l, PHRASE1, ACCEPTED_PHRASES1),
                    createAnswer(1002l, PHRASE2, ACCEPTED_PHRASES2))));

            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(true);
            settings.setTypoAllowed(true);

            question.getAnswers().forEach(a->a.setSettings(settings));
            question.setPartialResponseAllowed(true);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBMQ(1L, null), 0},
                    {new ResponseFBMQ(1L, new HashSet<>()), 0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "PHRASE ONE"),//-
                            new ResponseFBMQ.Pair(1001l, "Phrase22"),//-
                            new ResponseFBMQ.Pair(1002l, "33Phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "1phrase1"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase wow"),//-
                            new ResponseFBMQ.Pair(1002l, "PHrase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "Phrase100"),//-
                            new ResponseFBMQ.Pair(1001l, "Phrase tw1"),//-
                            new ResponseFBMQ.Pair(1002l, "Prase3")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "PHRASE"),//-
                            new ResponseFBMQ.Pair(1001l, "Phrase"),//-
                            new ResponseFBMQ.Pair(1002l, "pHrase4")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "prase1"), // typo
                            new ResponseFBMQ.Pair(1001l, "Phrase 22"),//-
                            new ResponseFBMQ.Pair(1002l, "33 phrase")))),//-
                            33.33d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase!"), // typo
                            new ResponseFBMQ.Pair(1001l, "phras two"), // typo
                            new ResponseFBMQ.Pair(1002l, "33 phrase")))),//-
                            66.66d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phra$e1"),// typo
                            new ResponseFBMQ.Pair(1001l, "phrase tw0"),//typo
                            new ResponseFBMQ.Pair(1002l, "phrase_3")))),// typo
                            100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class NonCaseSensitiveTyposAllowedPartialResponseAllowed {

        private QuestionFBMQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBMQ response;

        @Parameterized.Parameter(1)
        public double expected;

        @Before
        public void setUp() {
            question = new QuestionFBMQDomain();
            question.setQuestionId(1L);

            question.setAnswers(new HashSet<>(Arrays.asList(
                    createAnswer(1000l, PHRASE0, ACCEPTED_PHRASES0),
                    createAnswer(1001l, PHRASE1, ACCEPTED_PHRASES1),
                    createAnswer(1002l, PHRASE2, ACCEPTED_PHRASES2))));

            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(false);
            settings.setTypoAllowed(true);

            question.getAnswers().forEach(a->a.setSettings(settings));
            question.setPartialResponseAllowed(true);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBMQ(1L, null), 0},
                    {new ResponseFBMQ(1L, new HashSet<>()), 0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase zero"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase 22"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "1phrase1"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase 22"),//-
                            new ResponseFBMQ.Pair(1002l, "Phrase 33")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase100"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase tw11"),//-
                            new ResponseFBMQ.Pair(1002l, "prase33")))),//-
                            0},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "1-st phrase"),//-
                            new ResponseFBMQ.Pair(1001l, "phrase 1 ONE"),//-
                            new ResponseFBMQ.Pair(1002l, "PHRASE4")))),
                            33.33},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase222"),//-
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            33.33d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "phrase two"),
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            66.66d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "PRASE1"),
                            new ResponseFBMQ.Pair(1001l, "phrase TWO"),
                            new ResponseFBMQ.Pair(1002l, "33phrase")))),//-
                            66.66d},
                    {new ResponseFBMQ(1L,new HashSet<>(Arrays.asList(
                            new ResponseFBMQ.Pair(1000l, "phrase1"),
                            new ResponseFBMQ.Pair(1001l, "PHRASE two"),
                            new ResponseFBMQ.Pair(1002l, "pHrase#3")))),
                            100},

            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    private static AnswerFBMQDomain createAnswer(final Long answerId, final String phrase, final String[] acceptedPhrases) {
        AnswerFBMQDomain answer = new AnswerFBMQDomain();
        answer.setAnswerId(answerId);
        answer.setPhrase(phrase);
        PhraseDomain phraseDomain0 = new PhraseDomain();
        phraseDomain0.setPhrase(acceptedPhrases[0]);
        PhraseDomain phraseDomain1 = new PhraseDomain();
        phraseDomain1.setPhrase(acceptedPhrases[1]);
        PhraseDomain phraseDomain2 = new PhraseDomain();
        phraseDomain2.setPhrase(acceptedPhrases[2]);
        answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomain0, phraseDomain1, phraseDomain2)));
        return answer;
    }
}
