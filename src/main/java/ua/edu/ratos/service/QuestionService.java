package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question findOneById(Long questionId) {
        return questionRepository.findById(questionId).get();
    }

    public List<QuestionMultipleChoice> findAllQuestionMultipleChoiceWithAnswersByTheme(Theme theme) {
        return questionRepository.findAllQuestionMultipleChoiceWithAnswersByTheme(theme);
    }

    public List<QuestionMultipleChoice> findAllQuestionMultipleChoiceByThemeId(Long themeId) {
        return questionRepository.findAllQuestionMultipleChoiceByThemeId(themeId);
    }


    @PostConstruct
    public void post() {
        System.out.println("QuestionService was created....");
    }
}
