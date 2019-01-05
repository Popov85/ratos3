package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerFBSQService;
import ua.edu.ratos.service.dto.in.AnswerFBSQInDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/instructor/question/answer-fbsq")
public class AnswerFBSQController {

    @Autowired
    private AnswerFBSQService answerService;

    @PutMapping("/{answerId}")
    public void update(@PathVariable Long answerId, @Valid @RequestBody AnswerFBSQInDto dto) {
        answerService.update(answerId, dto);
        log.debug("Updated answer fbsq ID :: {}", dto);
    }
}
