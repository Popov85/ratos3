package ua.edu.ratos.domain.entity.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.service.dto.response.ResponseMatcher;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionMatcherTest {

    // Matcher
    private static final String[] SAMPLES0 = new String[] {"phrase0", "zero phrase"};
    private static final String[] SAMPLES1 = new String[] {"phrase1", "first phrase"};
    private static final String[] SAMPLES2 = new String[] {"phrase2", "second phrase"};
    private static final String[] SAMPLES3 = new String[] {"phrase3", "third phrase"};
    private static final String[] SAMPLES4 = new String[] {"phrase4", "fourth phrase"};

    private QuestionMatcher question;

    @Before
    public void init() {
        question = new QuestionMatcher();
        question.setQuestionId(1L);
        AnswerMatcher answer0 = createAnswer(1000l, 2000l, 2001l, SAMPLES0);
        AnswerMatcher answer1 = createAnswer(1001l, 2002l, 2003l, SAMPLES1);
        AnswerMatcher answer2 = createAnswer(1002l, 2004l, 2005l, SAMPLES2);
        AnswerMatcher answer3 = createAnswer(1003l, 2006l, 2007l, SAMPLES3);
        AnswerMatcher answer4 = createAnswer(1004l, 2008l, 2009l, SAMPLES4);
        question.setAnswers(Arrays.asList(answer0, answer1, answer2, answer3, answer4));
    }

    @Test
    public void evaluatePartialTest() {
        question.setPartialResponseAllowed(true);

        // Wrong (0)

        // Right
        ResponseMatcher.Triple triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2003l);
        // Right
        ResponseMatcher.Triple triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2001l);
        // Right
        ResponseMatcher.Triple triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2009l);
        // Wrong
        ResponseMatcher.Triple triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2005l);
        // Wrong
        ResponseMatcher.Triple triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2007l);

        ResponseMatcher response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Right (60)

        // Right
        triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2009l);
        // Wrong
        triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2007l);

        response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(60.00, question.evaluate(response), 0.01);

        // Right (100)

        // Right
        triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2007l);
        // Wrong
        triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2009l);

        response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(100.00, question.evaluate(response), 0.01);
    }


    @Test
    public void evaluatePartialNoAllowedTest() {
        question.setPartialResponseAllowed(false);

        // Wrong (0)

        // Right
        ResponseMatcher.Triple triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2003l);
        // Right
        ResponseMatcher.Triple triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2001l);
        // Right
        ResponseMatcher.Triple triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2009l);
        // Wrong
        ResponseMatcher.Triple triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2005l);
        // Wrong
        ResponseMatcher.Triple triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2007l);

        ResponseMatcher response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Wrong (60)

        // Right
        triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2009l);
        // Wrong
        triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2007l);

        response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Right (100)

        // Right
        triple0 = new ResponseMatcher.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMatcher.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMatcher.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMatcher.Triple(1003l, 2006l, 2007l);
        // Wrong
        triple4 = new ResponseMatcher.Triple(1004l, 2008l, 2009l);

        response = new ResponseMatcher(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(100.00, question.evaluate(response), 0.01);
    }

    private AnswerMatcher createAnswer(Long answerId, Long leftPhraseId, Long rightPhraseId, String[] samples) {
        AnswerMatcher answer = new AnswerMatcher();
        answer.setAnswerId(answerId);

        Phrase phrase0Left = new Phrase();
        phrase0Left.setPhraseId(leftPhraseId);
        phrase0Left.setPhrase(samples[0]);
        answer.setLeftPhrase(phrase0Left);

        Phrase phrase0Right = new Phrase();
        phrase0Right.setPhraseId(rightPhraseId);
        phrase0Right.setPhrase(samples[1]);
        answer.setRightPhrase(phrase0Right);
        return answer;
    }
}
