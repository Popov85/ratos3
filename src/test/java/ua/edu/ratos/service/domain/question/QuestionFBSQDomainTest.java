package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBSQDomain;
import ua.edu.ratos.service.domain.response.ResponseFBSQ;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionFBSQDomainTest {

    private static final String[] SAMPLES = new String[] {"phrase1", "phrase#1", "phrase one"};

    private QuestionFBSQDomain question;

    private AnswerFBSQDomain answer;


    @Before
    public void init() {
        question = new QuestionFBSQDomain();
        question.setQuestionId(1L);

        answer = new AnswerFBSQDomain();

        PhraseDomain phraseDomain0 = new PhraseDomain();
        phraseDomain0.setPhrase(SAMPLES[0]);
        PhraseDomain phraseDomain1 = new PhraseDomain();
        phraseDomain1.setPhrase(SAMPLES[1]);
        PhraseDomain phraseDomain2 = new PhraseDomain();
        phraseDomain2.setPhrase(SAMPLES[2]);
        PhraseDomain[] phraseDomains = new PhraseDomain[] {phraseDomain0, phraseDomain1, phraseDomain2};
        answer.setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(phraseDomains)));
        question.setAnswer(answer);
    }


    @Test
    public void evaluateBasicMatchTest() {
        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(false);
        answer.setSettings(settings);

        // Wrong
        ResponseFBSQ response = new ResponseFBSQ(1L, "string1");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFBSQ(1L, "phrase1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "phrase#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "phrase one");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateCaseSensitivityMatchTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(false);
        answer.setSettings(settings);

        // Wrong
        ResponseFBSQ response = new ResponseFBSQ(1L, "PHRASE!");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFBSQ(1L, "Phrase1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "PHRASE#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "phrase ONE");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateSingleTypoAndCaseSensitiveMatchTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(true);
        settings.setTypoAllowed(true);
        answer.setSettings(settings);

        // Wrong
        ResponseFBSQ response = new ResponseFBSQ(1L, "phrase");
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong
        response = new ResponseFBSQ(1L, "PHRASE2");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFBSQ(1L, "phrase2");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "phrasa#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "phrase-one");
        Assert.assertEquals(100, question.evaluate(response));
    }

    @Test
    public void evaluateSingleTypoAndCaseInSensitiveMatchTest() {

        SettingsFBDomain settings = new SettingsFBDomain();
        settings.setCaseSensitive(false);
        settings.setTypoAllowed(true);
        answer.setSettings(settings);

        // Wrong
        ResponseFBSQ response = new ResponseFBSQ(1L, "PHRASE");
        Assert.assertEquals(0, question.evaluate(response));

        // Right
        response = new ResponseFBSQ(1L, "PHRASE2");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "Phrasa#1");
        Assert.assertEquals(100, question.evaluate(response));

        // Right another match
        response = new ResponseFBSQ(1L, "Phrase-One");
        Assert.assertEquals(100, question.evaluate(response));
    }
}
