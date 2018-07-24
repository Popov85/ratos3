package ua.edu.ratos.integration_test.service;

import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankSingle;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.service.AnswerFBSQService;
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
    public static final String PHRASE3 = "javax.persistence.CascadeType";
    public static final String NEW_PHRASE = "Enum CascadeType";


    @Autowired
    private AnswerFBSQService answerService;

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private QuestionTypeRepository typeRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private SettingsAnswerFillBlankRepository settingsRepository;

    @Autowired
    private AcceptedPhraseRepository phraseRepository;

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseTest() {
        //1. Create question with answer with 3 accepted phrases
        final Staff staff = staffRepository.getOne(1L);
        insert(staff);

        //2. Add another (4-th) accepted phrase
        final AcceptedPhrase newPhrase = new AcceptedPhrase(NEW_PHRASE);
        newPhrase.setStaff(staff);
        answerService.addAcceptedPhrase(newPhrase, 1L);

        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getAcceptedPhrases()));
        Assert.assertEquals(4, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addAcceptedPhraseExistingTest() {
        //1. Create question with answer with 3 accepted phrases
        final Staff staff = staffRepository.getOne(1L);
        insert(staff);

        //2. Add another (4-th) accepted phrase
        final AcceptedPhrase newPhrase = new AcceptedPhrase(NEW_PHRASE);
        newPhrase.setStaff(staff);
        final AcceptedPhrase savedPhrase = phraseRepository.save(newPhrase);
        answerService.addAcceptedPhrase(savedPhrase.getPhraseId(), 1L);

        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getAcceptedPhrases()));
        Assert.assertEquals(4, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAcceptedPhraseTest() {
        //1. Create question with answer with 3 accepted phrases
        insert(staffRepository.getOne(1L));

        //2. Remove one (2-d) accepted phrase
        answerService.deleteAcceptedPhrase(2L,1L);

        //3. Find with accepted phrases and compare
        final AnswerFillBlankSingle foundAnswer = answerRepository.findByIdWithAcceptedPhrases(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundAnswer.getAcceptedPhrases()));
        Assert.assertEquals(2, foundAnswer.getAcceptedPhrases().size());
    }

    @Test
    @Sql(scripts = "/scripts/answer_fbsq_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/answer_fbsq_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        //1. Create question with answer with 3 accepted phrases
        insert(staffRepository.getOne(1L));

        // 2. Set another settings
        answerService.update(1L, 2L);

        // 3. Find and check updated settings
        final Optional<AnswerFillBlankSingle> foundAnswer = answerRepository.findById(1L);
        Assert.assertTrue(foundAnswer.isPresent());
        Assert.assertEquals("ru default", foundAnswer.get().getSettings().getName());
    }

    private Question insert(Staff staff) {
        QuestionFillBlankSingle question = new QuestionFillBlankSingle(QUESTION, (byte) 1);
        question.setLang(languageRepository.getOne(1L));
        question.setType(typeRepository.getOne(2L));
        question.setTheme(themeRepository.getOne(1L));
        AnswerFillBlankSingle answer = new AnswerFillBlankSingle();
        answer.setSettings(settingsRepository.getOne(1L));
        answer.setQuestion(question);
        final AcceptedPhrase phrase1 = new AcceptedPhrase(PHRASE1);
        phrase1.setStaff(staff);
        final AcceptedPhrase phrase2 = new AcceptedPhrase(PHRASE2);
        phrase2.setStaff(staff);
        final AcceptedPhrase phrase3 = new AcceptedPhrase(PHRASE3);
        phrase3.setStaff(staff);
        answer.setAcceptedPhrases(new HashSet<>(Arrays.asList(phrase1, phrase2, phrase3)));
        question.setAnswer(answer);
        return questionRepository.save(question);
    }
}
