package ua.edu.ratos.service.sequence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.question.QuestionMCQDomain;
import ua.edu.ratos.service.session.sequence.LevelPartProducer;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class LevelPartProducerTest {

    @Autowired
    private LevelPartProducer levelPartProducer;

    @TestConfiguration
    static class LevelPartProducerTestContextConfiguration {

        @Bean
        public CollectionShuffler collectionShuffler() {
            return new CollectionShuffler();
        }

        @Bean
        public LevelPartProducer levelPartProducer() {
            return new LevelPartProducer();
        }
    }

    @Test
    public void getLevelPartRequiredMinorityTest() {
        // Given total 10 questions of some type and level, 2 are required, requested 5
        Set<Question> set = new HashSet<>();

        QuestionMCQ q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQ q2 = createOne(2L, (byte) 1, "MCQ #2", false);
        QuestionMCQ q3 = createOne(3L, (byte) 1, "MCQ #3", false);
        QuestionMCQ q4 = createOne(4L, (byte) 1, "MCQ #4", false);
        QuestionMCQ q5 = createOne(5L, (byte) 1, "MCQ #5", false);
        QuestionMCQ q6 = createOne(6L, (byte) 1, "MCQ #6", false);
        QuestionMCQ q7 = createOne(7L, (byte) 1, "MCQ #7", false);
        QuestionMCQ q8 = createOne(8L, (byte) 1, "MCQ #8", false);
        QuestionMCQ q9 = createOne(9L, (byte) 1, "MCQ #9", false);
        QuestionMCQ q10 = createOne(10L, (byte) 1, "MCQ #10", true);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<Question> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<Question> required = result.stream().filter(Question::isRequired).collect(Collectors.toList());

        assertEquals(2, required.size());

        List<String> actual = required.stream().map(Question::getQuestion).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ #1", "MCQ #10"));
    }

    @Test
    public void getLevelPartRequiredMajorityTest() {
        // Given total 10 questions of some type and level, 9 (majority) are required, requested 5
        Set<Question> set = new HashSet<>();
        QuestionMCQ q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQ q2 = createOne(2L, (byte) 1, "MCQ #2", true);
        QuestionMCQ q3 = createOne(3L, (byte) 1, "MCQ #3", true);
        QuestionMCQ q4 = createOne(4L, (byte) 1, "MCQ #4", true);
        QuestionMCQ q5 = createOne(5L, (byte) 1, "MCQ #5", true);
        QuestionMCQ q6 = createOne(6L, (byte) 1, "MCQ #6", true);
        QuestionMCQ q7 = createOne(7L, (byte) 1, "MCQ #7", true);
        QuestionMCQ q8 = createOne(8L, (byte) 1, "MCQ #8", true);
        QuestionMCQ q9 = createOne(9L, (byte) 1, "MCQ #9", true);
        QuestionMCQ q10 = createOne(10L, (byte) 1, "MCQ #10", false);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<Question> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<Question> required = result.stream().filter(Question::isRequired).collect(Collectors.toList());

        assertEquals(5, required.size());

        List<String> actual = required.stream().map(Question::getQuestion).collect(Collectors.toList());

        assertThat(actual, not(hasItem("MCQ #10")));
    }

    @Test
    public void getLevelPartRequiredAllTest() {
        // Given total 10 questions of some type and level, 5 (all requested) are required, requested 5
        Set<Question> set = new HashSet<>();
        QuestionMCQ q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQ q2 = createOne(2L, (byte) 1, "MCQ #2", true);
        QuestionMCQ q3 = createOne(3L, (byte) 1, "MCQ #3", false);
        QuestionMCQ q4 = createOne(4L, (byte) 1, "MCQ #4", false);
        QuestionMCQ q5 = createOne(5L, (byte) 1, "MCQ #5", false);
        QuestionMCQ q6 = createOne(6L, (byte) 1, "MCQ #6", false);
        QuestionMCQ q7 = createOne(7L, (byte) 1, "MCQ #7", false);
        QuestionMCQ q8 = createOne(8L, (byte) 1, "MCQ #8", true);
        QuestionMCQ q9 = createOne(9L, (byte) 1, "MCQ #9", true);
        QuestionMCQ q10 = createOne(10L, (byte) 1, "MCQ #10", true);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<Question> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<Question> required = result.stream().filter(Question::isRequired).collect(Collectors.toList());

        assertEquals(5, required.size());

        List<String> actual = required.stream().map(Question::getQuestion).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ #1", "MCQ #2", "MCQ #8", "MCQ #9", "MCQ #10"));
    }

    private QuestionMCQ createOne(Long id, byte level, String question, boolean required) {
        QuestionMCQ q = new QuestionMCQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        QuestionType type = new QuestionType();
        type.setTypeId(1L);
        q.setType(type);
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }

}
