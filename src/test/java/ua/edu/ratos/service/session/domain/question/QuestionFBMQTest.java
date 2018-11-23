package ua.edu.ratos.service.session.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.domain.SettingsFB;
import ua.edu.ratos.service.session.domain.answer.AnswerFBMQ;
import ua.edu.ratos.service.session.domain.response.ResponseFillBlankMultiple;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionFBMQTest {

    private static final String PHRASE0 = "first phrase";

    private static final String[] SAMPLES0 = new String[] {"phrase1", "phrase#1", "phrase one"};

    private static final String PHRASE1 = "second phrase";

    private static final String[] SAMPLES1 = new String[] {"phrase2", "phrase#2", "phrase two"};

    private static final String PHRASE2 = "third phrase";

    private static final String[] SAMPLES2 = new String[] {"phrase3", "phrase#3", "phrase three"};

    private QuestionFBMQ question;

    @Before
    public void init() {
        question = new QuestionFBMQ();
        question.setQuestionId(1L);

        AnswerFBMQ answer0 = createAnswer(1000l, PHRASE0, SAMPLES0);
        AnswerFBMQ answer1 = createAnswer(1001l, PHRASE1, SAMPLES1);
        AnswerFBMQ answer2 = createAnswer(1002l, PHRASE2, SAMPLES2);

        question.setAnswers(new HashSet<>(Arrays.asList(answer0, answer1, answer2)));
    }

    @Test
    public void evaluateBasicTest() {

        SettingsFB settings = new SettingsFB();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(false);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFillBlankMultiple.Pair pair0 = new ResponseFillBlankMultiple.Pair(1000l, "phrase");
        // Wrong
        ResponseFillBlankMultiple.Pair pair1 = new ResponseFillBlankMultiple.Pair(1001l, "phrase22");
        // Wrong
        ResponseFillBlankMultiple.Pair pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33phrase");

        ResponseFillBlankMultiple response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "phrase#1");
        // Wrong
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "phrase22");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "phrase#1");
        // True
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "phrase2");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "first phrase");
        // Right
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "phrase2");
        // Right
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "phrase three");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluateCaseInSensitiveTest() {

        SettingsFB settings = new SettingsFB();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(false);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFillBlankMultiple.Pair pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFillBlankMultiple.Pair pair1 = new ResponseFillBlankMultiple.Pair(1001l, "PHRASE22");
        // Wrong
        ResponseFillBlankMultiple.Pair pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33PHRASE");

        ResponseFillBlankMultiple response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE#1");
        // Wrong
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase22");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33Phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "Phrase#1");
        // True
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "PHRASE2");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "First Phrase");
        // Right
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase2");
        // Right
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "phrase THREE");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluateSingleTypoAndCaseInSensitiveTest() {

        SettingsFB settings = new SettingsFB();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);

        question.getAnswers().forEach(a->a.setSettings(settings));

        question.setPartialResponseAllowed(true);

        // All incorrect

        // Wrong
        ResponseFillBlankMultiple.Pair pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFillBlankMultiple.Pair pair1 = new ResponseFillBlankMultiple.Pair(1001l, "2PHRASE2");
        // Wrong
        ResponseFillBlankMultiple.Pair pair2 = new ResponseFillBlankMultiple.Pair(1002l, "3PHRASES");

        ResponseFillBlankMultiple response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASA#1");
        // Wrong
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase23");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33Phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(33.33, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE-1");
        // True
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "FHRASE2");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "3phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(66.66, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "First_Phrase");
        // Right
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase8");
        // Right
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "phrase-THREE");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    @Test
    public void evaluatePartialResponseNotAllowedTest() {

        SettingsFB settings = new SettingsFB();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);

        question.getAnswers().forEach(a->a.setSettings(settings));
        question.setPartialResponseAllowed(false);

        // All incorrect

        // Wrong
        ResponseFillBlankMultiple.Pair pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE");
        // Wrong
        ResponseFillBlankMultiple.Pair pair1 = new ResponseFillBlankMultiple.Pair(1001l, "2PHRASE2");
        // Wrong
        ResponseFillBlankMultiple.Pair pair2 = new ResponseFillBlankMultiple.Pair(1002l, "3PHRASES");

        ResponseFillBlankMultiple response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // One correct (33)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASA#1");
        // Wrong
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase23");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "33Phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);


        // Two correct (67)

        // True
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "PHRASE-1");
        // True
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "FHRASE2");
        // Wrong
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "3phrase");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(0.00, question.evaluate(response),0.01);

        // All correct

        // Right
        pair0 = new ResponseFillBlankMultiple.Pair(1000l, "First_Phrase");
        // Right
        pair1 = new ResponseFillBlankMultiple.Pair(1001l, "Phrase8");
        // Right
        pair2 = new ResponseFillBlankMultiple.Pair(1002l, "phrase-THREE");

        response = new ResponseFillBlankMultiple(1L, new HashSet<>(Arrays.asList(pair0, pair1, pair2)));

        Assert.assertEquals(100.00, question.evaluate(response),0.01);
    }

    private AnswerFBMQ createAnswer(final Long answerId, final String phrase, final String[] acceptedPhrases) {
        AnswerFBMQ answer = new AnswerFBMQ();
        answer.setAnswerId(answerId);
        answer.setPhrase(phrase);
        Phrase phrase0 = new Phrase();
        phrase0.setPhrase(acceptedPhrases[0]);
        Phrase phrase1 = new Phrase();
        phrase1.setPhrase(acceptedPhrases[1]);
        Phrase phrase2 = new Phrase();
        phrase2.setPhrase(acceptedPhrases[2]);
        answer.setAcceptedPhrases(new HashSet<>(Arrays.asList(phrase0, phrase1, phrase2)));
        return answer;
    }
}
