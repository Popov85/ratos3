package ua.edu.ratos.domain.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.*;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void findByIdTest() {
        System.out.println(questionRepository.findById(1L));
    }

    @Test
    public void findAllTest() {
        questionRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void findAllQuestionsMultipleChoiceByThemeIdTest() {
        questionRepository.findAllQuestionMultipleChoiceByThemeId(1L).forEach(System.out::println);
    }

    @Test
    public void findAllQuestionsMultipleChoiceWithAnswersByThemeTest() {
        Theme theme = new Theme();
        theme.setThemeId(1L);
        questionRepository.findAllQuestionMultipleChoiceWithAnswersByTheme(theme).forEach(System.out::println);
    }


    @Test
    public void updateMCQTest() {
        QuestionMultipleChoice question = (QuestionMultipleChoice) questionRepository.findById(3L).get();
        assertTrue(question.getHelp().isPresent());

        question.setHelp(null);

        QuestionMultipleChoice questionUpdated = (QuestionMultipleChoice) questionRepository.findById(3L).get();
        assertFalse(questionUpdated.getHelp().isPresent());
    }


    @Test
    public void updateFBSTest() {
        QuestionFillBlankSingle question = (QuestionFillBlankSingle) questionRepository.findById(2L).get();
        assertFalse(question.getHelp().isPresent());
        assertTrue(question.getResources().isEmpty());

        question.setQuestion("Defines the set of cascadable operations.");

        Help help = new Help();
        help.setHelp("See package javax.persistence");
        help.setQuestion(question);
        final Resource resource = new Resource();
        resource.setLink("https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html");
        resource.setDescription("javax.persistence");

        help.setResources(Arrays.asList(resource));

        question.setHelp(help);

        QuestionFillBlankSingle questionUpdated = (QuestionFillBlankSingle) questionRepository.findById(2L).get();
        assertEquals(questionUpdated.getQuestion(), "Defines the set of cascadable operations.");
        assertTrue(question.getHelp().isPresent());
    }
}
