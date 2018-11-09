package ua.edu.ratos.domain.entity.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.service.dto.response.ResponseSequence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class QuestionSequenceTest {

    private QuestionSequence question;

    private List<AnswerSequence> answers;

    @Before
    public void init() {
        question = new QuestionSequence();
        question.setQuestionId(1L);
        answers = new ArrayList<>();
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
        ResponseSequence response;

        // Empty response
        userResponses = new Long[] {};
        response = new ResponseSequence(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong order
        userResponses = new Long[] {1004l, 1003l, 1002l, 1001l, 1000l};
        response = new ResponseSequence(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Wrong response
        userResponses = new Long[] {2000l, 2001l, 2002l, 2003l, 2004l};
        response = new ResponseSequence(1L, Arrays.asList(userResponses));
        Assert.assertEquals(0, question.evaluate(response));

        // Right (right order)
        userResponses = new Long[] {1000l, 1001l, 1002l, 1003l, 1004l};
        response = new ResponseSequence(1L, Arrays.asList(userResponses));
        Assert.assertEquals(100, question.evaluate(response));

    }


    private AnswerSequence createAnswer(Long answerId, short order) {
        AnswerSequence answer = new AnswerSequence();
        answer.setAnswerId(answerId);
        Phrase phrase = new Phrase();
        answer.setPhrase(phrase);
        answer.setOrder(order);
        return answer;
    }
}
