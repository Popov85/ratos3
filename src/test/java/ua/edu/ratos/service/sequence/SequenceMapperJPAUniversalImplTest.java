package ua.edu.ratos.service.sequence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionFBSQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.entity.question.QuestionSQ;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.service.session.sequence.LevelPartProducer;
import ua.edu.ratos.service.session.sequence.SequenceMapperJPAUniversalImpl;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.*;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SequenceMapperJPAUniversalImplTest {

    @Autowired
    private SequenceMapperJPAUniversalImpl sequenceMapper;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private SchemeTheme schemeTheme;

    private Set<SchemeThemeSettings> settings;

    private Set<Question> questionsType1;

    private Set<Question> questionsType2;

    private Set<Question> questionsType5;

    @TestConfiguration
    static class SequenceMapperTestContextConfiguration {

        @Bean
        public CollectionShuffler collectionShuffler() {
            return new CollectionShuffler();
        }

        @Bean
        public LevelPartProducer levelPartProducer() {
            return new LevelPartProducer();
        }

        @Bean
        public SequenceMapperJPAUniversalImpl sequenceMapper() {
            return new SequenceMapperJPAUniversalImpl();
        }
    }

    @Before
    public void init() {
        // This matrix defines how many questions should be selected for this scheme
        SchemeThemeSettings set1 = createSettings(1L, createType(1L), (short)3, (short)2, (short)2);
        SchemeThemeSettings set2 = createSettings(1L, createType(2L), (short)1, (short)1, (short)1);
        SchemeThemeSettings set3 = createSettings(1L, createType(5L), (short)3, (short)0, (short)0);
        settings = new HashSet<>();
        settings.addAll(Arrays.asList(set1, set2, set3));

        final QuestionMCQ q11 = createQuestionType1(1L, (byte) 1, "MCQ11", true);
        final QuestionMCQ q12 = createQuestionType1(2L, (byte) 1, "MCQ12", false);
        final QuestionMCQ q13 = createQuestionType1(3L, (byte) 1, "MCQ13", false);

        final QuestionMCQ q14 = createQuestionType1(4L, (byte) 2, "MCQ14", false);
        final QuestionMCQ q15 = createQuestionType1(5L, (byte) 2, "MCQ15", false);
        final QuestionMCQ q16 = createQuestionType1(6L, (byte) 2, "MCQ16", false);

        final QuestionMCQ q17 = createQuestionType1(7L, (byte) 3, "MCQ17", false);
        final QuestionMCQ q18 = createQuestionType1(8L, (byte) 3, "MCQ18", false);
        final QuestionMCQ q19 = createQuestionType1(9L, (byte) 3, "MCQ19", false);
        questionsType1 = new HashSet<>();
        questionsType1.addAll(Arrays.asList(q11, q12, q13, q14, q15, q16, q17, q18, q19));

        final QuestionFBSQ q21 = createQuestionType2(10L, (byte) 1, "FBSQ21", false);
        final QuestionFBSQ q22 = createQuestionType2(11L, (byte) 1, "FBSQ22", false);
        final QuestionFBSQ q23 = createQuestionType2(12L, (byte) 1, "FBSQ23", false);

        final QuestionFBSQ q24 = createQuestionType2(13L, (byte) 2, "FBSQ24", false);
        final QuestionFBSQ q25 = createQuestionType2(14L, (byte) 2, "FBSQ25", false);
        final QuestionFBSQ q26 = createQuestionType2(15L, (byte) 2, "FBSQ26", false);

        final QuestionFBSQ q27 = createQuestionType2(16L, (byte) 3, "FBSQ27", false);
        final QuestionFBSQ q28 = createQuestionType2(17L, (byte) 3, "FBSQ28", false);
        final QuestionFBSQ q29 = createQuestionType2(18L, (byte) 3, "FBSQ29", false);
        questionsType2 = new HashSet<>();
        questionsType2.addAll(Arrays.asList(q21, q22, q23, q24, q25, q26, q27, q28, q29));

        final QuestionSQ q51 = createQuestionType5(19L, (byte) 1, "SQ31", true);
        final QuestionSQ q52 = createQuestionType5(20L, (byte) 1, "SQ32", false);
        final QuestionSQ q53 = createQuestionType5(21L, (byte) 1, "SQ33", false);

        final QuestionSQ q54 = createQuestionType5(23L, (byte) 2, "SQ34", false);
        final QuestionSQ q55 = createQuestionType5(24L, (byte) 2, "SQ35", false);
        final QuestionSQ q56 = createQuestionType5(25L, (byte) 2, "SQ36", false);

        final QuestionSQ q57 = createQuestionType5(26L, (byte) 3, "SQ37", false);
        final QuestionSQ q58 = createQuestionType5(27L, (byte) 3, "SQ38", false);
        final QuestionSQ q59 = createQuestionType5(28L, (byte) 3, "SQ39", false);
        questionsType5 = new HashSet<>();
        questionsType5.addAll(Arrays.asList(q51, q52, q53, q54, q55, q56, q57, q58, q59));
    }

    @Test
    public void getNOutOfMTest() {
        Mockito.when(questionRepository.findAllForSessionByThemeIdAndType(1L, 1L)).thenReturn(questionsType1);
        Mockito.when(questionRepository.findAllForSessionByThemeIdAndType(1L, 2L)).thenReturn(questionsType2);
        Mockito.when(questionRepository.findAllForSessionByThemeIdAndType(1L, 5L)).thenReturn(questionsType5);

        final List<Question> result = sequenceMapper.getNOutOfM(1L, settings);

        verify(questionRepository, times(1)).findAllForSessionByThemeIdAndType(1L, 1L);
        verify(questionRepository, times(1)).findAllForSessionByThemeIdAndType(1L, 2L);
        verify(questionRepository, times(1)).findAllForSessionByThemeIdAndType(1L, 5L);

        assertEquals(13, result.size());

        List<Question> required = result.stream().filter(Question::isRequired).collect(Collectors.toList());

        assertEquals(2, required.size());

        List<String> actual = required.stream().map(q -> q.getQuestion()).collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder("MCQ11", "SQ31"));
    }

    //-------------------------------------------------SUPPORT----------------------------------------------------------

    private QuestionType createType(Long typeId) {
        QuestionType t1 = new QuestionType();
        t1.setTypeId(typeId);
        return t1;
    }

    private SchemeThemeSettings createSettings(Long id, QuestionType type, short l1, short l2, short l3) {
        SchemeThemeSettings set = new SchemeThemeSettings();
        set.setSchemeThemeSettingsId(id);
        set.setSchemeTheme(schemeTheme);
        set.setType(type);
        set.setLevel1(l1);
        set.setLevel2(l2);
        set.setLevel3(l3);
        return set;
    }

    private QuestionMCQ createQuestionType1(Long id, byte level, String question, boolean required) {
        QuestionMCQ q = new QuestionMCQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setType(createType(1L));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }

    private QuestionFBSQ createQuestionType2(Long id, byte level, String question, boolean required) {
        QuestionFBSQ q = new QuestionFBSQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setType(createType(2L));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }

    private QuestionSQ createQuestionType5(Long id, byte level, String question, boolean required) {
        QuestionSQ q = new QuestionSQ();
        q.setQuestionId(id);
        q.setQuestion(question);
        q.setType(createType(5L));
        q.setLevel(level);
        q.setRequired(required);
        return q;
    }
}
