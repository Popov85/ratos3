package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBMQDomain;
import ua.edu.ratos.service.domain.response.ResponseFBMQ;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionFBMQDomainTest {

    private static final String PHRASE0 = "first phrase";

    private static final String[] SAMPLES0 = new String[] {"phrase1", "phrase#1", "phrase one"};

    private static final String PHRASE1 = "second phrase";

    private static final String[] SAMPLES1 = new String[] {"phrase2", "phrase#2", "phrase two"};

    private static final String PHRASE2 = "third phrase";

    private static final String[] SAMPLES2 = new String[] {"phrase3", "phrase#3", "phrase three"};

    private QuestionFBMQDomain question;

    @Before
    public void init() {
        question = new QuestionFBMQDomain();
        question.setQuestionId(1L);

        AnswerFBMQDomain answer0 = createAnswer(1000l, PHRASE0, SAMPLES0);
        AnswerFBMQDomain answer1 = createAnswer(1001l, PHRASE1, SAMPLES1);
        AnswerFBMQDomain answer2 = createAnswer(1002l, PHRASE2, SAMPLES2);

        question.setAnswers(new HashSet<>(Arrays.asList(answer0, answer1, answer2)));
    }

    @Test
    public void evaluateBasicTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(false);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFBMQ.Pair pair0 = new ResponseFBMQ.Pair(1000l, "phrase");
        // Wrong
        ResponseFBMQ.Pair pair1 = new ResponseFBMQ.Pair(1001l, "phrase22");
        // Wrong
        ResponseFBMQ.Pair pair2 = new ResponseFBMQ.Pair(1002l, "33phrase");

        ResponseFBMQ response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "phrase#1");
        // Wrong
        pair1 = new ResponseFBMQ.Pair(1001l, "phrase22");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "phrase#1");
        // True
        pair1 = new ResponseFBMQ.Pair(1001l, "phrase2");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFBMQ.Pair(1000l, "first phrase");
        // Right
        pair1 = new ResponseFBMQ.Pair(1001l, "phrase2");
        // Right
        pair2 = new ResponseFBMQ.Pair(1002l, "phrase three");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluateCaseInSensitiveTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(false);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFBMQ.Pair pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFBMQ.Pair pair1 = new ResponseFBMQ.Pair(1001l, "PHRASE22");
        // Wrong
        ResponseFBMQ.Pair pair2 = new ResponseFBMQ.Pair(1002l, "33PHRASE");

        ResponseFBMQ response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE#1");
        // Wrong
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase22");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33Phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "Phrase#1");
        // True
        pair1 = new ResponseFBMQ.Pair(1001l, "PHRASE2");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFBMQ.Pair(1000l, "First Phrase");
        // Right
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase2");
        // Right
        pair2 = new ResponseFBMQ.Pair(1002l, "phrase THREE");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluateSingleTypoAndCaseInSensitiveTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);

        question.getAnswers().forEach(a->a.setSettings(settings));

        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFBMQ.Pair pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFBMQ.Pair pair1 = new ResponseFBMQ.Pair(1001l, "2PHRASE2");
        // Wrong
        ResponseFBMQ.Pair pair2 = new ResponseFBMQ.Pair(1002l, "3PHRASES");

        ResponseFBMQ response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "PHRASA#1");
        // Wrong
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase23");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33Phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE-1");
        // True
        pair1 = new ResponseFBMQ.Pair(1001l, "FHRASE2");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "3phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFBMQ.Pair(1000l, "First_Phrase");
        // Right
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase8");
        // Right
        pair2 = new ResponseFBMQ.Pair(1002l, "phrase-THREE");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluatePartialResponseNotAllowedTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(false);

        // All incorrect

        // Wrong
        ResponseFBMQ.Pair pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFBMQ.Pair pair1 = new ResponseFBMQ.Pair(1001l, "2PHRASE2");
        // Wrong
        ResponseFBMQ.Pair pair2 = new ResponseFBMQ.Pair(1002l, "3PHRASES");

        ResponseFBMQ response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "PHRASA#1");
        // Wrong
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase23");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "33Phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFBMQ.Pair(1000l, "PHRASE-1");
        // True
        pair1 = new ResponseFBMQ.Pair(1001l, "FHRASE2");
        // Wrong
        pair2 = new ResponseFBMQ.Pair(1002l, "3phrase");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFBMQ.Pair(1000l, "First_Phrase");
        // Right
        pair1 = new ResponseFBMQ.Pair(1001l, "Phrase8");
        // Right
        pair2 = new ResponseFBMQ.Pair(1002l, "phrase-THREE");

        response = new ResponseFBMQ(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    private AnswerFBMQDomain createAnswer(final Long answerId, final String phrase, final String[] acceptedPhrases) {
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
