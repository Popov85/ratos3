package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBSQDomain;
import ua.edu.ratos.service.domain.response.ResponseFBSQ;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@RunWith(Enclosed.class)
public class QuestionFBSQDomainParameterizedTest {

    private static final String[] ACCEPTED_PHRASES = new String[] {"phrase1", "phrase#1", "phrase one"};


    @RunWith(Parameterized.class)
    public static class CaseSensitiveNoTyposAllowed {

        private QuestionFBSQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBSQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionFBSQDomain();
            question.setQuestionId(1L);
            AnswerFBSQDomain answer = new AnswerFBSQDomain();
            PhraseDomain phraseDomain0 = new PhraseDomain();
            phraseDomain0.setPhrase(ACCEPTED_PHRASES[0]);
            PhraseDomain phraseDomain1 = new PhraseDomain();
            phraseDomain1.setPhrase(ACCEPTED_PHRASES[1]);
            PhraseDomain phraseDomain2 = new PhraseDomain();
            phraseDomain2.setPhrase(ACCEPTED_PHRASES[2]);
            PhraseDomain[] phraseDomains = new PhraseDomain[] {phraseDomain0, phraseDomain1, phraseDomain2};
            answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomains)));
            question.setAnswer(answer);
            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(true);
            settings.setTypoAllowed(false);
            answer.setSettings(settings);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBSQ(1L, null), 0},
                    {new ResponseFBSQ(1L, ""), 0},
                    {new ResponseFBSQ(1L, "000"), 0},
                    {new ResponseFBSQ(1L, "string1"), 0},
                    {new ResponseFBSQ(1L, "phrase 1"), 0},
                    {new ResponseFBSQ(1L, "phrase1 "), 100},
                    {new ResponseFBSQ(1L, " phrase1 "), 100},
                    {new ResponseFBSQ(1L, "phrase1"), 100},
                    {new ResponseFBSQ(1L, "phrase#1"), 100},
                    {new ResponseFBSQ(1L, "phrase one"), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class NonCaseSensitiveNoTyposAllowed {

        private QuestionFBSQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBSQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionFBSQDomain();
            question.setQuestionId(1L);
            AnswerFBSQDomain answer = new AnswerFBSQDomain();
            PhraseDomain phraseDomain0 = new PhraseDomain();
            phraseDomain0.setPhrase(ACCEPTED_PHRASES[0]);
            PhraseDomain phraseDomain1 = new PhraseDomain();
            phraseDomain1.setPhrase(ACCEPTED_PHRASES[1]);
            PhraseDomain phraseDomain2 = new PhraseDomain();
            phraseDomain2.setPhrase(ACCEPTED_PHRASES[2]);
            PhraseDomain[] phraseDomains = new PhraseDomain[] {phraseDomain0, phraseDomain1, phraseDomain2};
            answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomains)));
            question.setAnswer(answer);
            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(false);
            settings.setTypoAllowed(false);
            answer.setSettings(settings);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBSQ(1L, null), 0},
                    {new ResponseFBSQ(1L, ""), 0},
                    {new ResponseFBSQ(1L, "000"), 0},
                    {new ResponseFBSQ(1L, "string1"), 0},
                    {new ResponseFBSQ(1L, "phrase 1"), 0},
                    {new ResponseFBSQ(1L, "phrase1 "), 100},
                    {new ResponseFBSQ(1L, " phrase1 "), 100},
                    {new ResponseFBSQ(1L, "phrase1"), 100},
                    {new ResponseFBSQ(1L, "phrase#1"), 100},
                    {new ResponseFBSQ(1L, "phrase one"), 100},
                    {new ResponseFBSQ(1L, "Phrase one"), 100},
                    {new ResponseFBSQ(1L, "Phrase One"), 100},
                    {new ResponseFBSQ(1L, "PHRASE one"), 100},
                    {new ResponseFBSQ(1L, "Phrase ONE"), 100},
                    {new ResponseFBSQ(1L, "PHRASE ONE"), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class CaseSensitiveTyposAllowed {

        private QuestionFBSQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBSQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionFBSQDomain();
            question.setQuestionId(1L);
            AnswerFBSQDomain answer = new AnswerFBSQDomain();
            PhraseDomain phraseDomain0 = new PhraseDomain();
            phraseDomain0.setPhrase(ACCEPTED_PHRASES[0]);
            PhraseDomain phraseDomain1 = new PhraseDomain();
            phraseDomain1.setPhrase(ACCEPTED_PHRASES[1]);
            PhraseDomain phraseDomain2 = new PhraseDomain();
            phraseDomain2.setPhrase(ACCEPTED_PHRASES[2]);
            PhraseDomain[] phraseDomains = new PhraseDomain[] {phraseDomain0, phraseDomain1, phraseDomain2};
            answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomains)));
            question.setAnswer(answer);
            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(true);
            settings.setTypoAllowed(true);
            answer.setSettings(settings);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBSQ(1L, null), 0},
                    {new ResponseFBSQ(1L, ""), 0},
                    {new ResponseFBSQ(1L, "000"), 0},
                    {new ResponseFBSQ(1L, "string1"), 0},
                    {new ResponseFBSQ(1L, "phrase1 "), 100},
                    {new ResponseFBSQ(1L, " phrase1 "), 100},
                    {new ResponseFBSQ(1L, "phrase1"), 100},
                    {new ResponseFBSQ(1L, "phrase#1"), 100},
                    {new ResponseFBSQ(1L, "phrase one"), 100},
                    {new ResponseFBSQ(1L, "phraseone"), 100},
                    {new ResponseFBSQ(1L, "phrase on1"), 100},
                    {new ResponseFBSQ(1L, "prase one"), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

    @RunWith(Parameterized.class)
    public static class NonCaseSensitiveTyposAllowed {

        private QuestionFBSQDomain question;

        @Parameterized.Parameter(0)
        public ResponseFBSQ response;

        @Parameterized.Parameter(1)
        public int expected;

        @Before
        public void setUp() {
            question = new QuestionFBSQDomain();
            question.setQuestionId(1L);
            AnswerFBSQDomain answer = new AnswerFBSQDomain();
            PhraseDomain phraseDomain0 = new PhraseDomain();
            phraseDomain0.setPhrase(ACCEPTED_PHRASES[0]);
            PhraseDomain phraseDomain1 = new PhraseDomain();
            phraseDomain1.setPhrase(ACCEPTED_PHRASES[1]);
            PhraseDomain phraseDomain2 = new PhraseDomain();
            phraseDomain2.setPhrase(ACCEPTED_PHRASES[2]);
            PhraseDomain[] phraseDomains = new PhraseDomain[] {phraseDomain0, phraseDomain1, phraseDomain2};
            answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomains)));
            question.setAnswer(answer);
            SettingsFBDomain settings = new SettingsFBDomain();
            settings.setCaseSensitive(false);
            settings.setTypoAllowed(true);
            answer.setSettings(settings);
        }

        @Parameterized.Parameters(name = "{index}: evaluateTest({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new ResponseFBSQ(1L, null), 0},
                    {new ResponseFBSQ(1L, ""), 0},
                    {new ResponseFBSQ(1L, "000"), 0},
                    {new ResponseFBSQ(1L, "string1"), 0},
                    {new ResponseFBSQ(1L, "phrase1 "), 100},
                    {new ResponseFBSQ(1L, " phrase1 "), 100},
                    {new ResponseFBSQ(1L, "phrase1"), 100},
                    {new ResponseFBSQ(1L, "phrase#1"), 100},
                    {new ResponseFBSQ(1L, "phrase one"), 100},
                    {new ResponseFBSQ(1L, "phraseone"), 100},
                    {new ResponseFBSQ(1L, "phrase on1"), 100},
                    {new ResponseFBSQ(1L, "prase one"), 100},
                    {new ResponseFBSQ(1L, "Phraseone"), 100},
                    {new ResponseFBSQ(1L, "phrase ON1"), 100},
                    {new ResponseFBSQ(1L, "PRASE ONE"), 100},
            });
        }

        @Test(timeout = 1000L)
        public void evaluateTest() throws Exception {
            Assert.assertEquals("Calculated score is not equal to the expected", expected, question.evaluate(response), 0.01);
        }
    }

}
