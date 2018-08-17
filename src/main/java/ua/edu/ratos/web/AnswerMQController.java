package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.entity.AnswerMQInDto;

@Slf4j
@RestController
public class AnswerMQController {

    @Autowired
    private AnswerSQService answerService;

    @PostMapping("/answer/mq")
    public Long save(@Validated(AnswerMQInDto.New.class) @RequestBody AnswerMQInDto dto, BindingResult result) {
        log.error("Error :: "+result.getAllErrors()+" dto :: "+dto);
        return 1L;
    }

    @PutMapping("/answer/mq")
    public void update(@Validated(AnswerMQInDto.Update.class) @RequestBody AnswerMQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @DeleteMapping("/answer/mq/{answerId}")
    public void delete(@PathVariable Long answerId) {
        log.info("Answer to delete ID :: {}", answerId);
    }

}
