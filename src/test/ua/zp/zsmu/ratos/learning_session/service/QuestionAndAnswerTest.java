package ua.zp.zsmu.ratos.learning_session.service;

import org.junit.Test;
import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrey on 4/17/2017.
 */

public class QuestionAndAnswerTest {

        @Test
        public void calculateResultTestOneRightRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 0, false));
                answers.add(createAnswer(1001l, "Title1", 0, false));
                answers.add(createAnswer(1002l, "Title2", 100, true));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All wrong
                userAnswers = new Long[] { 1000l, 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong
                userAnswers = new Long[] { 1000l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong, one right
                userAnswers = new Long[] { 1000l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // Right
                userAnswers = new Long[] { 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestOneRightNotRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 0, false));
                answers.add(createAnswer(1001l, "Title1", 0, false));
                answers.add(createAnswer(1002l, "Title2", 100, false));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All wrong
                userAnswers = new Long[] { 1000l, 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong
                userAnswers = new Long[] { 1000l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong, one right
                userAnswers = new Long[] { 1000l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // Right
                userAnswers = new Long[] { 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestTwoRightRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 50, true));
                answers.add(createAnswer(1001l, "Title1", 0, false));
                answers.add(createAnswer(1002l, "Title2", 50, true));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All wrong
                userAnswers = new Long[] { 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong, one right, without required
                userAnswers = new Long[] { 1002l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One right without one required
                userAnswers = new Long[] { 1000l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All right
                userAnswers = new Long[] { 1000l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestTwoRightNotRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 50, false));
                answers.add(createAnswer(1001l, "Title1", 0, false));
                answers.add(createAnswer(1002l, "Title2", 50, false));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l,1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All wrong
                userAnswers = new Long[] { 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong, one right
                userAnswers = new Long[] { 1002l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One right
                userAnswers = new Long[] { 1000l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(50, questionAndAnswer.calculateResult());

                // All right
                userAnswers = new Long[] { 1000l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestTwoRightOneRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 50, false));
                answers.add(createAnswer(1001l, "Title1", 0, false));
                answers.add(createAnswer(1002l, "Title2", 50, true));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All wrong
                userAnswers = new Long[] { 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());


                // One wrong, one right (not required)
                userAnswers = new Long[] { 1000l, 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One wrong, one right (required)
                userAnswers = new Long[] { 1001l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One right (not required)
                userAnswers = new Long[] { 1000l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One right (required)
                userAnswers = new Long[] { 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(50, questionAndAnswer.calculateResult());

                // All right
                userAnswers = new Long[] { 1000l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());
        }

        @Test
        public void calculateResultTestAllTrueRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 25, true));
                answers.add(createAnswer(1001l, "Title1", 25, true));
                answers.add(createAnswer(1002l, "Title2", 25, true));
                answers.add(createAnswer(1003l, "Title3", 25, true));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

                // One true without many required
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // Two true without many required
                userAnswers = new Long[] { 1000l, 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // Three true without one required
                userAnswers = new Long[] { 1000l, 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestAllTrueNotRequired() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 25, false));
                answers.add(createAnswer(1001l, "Title1", 25, false));
                answers.add(createAnswer(1002l, "Title2", 25, false));
                answers.add(createAnswer(1003l, "Title3", 25, false));
                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

                // One true without many not required
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(25, questionAndAnswer.calculateResult());

                // Two true without many not required
                userAnswers = new Long[] { 1000l, 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(50, questionAndAnswer.calculateResult());

                // Three true without one not required
                userAnswers = new Long[] { 1000l, 1001l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(75, questionAndAnswer.calculateResult());

        }

        @Test
        public void calculateResultTestManyAnswers() {

                QuestionAndAnswer questionAndAnswer;

                Question question = new Question();

                List<Answer> answers = new ArrayList<>();
                answers.add(createAnswer(1000l, "Title0", 0, false));
                answers.add(createAnswer(1001l, "Title1", 33, true));
                answers.add(createAnswer(1002l, "Title2", 33, true));
                answers.add(createAnswer(1003l, "Title3", 0, false));
                answers.add(createAnswer(1004l, "Title4", 34, false));

                question.setAnswers(answers);

                // Empty list
                Long[] userAnswers = new Long[] {};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All answers
                userAnswers = new Long[] { 1000l, 1001l, 1002l, 1003l, 1004l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // One true without all required
                userAnswers = new Long[] { 1004l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All not true
                userAnswers = new Long[] { 1000l, 1003l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

                // All true
                userAnswers = new Long[] { 1001l, 1002l, 1004l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(100, questionAndAnswer.calculateResult());

                // All true required without one not required
                userAnswers = new Long[] { 1001l, 1002l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(66, questionAndAnswer.calculateResult());

                // One true required without one true required
                userAnswers = new Long[] { 1001l};
                questionAndAnswer = new QuestionAndAnswer(question, Arrays.asList(userAnswers));
                assertEquals(0, questionAndAnswer.calculateResult());

        }

        private Answer createAnswer(Long id, String title, int percentage, boolean isRequired) {
                Answer answer = new Answer();
                answer.setId(id);
                answer.setTitle(title);
                answer.setPercentage(percentage);
                answer.setRequired(isRequired);
                return answer;
        }

}
