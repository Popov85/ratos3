package ua.edu.ratos.service.domain.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.answer.AnswerSQDomain;
import ua.edu.ratos.service.domain.response.ResponseSQ;

import java.util.*;

@RunWith(JUnit4.class)
public class QuestionSQDomainTest {

    private QuestionSQDomain question;

    private Set<AnswerSQDomain> answers;

    @Before
    public void init() {
        question = new QuestionSQDomain();
        question.setQuestionId(1L);
        answers = new HashSet<>();
    }

    @Test
    public void evaluateTest() {

        answers.add(createAnswer(1000l,  (short) 0));
        answers.add(createAnswer(1001l,  (short) 1));
        answers.add(createAnswer(1002l,  (short) 2));
        answers.add(createAnswer(1003l,  (short) 3));
        answers.add(createAnswer(1004l,  (short) 4));

        question.setAnswers(answers);

        Long[] userResponses;
        ResponseSQ response;

        // Empty response
        userResponses = new Long[] {};
        response = new ResponseSQ(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong order
        userResponses = new Long[] {1004l, 1003l, 1002l, 1001l, 1000l};
        response = new ResponseSQ(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong response
        userResponses = new Long[] {2000l, 2001l, 2002l, 2003l, 2004l};
        response = new ResponseSQ(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Right (right order)
        userResponses = new Long[] {1000l, 1001l, 1002l, 1003l, 1004l};
        response = new ResponseSQ(1L, Arrays.asList(userResponses));
        Assert.assertEquals(100, question.evaluate(response));

    }


    private AnswerSQDomain createAnswer(Long answerId, short order) {
        AnswerSQDomain answer = new AnswerSQDomain();
        answer.setAnswerId(answerId);
        PhraseDomain phraseDomain = new PhraseDomain();
        answer.setPhraseDomain(phraseDomain);
        answer.setOrder(order);
        return answer;
    }
}
