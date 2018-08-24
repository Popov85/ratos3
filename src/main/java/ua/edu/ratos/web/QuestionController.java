package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.dto.entity.*;


@Slf4j
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/question/mcq")
    public Long save(@Validated({QuestionInDto.New.class, AnswerMCQInDto.Include.class}) @RequestBody QuestionMCQInDto dto) {
        log.debug("QuestionMultipleChoice dto :: {} ", dto);
      /*  final Long generatedId = questionService.save(dto);
        log.debug("Saved multipleChoiceQuestion ID = {} ", generatedId);*/
        return 1L;
    }

    @PostMapping("/question/fbsq")
    public Long save(@Validated({QuestionInDto.New.class, AnswerFBSQInDto.New.class}) @RequestBody QuestionFBSQInDto dto) {
        log.debug("QuestionFillBlankSimple dto :: {} ", dto);
        /*  final Long generatedId = questionService.save(dto);
        log.debug("Saved fillBlankSimpleQuestion ID = {} ", generatedId);*/
        return 1L;
    }

    @PostMapping("/question/fbmq")
    public Long save(@Validated({QuestionInDto.New.class, AnswerFBMQInDto.Include.class}) @RequestBody QuestionFBMQInDto dto) {
        log.debug("QuestionFillBlankMultiple dto :: {} ", dto);
        /*  final Long generatedId = questionService.save(dto);
        log.debug("Saved fillBlankMultipleQuestion ID = {} ", generatedId);*/
        return 1L;
    }

    @PostMapping("/question/mq")
    public Long save(@Validated({QuestionInDto.New.class, AnswerMQInDto.Include.class}) @RequestBody QuestionMQInDto dto) {
        log.debug("QuestionMatcher dto :: {} ", dto);
        /*  final Long generatedId = questionService.save(dto);
        log.debug("Saved matcherQuestion ID = {} ", generatedId);*/
        return 1L;
    }


    @PostMapping("/question/sq")
    public Long save(@Validated({QuestionInDto.New.class, AnswerSQInDto.Include.class}) @RequestBody QuestionSQInDto dto) {
        log.debug("QuestionSequence dto :: {} ", dto);
        /*  final Long generatedId = questionService.save(dto);
        log.debug("Saved sequenceQuestion ID = {} ", generatedId);*/
        return 1L;
    }




    @PutMapping("/question")
    public void update(@Validated(QuestionInDto.Update.class) @RequestBody QuestionInDto dto) {
        log.debug("Question dto :: {} ", dto);
    }

    @DeleteMapping("/question/{questionId}")
    public void delete(@PathVariable Long questionId) {
        log.debug("Question to delete ID :: {}", questionId);
    }

}
