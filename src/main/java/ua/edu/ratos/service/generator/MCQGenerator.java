package ua.edu.ratos.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.answer.AnswerMCQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.repository.QuestionRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MCQGenerator {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private Rnd rnd;


    @TrackTime
    @Transactional
    public List<QuestionMCQ> generate(int quantity, List<Theme> themes, List<Resource> resources, List<Help> helps) {
        List<QuestionMCQ> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Theme theme = themes.get(rnd.rnd(0, themes.size()));
            Help help = helps.get(rnd.rnd(0, helps.size() - 1));
            QuestionMCQ question = createOne(i, theme, help, resources);
            result.add(question);
        }
        questionRepository.saveAll(result);
        return result;
    }

    private QuestionMCQ createOne(int questionId, Theme theme, Help help, List<Resource> resources) {
        QuestionMCQ questionMCQ = new QuestionMCQ();
        questionMCQ.setQuestion(prefix()+"_question MCQ #"+questionId);
        questionMCQ.setType(em.getReference(QuestionType.class, 1L));
        questionMCQ.setLang(em.getReference(Language.class, 1L));
        questionMCQ.setLevel((byte) rnd.rnd(1, 4));
        questionMCQ.setTheme(theme);

        // each 100th is deleted
        if (questionId%100==0) questionMCQ.setDeleted(true);

        questionMCQ.addHelp(em.getReference(Help.class, help.getHelpId()));

        Resource resource = resources.get(rnd.rnd(0, resources.size() - 1));
        questionMCQ.addResource(em.getReference(Resource.class, resource.getResourceId()));

        List<AnswerMCQ> answers = createAnswers(questionId, rnd.rnd(3, 6), resources);
        answers.forEach(a->questionMCQ.addAnswer(a));
        return questionMCQ;
    }

    private String prefix() {
        List<Character> letters = Arrays.asList('a','b','c','d','e','f','g');
        return Character.toString(letters.get(rnd.rnd(0, 7)))
                +Character.toString(letters.get(rnd.rnd(0, 7)))
                +Character.toString(letters.get(rnd.rnd(0, 7)));
    }

    private List<AnswerMCQ> createAnswers(int questionId, int quantity, List<Resource> resources) {
        List<AnswerMCQ> result = new ArrayList<>();
        for (int i = 1; i < quantity; i++) {
            // Add many incorrect answers
            result.add(createInCorrectAnswer(questionId, i, resources));
        }
        // At the end add the correct answer
        result.add(createCorrectAnswer(questionId, quantity, resources));
        return result;
    }

    private AnswerMCQ createInCorrectAnswer(int questionId, int answer, List<Resource> resources) {
        AnswerMCQ answerMCQ = new AnswerMCQ();
        answerMCQ.setAnswer("Answer (incorrect) #"+answer+" to question #"+questionId);
        answerMCQ.setPercent((short)0);
        answerMCQ.setRequired(false);
        Resource resource = resources.get(rnd.rnd(0, resources.size() - 1));
        answerMCQ.addResource(em.getReference(Resource.class, resource.getResourceId()));
        return answerMCQ;
    }

    private AnswerMCQ createCorrectAnswer(int questionId, int answer, List<Resource> resources) {
        AnswerMCQ answerMCQ = new AnswerMCQ();
        answerMCQ.setAnswer("Answer (correct) #"+answer+" to question #"+questionId);
        answerMCQ.setPercent((short)100);
        answerMCQ.setRequired(true);
        Resource resource = resources.get(rnd.rnd(0, resources.size() - 1));
        answerMCQ.addResource(em.getReference(Resource.class, resource.getResourceId()));
        return answerMCQ;
    }



    //------------------------------Extended version (new resource and help for each question)--------------------------


    @Transactional
    public List<QuestionMCQ> generate(int quantity, List<Theme> themes) {
        List<QuestionMCQ> result = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            Theme theme = themes.get(rnd.rnd(0, themes.size() - 1));
            QuestionMCQ question = createOne(i, theme);
            result.add(question);
        }
        questionRepository.saveAll(result);
        return result;
    }

    private QuestionMCQ createOne(int questionId, Theme theme) {
        QuestionMCQ questionMCQ = new QuestionMCQ();
        questionMCQ.setQuestion("Question MCQ #"+questionId);
        questionMCQ.setType(em.getReference(QuestionType.class, 1L));
        questionMCQ.setLang(em.getReference(Language.class, 1L));
        questionMCQ.setLevel((byte) rnd.rnd(1, 3));
        questionMCQ.setTheme(theme);

        addHelp(questionMCQ);

        addResource(questionMCQ);

        List<AnswerMCQ> answers = createAnswers(questionId, rnd.rnd(3, 5), true);
        answers.forEach(a->questionMCQ.addAnswer(a));
        return questionMCQ;
    }


    private List<AnswerMCQ> createAnswers(int questionId, int quantity, boolean resource) {
        List<AnswerMCQ> result = new ArrayList<>();
        for (int i = 1; i < quantity; i++) {
            // Add many incorrect answers
            result.add(createInCorrectAnswer(questionId, i, resource));
        }
        // At the end add the correct answer
        result.add(createCorrectAnswer(questionId, quantity, resource));
        return result;
    }


    private AnswerMCQ createInCorrectAnswer(int questionId, int answer, boolean resource) {
        AnswerMCQ answerMCQ = new AnswerMCQ();
        answerMCQ.setAnswer("Answer (incorrect) #"+answer+" to question #"+questionId);
        answerMCQ.setPercent((short)0);
        answerMCQ.setRequired(false);
        if (resource) addResource(answerMCQ);
        return answerMCQ;
    }

    private AnswerMCQ createCorrectAnswer(int questionId, int answer, boolean resource) {
        AnswerMCQ answerMCQ = new AnswerMCQ();
        answerMCQ.setAnswer("Answer (correct) #"+answer+" to question #"+questionId);
        answerMCQ.setPercent((short)100);
        answerMCQ.setRequired(true);
        if (resource) addResource(answerMCQ);
        return answerMCQ;
    }

    private AnswerMCQ addResource(AnswerMCQ a) {
        Resource resource = new Resource();
        resource.setDescription("Resource for answer "+a.getAnswer());
        resource.setLink("https://resources-for-answers.com/"+a.getAnswer());
        resource.setStaff(em.getReference(Staff.class, 1L));
        a.addResource(resource);
        return a;
    }

    private QuestionMCQ addResource(QuestionMCQ q) {
        Resource resource = new Resource();
        resource.setDescription("Resource for question "+q.getQuestion());
        resource.setLink("https://resources-for-totalByType.com/"+q.getQuestion());
        resource.setStaff(em.getReference(Staff.class, 1L));
        q.addResource(resource);
        return q;
    }

    private QuestionMCQ addHelp(QuestionMCQ q) {
        Help help = new Help();
        help.setName("Help MCQ for "+q.getQuestion());
        help.setHelp("See Help MCQ for "+q.getQuestion());
        help.setStaff(em.getReference(Staff.class, 1L));
        addResource(help);
        q.addHelp(help);
        return q;
    }

    private Help addResource(Help h) {
        Resource resource = new Resource();
        resource.setDescription("Resource for help "+h.getName());
        resource.setLink("https://resources-for-helps.com/"+h.getName());
        resource.setStaff(em.getReference(Staff.class, 1L));
        h.addResource(resource);
        return h;
    }
}
