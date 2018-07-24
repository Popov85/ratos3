package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.domain.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;


    @ResponseBody
    @GetMapping("/findOneById")
    public String findOneById() {
        final Question question = questionRepository.findById(21L).get();
        log.info("Question with ID = 1L :: {} ", question);
        return question.getQuestion();
    }

    @ResponseBody
    @GetMapping("/findAllQMCWithEverythingByTheme")
    public List<String> findAllQuestionMultipleChoiceWithAnswersByTheme() {
        final List<QuestionMultipleChoice> questions = questionRepository.findAllMCQWithEverythingByThemeId(1L);
        log.info("All MCQs of theme with ID :: 1L");
        questions.forEach(q->log.info(q.getAnswers().toString()));
        return questions.stream().map(q->q.getQuestion()).collect(Collectors.toList());
    }

}
