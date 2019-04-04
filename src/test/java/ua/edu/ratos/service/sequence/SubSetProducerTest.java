package ua.edu.ratos.service.sequence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionFBSQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.entity.question.QuestionSQ;
import ua.edu.ratos.service.session.sequence.Affiliation;
import ua.edu.ratos.service.session.sequence.RequiredAwareSubSetProducer;
import ua.edu.ratos.service.session.sequence.SubSetProducer;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.*;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SubSetProducerTest {

    @Autowired
    private SubSetProducer subSetProducer;

    private Map<Affiliation, Set<Question>> map;

    @TestConfiguration
    static class SequenceMapperTestContextConfiguration {

        @Bean
        public RequiredAwareSubSetProducer levelPartProducer() {
            return new RequiredAwareSubSetProducer();
        }

        @Bean
        public SubSetProducer subSetProducer() {
            return new SubSetProducer();
        }

        @Bean
        public CollectionShuffler collectionShuffler() {
            return new CollectionShuffler();
        }
    }

    @Before
    public void init() {

        map = new HashMap<>();

        final QuestionMCQ q11 = createQuestionType1(1L, 1L, (byte) 1, "MCQ11", true);
        final QuestionMCQ q12 = createQuestionType1(2L, 1L, (byte) 1, "MCQ12", false);
        final QuestionMCQ q13 = createQuestionType1(3L, 1L, (byte) 1, "MCQ13", false);

        final QuestionMCQ q14 = createQuestionType1(4L, 1L, (byte) 2, "MCQ14", false);
        final QuestionMCQ q15 = createQuestionType1(5L, 1L, (byte) 2, "MCQ15", false);
        final QuestionMCQ q16 = createQuestionType1(6L, 1L, (byte) 2, "MCQ16", false);

        final QuestionMCQ q17 = createQuestionType1(7L, 1L, (byte) 3, "MCQ17", false);
        final QuestionMCQ q18 = createQuestionType1(8L, 1L, (byte) 3, "MCQ18", false);
        final QuestionMCQ q19 = createQuestionType1(9L, 1L, (byte) 3, "MCQ19", false);

        // 2 out of total 3 for each
        Affiliation a1 = new Affiliation(1L, 1L, (byte) 1, (short) 2);
        Affiliation a2 = new Affiliation(1L, 1L, (byte) 2, (short) 2);
        Affiliation a3 = new Affiliation(1L, 1L, (byte) 3, (short) 2);

        map.put(a1, new HashSet<>(Arrays.asList(q11, q12, q13)));
        map.put(a2, new HashSet<>(Arrays.asList(q14, q15, q16)));
        map.put(a3, new HashSet<>(Arrays.asList(q17, q18, q19)));


        final QuestionFBSQ q21 = createQuestionType2(10L, 2L, (byte) 1, "FBSQ21", false);
        final QuestionFBSQ q22 = createQuestionType2(11L, 2L, (byte) 1, "FBSQ22", false);
        final QuestionFBSQ q23 = createQuestionType2(12L, 2L, (byte) 1, "FBSQ23", false);

        final QuestionFBSQ q24 = createQuestionType2(13L, 2L, (byte) 2, "FBSQ24", false);
        final QuestionFBSQ q25 = createQuestionType2(14L, 2L, (byte) 2, "FBSQ25", false);
        final QuestionFBSQ q26 = createQuestionType2(15L, 2L, (byte) 2, "FBSQ26", false);

        final QuestionFBSQ q27 = createQuestionType2(16L, 2L, (byte) 3, "FBSQ27", false);
        final QuestionFBSQ q28 = createQuestionType2(17L, 2L, (byte) 3, "FBSQ28", false);
        final QuestionFBSQ q29 = createQuestionType2(18L, 2L, (byte) 3, "FBSQ29", false);

        // 2 out of total 3 for each
        Affiliation a4 = new Affiliation(2L, 2L, (byte) 1, (short) 2);
        Affiliation a5 = new Affiliation(2L, 2L, (byte) 2, (short) 2);
        Affiliation a6 = new Affiliation(2L, 2L, (byte) 3, (short) 2);

        map.put(a4, new HashSet<>(Arrays.asList(q21, q22, q23)));
        map.put(a5, new HashSet<>(Arrays.asList(q24, q25, q26)));
        map.put(a6, new HashSet<>(Arrays.asList(q27, q28, q29)));


        final QuestionSQ q51 = createQuestionType5(19L, 3L, (byte) 1, "SQ31", true);
        final QuestionSQ q52 = createQuestionType5(20L, 3L, (byte) 1, "SQ32", false);
        final QuestionSQ q53 = createQuestionType5(21L, 3L, (byte) 1, "SQ33", false);

        final QuestionSQ q54 = createQuestionType5(23L, 3L, (byte) 2, "SQ34", false);
        final QuestionSQ q55 = createQuestionType5(24L, 3L, (byte) 2, "SQ35", false);
        final QuestionSQ q56 = createQuestionType5(25L, 3L, (byte) 2, "SQ36", false);

        final QuestionSQ q57 = createQuestionType5(26L, 3L, (byte) 3, "SQ37", false);
        final QuestionSQ q58 = createQuestionType5(27L, 3L, (byte) 3, "SQ38", false);
        final QuestionSQ q59 = createQuestionType5(28L, 3L, (byte) 3, "SQ39", false);

        // 2 out of total 3 for each
        Affiliation a7 = new Affiliation(3L, 5L, (byte) 1, (short) 2);
        Affiliation a8 = new Affiliation(3L, 5L, (byte) 2, (short) 2);
        Affiliation a9 = new Affiliation(3L, 5L, (byte) 3, (short) 2);

        map.put(a7, new HashSet<>(Arrays.asList(q51, q52, q53)));
        map.put(a8, new HashSet<>(Arrays.asList(q54, q55, q56)));
        map.put(a9, new HashSet<>(Arrays.asList(q57, q58, q59)));

    }

    @Test
    public void getSubSetTest() {

        final List<Question> result = subSetProducer.getSubSet(map);

        assertEquals(18, result.size());

        List<Question> required = result.stream().filter(Question::isRequired).collect(Collectors.toList());

        assertEquals(2, required.size());

        List<String> actual = required.stream().map(q -> q.getQuestion()).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ11", "SQ31"));
    }

    //-------------------------------------------------SUPPORT----------------------------------------------------------

    private QuestionType createType(Long typeId) {
        QuestionType t = new QuestionType();
        t.setTypeId(typeId);
        return t;
    }

    private Theme createTheme(Long themeId) {
        Theme t = new Theme();
        t.setThemeId(themeId);
        return t;
    }


    private QuestionMCQ createQuestionType1(Long id, Long themeId, byte level, String question, boolean required) {
        QuestionMCQ q = new QuestionMCQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setType(createType(1L));
        q.setTheme(createTheme(themeId));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }

    private QuestionFBSQ createQuestionType2(Long id, Long themeId, byte level, String question, boolean required) {
        QuestionFBSQ q = new QuestionFBSQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setTheme(createTheme(themeId));
        q.setType(createType(2L));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }

    private QuestionSQ createQuestionType5(Long id, Long themeId, byte level, String question, boolean required) {
        QuestionSQ q = new QuestionSQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setTheme(createTheme(themeId));
        q.setType(createType(5L));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }
}
