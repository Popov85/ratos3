package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.service.QuestionService;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @ResponseBody
    @GetMapping("/findOneById")
    public String findOneById() {
        final Question question = questionService.findOneById(21L);
        log.info("Question with ID = 1L :: {} ", question);
        return question.getQuestion();
    }

    @ResponseBody
    @GetMapping("/findAllQMCWithAnswersByTheme")
    public List<String> findAllQuestionMultipleChoiceWithAnswersByTheme() {
        Theme theme = new Theme();
        theme.setThemeId(1L);
        final List<QuestionMultipleChoice> questions = questionService.findAllQuestionMultipleChoiceWithAnswersByTheme(theme);
        log.info("All MCQs of theme with ID=1L ::");
        questions.forEach(q->log.info(q.getAnswers().toString()));
        return questions.stream().map(q->q.getQuestion()).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/findAllByThemeId")
    public List<String> findAllQuestionMultipleChoiceByThemeId() {
        final List<QuestionMultipleChoice> questions = questionService.findAllQuestionMultipleChoiceByThemeId(1L);
        log.info("All MCQs of theme with ID=1L ::");
        questions.forEach(q->log.info(q.getQuestion()));
        return questions.stream().map(q->q.getQuestion()).collect(Collectors.toList());
    }

}
