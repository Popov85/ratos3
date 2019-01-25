package ua.edu.ratos.service.sequence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
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
        Set<QuestionMCQDomain> set = new HashSet<>();
        QuestionMCQDomain q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQDomain q2 = createOne(2L, (byte) 1, "MCQ #2", false);
        QuestionMCQDomain q3 = createOne(3L, (byte) 1, "MCQ #3", false);
        QuestionMCQDomain q4 = createOne(4L, (byte) 1, "MCQ #4", false);
        QuestionMCQDomain q5 = createOne(5L, (byte) 1, "MCQ #5", false);
        QuestionMCQDomain q6 = createOne(6L, (byte) 1, "MCQ #6", false);
        QuestionMCQDomain q7 = createOne(7L, (byte) 1, "MCQ #7", false);
        QuestionMCQDomain q8 = createOne(8L, (byte) 1, "MCQ #8", false);
        QuestionMCQDomain q9 = createOne(9L, (byte) 1, "MCQ #9", false);
        QuestionMCQDomain q10 = createOne(10L, (byte) 1, "MCQ #10", true);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<QuestionDomain> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<QuestionDomain> required = result.stream().filter(QuestionDomain::isRequired).collect(Collectors.toList());

        assertEquals(2, required.size());

        List<String> actual = required.stream().map(QuestionDomain::getQuestion).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ #1", "MCQ #10"));
    }

    @Test
    public void getLevelPartRequiredMajorityTest() {
        // Given total 10 questions of some type and level, 9 (majority) are required, requested 5
        Set<QuestionMCQDomain> set = new HashSet<>();
        QuestionMCQDomain q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQDomain q2 = createOne(2L, (byte) 1, "MCQ #2", true);
        QuestionMCQDomain q3 = createOne(3L, (byte) 1, "MCQ #3", true);
        QuestionMCQDomain q4 = createOne(4L, (byte) 1, "MCQ #4", true);
        QuestionMCQDomain q5 = createOne(5L, (byte) 1, "MCQ #5", true);
        QuestionMCQDomain q6 = createOne(6L, (byte) 1, "MCQ #6", true);
        QuestionMCQDomain q7 = createOne(7L, (byte) 1, "MCQ #7", true);
        QuestionMCQDomain q8 = createOne(8L, (byte) 1, "MCQ #8", true);
        QuestionMCQDomain q9 = createOne(9L, (byte) 1, "MCQ #9", true);
        QuestionMCQDomain q10 = createOne(10L, (byte) 1, "MCQ #10", false);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<QuestionDomain> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<QuestionDomain> required = result.stream().filter(QuestionDomain::isRequired).collect(Collectors.toList());

        assertEquals(5, required.size());

        List<String> actual = required.stream().map(QuestionDomain::getQuestion).collect(Collectors.toList());

        assertThat(actual, not(hasItem("MCQ #10")));
    }

    @Test
    public void getLevelPartRequiredAllTest() {
        // Given total 10 questions of some type and level, 5 (all requested) are required, requested 5
        Set<QuestionMCQDomain> set = new HashSet<>();
        QuestionMCQDomain q1 = createOne(1L, (byte) 1, "MCQ #1", true);
        QuestionMCQDomain q2 = createOne(2L, (byte) 1, "MCQ #2", true);
        QuestionMCQDomain q3 = createOne(3L, (byte) 1, "MCQ #3", false);
        QuestionMCQDomain q4 = createOne(4L, (byte) 1, "MCQ #4", false);
        QuestionMCQDomain q5 = createOne(5L, (byte) 1, "MCQ #5", false);
        QuestionMCQDomain q6 = createOne(6L, (byte) 1, "MCQ #6", false);
        QuestionMCQDomain q7 = createOne(7L, (byte) 1, "MCQ #7", false);
        QuestionMCQDomain q8 = createOne(8L, (byte) 1, "MCQ #8", true);
        QuestionMCQDomain q9 = createOne(9L, (byte) 1, "MCQ #9", true);
        QuestionMCQDomain q10 = createOne(10L, (byte) 1, "MCQ #10", true);
        set.addAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<QuestionDomain> result = levelPartProducer.getLevelPart(set, (byte) 1, (short) 5);

        Assert.assertEquals(5, result.size());

        List<QuestionDomain> required = result.stream().filter(QuestionDomain::isRequired).collect(Collectors.toList());

        assertEquals(5, required.size());

        List<String> actual = required.stream().map(QuestionDomain::getQuestion).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ #1", "MCQ #2", "MCQ #8", "MCQ #9", "MCQ #10"));
    }

    private QuestionMCQDomain createOne(Long id, byte level, String question, boolean required) {
        return (QuestionMCQDomain) new QuestionMCQDomain()
                .setQuestionId(id)
                .setQuestion(question)
                .setType(1L)
                .setLevel(level)
                .setRequired(required);
    }

}
