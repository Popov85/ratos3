package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerFBSQService;
import ua.edu.ratos.service.dto.entity.AnswerFBSQInDto;

@Slf4j
@RestController
public class AnswerFBSQController {

    @Autowired
    private AnswerFBSQService answerService;

    @PutMapping("/answer/fbsq")
    public void update(@Validated(AnswerFBSQInDto.Update.class) @RequestBody AnswerFBSQInDto dto) {
        log.info("Answer :: {}", dto);
    }
}
