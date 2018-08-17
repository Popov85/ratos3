package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.entity.AnswerSQInDto;

@Slf4j
@RestController
public class AnswerSQController {
    @Autowired
    private AnswerSQService answerService;

    @PostMapping("/answer/sq")
    public Long save(@Validated(AnswerSQInDto.New.class) @RequestBody AnswerSQInDto dto, BindingResult result) {
        log.error("Error :: "+result.getAllErrors()+" dto :: "+dto);
        return 1L;
    }

    @PutMapping("/answer/sq")
    public void update(@Validated(AnswerSQInDto.Update.class) @RequestBody AnswerSQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @DeleteMapping("/answer/sq/{answerId}")
    public void delete(@PathVariable Long answerId) {
        log.info("Answer to delete ID :: {}", answerId);
    }
}
