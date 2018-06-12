package ua.edu.ratos.domain.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.model.Help;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.Theme;
import ua.edu.ratos.domain.model.answer.*;
import ua.edu.ratos.domain.model.question.*;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void findAllQuestions() {
        questionRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void findAllQuestionsMultipleChoiceByThemeIdTest() {
        questionRepository.findAllQuestionMultipleChoiceByThemeId(1L).forEach(System.out::println);
    }

    //@Commit
    @Test
    public void saveMCQTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionMultipleChoice mcq = new QuestionMultipleChoice();
        mcq.setQuestion("Interface used to interact with the second-level cache.");
        mcq.setLevel((byte)1);
        mcq.setTheme(theme);

        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswer("Cache");
        answer.setPercent((short)100);
        answer.setRequired(true);

        AnswerMultipleChoice answer1 = new AnswerMultipleChoice();
        answer1.setAnswer("EntityManager");
        answer1.setPercent((short)0);
        answer1.setRequired(false);

        AnswerMultipleChoice answer2 = new AnswerMultipleChoice();
        answer2.setAnswer("StoredProcedure");
        answer2.setPercent((short)0);
        answer2.setRequired(false);

        AnswerMultipleChoice answer3 = new AnswerMultipleChoice();
        answer3.setAnswer("Subgraph<T>");
        answer3.setPercent((short)0);
        answer3.setRequired(false);

        mcq.addAnswer(answer);
        mcq.addAnswer(answer1);
        mcq.addAnswer(answer2);
        mcq.addAnswer(answer3);

        Resource resource = new Resource();
        resource.setLink("http://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html");
        resource.setDescription("Java Persistence is the API");

        mcq.setResources(Arrays.asList(resource));

        Help help = new Help();
        help.setHelp("Package javax.persistence");
        help.setQuestion(mcq);

        mcq.setHelp(help);

        questionRepository.save(mcq);
    }


    //@Commit
    @Test
    public void saveFBSTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionFillBlankSingle fbsq = new QuestionFillBlankSingle();
        fbsq.setQuestion("Defines the set of cascadable operations that are propagated to the associated entity.");
        fbsq.setLevel((byte)1);
        fbsq.setTheme(theme);

        final AnswerFillBlankSingle answer = new AnswerFillBlankSingle();

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("CascadeType");

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Enum<CascadeType>");

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("java.lang.Object java.lang.Enum<CascadeType>");

        answer.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1, acceptedPhrase2));

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setLang("en");
        settings.setWordsLimit((short)3);
        settings.setSymbolsLimit((short) 25);

        answer.setSettings(settings);

        answer.setQuestion(fbsq);

        fbsq.setAnswer(answer);

        questionRepository.save(fbsq);
    }

    //@Commit
    @Test
    public void saveFBMTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionFillBlankMultiple fbmq = new QuestionFillBlankMultiple();
        fbmq.setQuestion("Lock modes can be specified by means of passing a LockModeType argument to one " +
                "of the EntityManager methods that take locks (lock, find, or refresh) or to the" +
                "Query.setLockMode() or TypedQuery.setLockMode() method.");
        fbmq.setLevel((byte)2);
        fbmq.setTheme(theme);

        final AnswerFillBlankMultiple answer = new AnswerFillBlankMultiple();
        answer.setPhrase("Query.setLockMode()");
        answer.setOccurrence((byte)1);

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("setLockMode() of Query");

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Query.setLockMode");

        answer.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1));

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setLang("en");
        settings.setWordsLimit((short)3);
        settings.setSymbolsLimit((short) 30);

        answer.setSettings(settings);
        answer.setQuestion(fbmq);


        final AnswerFillBlankMultiple answer2 = new AnswerFillBlankMultiple();
        answer2.setPhrase("TypedQuery.setLockMode()");
        answer2.setOccurrence((byte)1);

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("setLockMode() of TypedQuery");

        AcceptedPhrase acceptedPhrase3 = new AcceptedPhrase();
        acceptedPhrase3.setPhrase("TypedQuery.setLockMode");

        answer2.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1));

        SettingsAnswerFillBlank settings2 = new SettingsAnswerFillBlank();
        settings2.setLang("en");
        settings2.setWordsLimit((short)3);
        settings2.setSymbolsLimit((short) 40);

        answer2.setSettings(settings2);
        answer2.setQuestion(fbmq);

        fbmq.setAnswers(Arrays.asList(answer, answer2));

        questionRepository.save(fbmq);
    }

    //@Commit
    @Test
    public void saveMQTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionMatcher mq = new QuestionMatcher();
        mq.setQuestion("Match the following concepts:");
        mq.setLevel((byte)3);
        mq.setTheme(theme);

        final AnswerMatcher answer = new AnswerMatcher();
        answer.setLeftPhrase("Interface used to interact with the second-level cache.");
        answer.setRightPhrase("Cache");
        answer.setQuestion(mq);

        final AnswerMatcher answer2 = new AnswerMatcher();
        answer2.setLeftPhrase("Type for query parameter objects.");
        answer2.setRightPhrase("Parameter<T>");
        answer2.setQuestion(mq);

        final AnswerMatcher answer3 = new AnswerMatcher();
        answer3.setLeftPhrase("Interface used to interact with the persistence context.");
        answer3.setRightPhrase("EntityManager");
        answer3.setQuestion(mq);

        final AnswerMatcher answer4 = new AnswerMatcher();
        answer4.setLeftPhrase("Interface used to control query execution.");
        answer4.setRightPhrase("Query");
        answer4.setQuestion(mq);

        mq.setAnswers(Arrays.asList(answer, answer2, answer3));

        questionRepository.save(mq);
    }

    //@Commit
    @Test
    public void saveSQTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionSequence sq = new QuestionSequence();
        sq.setQuestion("Order Maven build process stages one after another:");
        sq.setLevel((byte)2);
        sq.setTheme(theme);

        final AnswerSequence answer = new AnswerSequence();
        answer.setPhrase("clean");
        answer.setOrder((short) 0);
        answer.setQuestion(sq);

        final AnswerSequence answer2 = new AnswerSequence();
        answer2.setPhrase("validate");
        answer2.setOrder((short) 1);
        answer2.setQuestion(sq);

        final AnswerSequence answer3 = new AnswerSequence();
        answer3.setPhrase("compile");
        answer3.setOrder((short) 2);
        answer3.setQuestion(sq);

        final AnswerSequence answer4 = new AnswerSequence();
        answer4.setPhrase("test");
        answer4.setOrder((short) 3);
        answer4.setQuestion(sq);

        final AnswerSequence answer5 = new AnswerSequence();
        answer5.setPhrase("package");
        answer5.setOrder((short) 4);
        answer5.setQuestion(sq);

        final AnswerSequence answer6 = new AnswerSequence();
        answer6.setPhrase("verify");
        answer6.setOrder((short) 5);
        answer6.setQuestion(sq);

        sq.setAnswers(Arrays.asList(answer, answer2, answer3, answer4, answer5, answer6));

        questionRepository.save(sq);
    }
}
