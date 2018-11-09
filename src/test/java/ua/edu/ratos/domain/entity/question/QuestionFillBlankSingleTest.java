package ua.edu.ratos.domain.entity.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.response.ResponseFillBlankSingle;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionFillBlankSingleTest {

    private static final String[] SAMPLES = new String[] {"phrase1", "phrase#1", "phrase one"};

    private QuestionFillBlankSingle question;

    private AnswerFillBlankSingle answer;


    @Before
    public void init() {
        question = new QuestionFillBlankSingle();
        question.setQuestionId(1L);

        answer = new AnswerFillBlankSingle();

        Phrase phrase0 = new Phrase();
        phrase0.setPhrase(SAMPLES[0]);
        Phrase phrase1 = new Phrase();
        phrase1.setPhrase(SAMPLES[1]);
        Phrase phrase2 = new Phrase();
        phrase2.setPhrase(SAMPLES[2]);
        Phrase[] phrases = new Phrase[] {phrase0, phrase1, phrase2};
        answer.setAcceptedPhrases(new HashSet<>(Arrays.asList(phrases)));
        question.setAnswer(answer);
    }


    @Test
    public void evaluateBasicMatchTest() {
        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(false);
        answer.setSettings(settings);

        // Wrong
        ResponseFillBlankSingle response = new ResponseFillBlankSingle(1L, "string1");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFillBlankSingle(1L, "phrase1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "phrase#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "phrase one");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateCaseSensitivityMatchTest() {

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(false);
        answer.setSettings(settings);

        // Wrong
        ResponseFillBlankSingle response = new ResponseFillBlankSingle(1L, "PHRASE!");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFillBlankSingle(1L, "Phrase1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "PHRASE#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "phrase ONE");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateSingleTypoAndCaseSensitiveMatchTest() {

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(true);
        answer.setSettings(settings);

        // Wrong
        ResponseFillBlankSingle response = new ResponseFillBlankSingle(1L, "phrase");
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong
        response = new ResponseFillBlankSingle(1L, "PHRASE2");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFillBlankSingle(1L, "phrase2");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "phrasa#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "phrase-one");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateSingleTypoAndCaseInSensitiveMatchTest() {

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);
        answer.setSettings(settings);

        // Wrong
        ResponseFillBlankSingle response = new ResponseFillBlankSingle(1L, "PHRASE");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFillBlankSingle(1L, "PHRASE2");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "Phrasa#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFillBlankSingle(1L, "Phrase-One");
        Assert.assertEquals(100, question.evaluate(response));
    }
}
