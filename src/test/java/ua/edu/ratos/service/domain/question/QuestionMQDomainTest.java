package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.answer.AnswerMQDomain;
import ua.edu.ratos.service.domain.response.ResponseMQ;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class QuestionMQDomainTest {

    // Matcher
    private static final String[] SAMPLES0 = new String[] {"phrase0", "zero phraseDomain"};
    private static final String[] SAMPLES1 = new String[] {"phrase1", "first phraseDomain"};
    private static final String[] SAMPLES2 = new String[] {"phrase2", "second phraseDomain"};
    private static final String[] SAMPLES3 = new String[] {"phrase3", "third phraseDomain"};
    private static final String[] SAMPLES4 = new String[] {"phrase4", "fourth phraseDomain"};

    private QuestionMQDomain question;

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
    }

    @Test
    public void evaluatePartialTest() {
        question.setPartialResponseAllowed(true);

        // Wrong (0)

        // Right
        ResponseMQ.Triple triple0 = new ResponseMQ.Triple(1000l, 2000l, 2003l);
        // Right
        ResponseMQ.Triple triple1 = new ResponseMQ.Triple(1001l, 2002l, 2001l);
        // Right
        ResponseMQ.Triple triple2 = new ResponseMQ.Triple(1002l, 2004l, 2009l);
        // Wrong
        ResponseMQ.Triple triple3 = new ResponseMQ.Triple(1003l, 2006l, 2005l);
        // Wrong
        ResponseMQ.Triple triple4 = new ResponseMQ.Triple(1004l, 2008l, 2007l);

        ResponseMQ response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Right (60)

        // Right
        triple0 = new ResponseMQ.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMQ.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMQ.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMQ.Triple(1003l, 2006l, 2009l);
        // Wrong
        triple4 = new ResponseMQ.Triple(1004l, 2008l, 2007l);

        response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(60.00, question.evaluate(response), 0.01);

        // Right (100)

        // Right
        triple0 = new ResponseMQ.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMQ.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMQ.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMQ.Triple(1003l, 2006l, 2007l);
        // Wrong
        triple4 = new ResponseMQ.Triple(1004l, 2008l, 2009l);

        response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(100.00, question.evaluate(response), 0.01);
    }


    @Test
    public void evaluatePartialNoAllowedTest() {
        question.setPartialResponseAllowed(false);

        // Wrong (0)

        // Right
        ResponseMQ.Triple triple0 = new ResponseMQ.Triple(1000l, 2000l, 2003l);
        // Right
        ResponseMQ.Triple triple1 = new ResponseMQ.Triple(1001l, 2002l, 2001l);
        // Right
        ResponseMQ.Triple triple2 = new ResponseMQ.Triple(1002l, 2004l, 2009l);
        // Wrong
        ResponseMQ.Triple triple3 = new ResponseMQ.Triple(1003l, 2006l, 2005l);
        // Wrong
        ResponseMQ.Triple triple4 = new ResponseMQ.Triple(1004l, 2008l, 2007l);

        ResponseMQ response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Wrong (60)

        // Right
        triple0 = new ResponseMQ.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMQ.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMQ.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMQ.Triple(1003l, 2006l, 2009l);
        // Wrong
        triple4 = new ResponseMQ.Triple(1004l, 2008l, 2007l);

        response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(0.00, question.evaluate(response), 0.01);

        // Right (100)

        // Right
        triple0 = new ResponseMQ.Triple(1000l, 2000l, 2001l);
        // Right
        triple1 = new ResponseMQ.Triple(1001l, 2002l, 2003l);
        // Right
        triple2 = new ResponseMQ.Triple(1002l, 2004l, 2005l);
        // Wrong
        triple3 = new ResponseMQ.Triple(1003l, 2006l, 2007l);
        // Wrong
        triple4 = new ResponseMQ.Triple(1004l, 2008l, 2009l);

        response = new ResponseMQ(1L, new HashSet<>(Arrays.asList(triple0, triple1, triple2, triple3, triple4)));

        Assert.assertEquals(100.00, question.evaluate(response), 0.01);
    }

    private AnswerMQDomain createAnswer(Long answerId, Long leftPhraseId, Long rightPhraseId, String[] samples) {
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
