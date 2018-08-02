package ua.edu.ratos.it.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.domain.entity.answer.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankSingle;
import ua.edu.ratos.domain.entity.question.QuestionType;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.service.AnswerFBSQService;
import ua.edu.ratos.service.dto.entity.AnswerFBSQInDto;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnswerFBSQServiceTestIT {

    public static final String QUESTION = "What defines the set of cascadable operations that are propagated to the associated entity?";
    public static final String PHRASE1 = "CascadeType";
    public static final String PHRASE2 = "Enum<CascadeType>";
    public static final String NEW_PHRASE_3 = "javax.persistence.CascadeType";
    public static final String SETTINGS_NAME_2 = "ru default";

    @Autowired
    private AnswerFBSQService answerService;

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager em;

    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWrongTest() {
        //1. Insert question with answer with 2 accepted phrases
        insert();
        // 2. Set another settings {2L} and add no phrases
        AnswerFBSQInDto dto = new AnswerFBSQInDto(1L, 2L, new HashSet<>());
        answerService.update(dto);
    }


    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateSimpleTest() {
        //1. Insert question with answer with 2 accepted phrases
        insert();
        // 2. Set another settings {2L}
        AnswerFBSQInDto dto = new AnswerFBSQInDto(1L, 2L, new HashSet<>(Arrays.asList(1L, 2L)));
        answerService.update(dto);
        // 3. Find and check updated settings
        final Optional<AnswerFillBlankSingle> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals(SETTINGS_NAME_2, foundAnswer.get().getSettings().getName());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateWithPhrasesTest() {
        //1. Insert question with answer with 2 accepted phrases
        insert();
        // 2. Set another settings {2L} and add another phrase {3L}
        AnswerFBSQInDto dto = new AnswerFBSQInDto(1L, 2L, new HashSet<>(Arrays.asList(1L, 2L, 3L)));
        answerService.update(dto);
        // 3. Find and check updated settings
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertNotNull(foundAnswer);
        Assert.assertEquals(3, foundAnswer.getAcceptedPhrases().size());
        Assert.assertEquals(SETTINGS_NAME_2, foundAnswer.getSettings().getName());
        Assert.assertTrue(foundAnswer.getAcceptedPhrases().contains(new AcceptedPhrase(NEW_PHRASE_3)));
    }


    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseTest() {
        //1. Insert question with answer with 2 accepted phrases
        insert();
        //2. Add another (3d) accepted phrase
        answerService.addAcceptedPhrase(1L, 3L);
        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(3, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseExistingTest() {
        //1. Insert question with answer with 2 accepted phrases
        insert();
        //2. Add another accepted phrase already associated with the answer {2L}
        answerService.addAcceptedPhrase(1L, 2L);
        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(2, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAcceptedPhraseTest() {
        //1. Create question with answer with 3 accepted phrases
        insert();
        //2. Remove one (2-d) accepted phrase
        answerService.deleteAcceptedPhrase(1L,2L);
        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertEquals(1, foundAnswer.getAcceptedPhrases().size());
    }

    private Question insert() {
        QuestionFillBlankSingle question = new QuestionFillBlankSingle(QUESTION, (byte) 1);
        question.setLang(em.getReference(Language.class,1L));
        question.setType(em.getReference(QuestionType.class, 2L));
        question.setTheme(em.getReference(Theme.class, 1L));
        AnswerFillBlankSingle answer = new AnswerFillBlankSingle();
        answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, 1L));
        answer.setQuestion(question);
        answer.addPhrase(em.find(AcceptedPhrase.class, 1L));
        answer.addPhrase(em.find(AcceptedPhrase.class, 2L));
        question.setAnswer(answer);
        return questionRepository.save(question);
    }
}
