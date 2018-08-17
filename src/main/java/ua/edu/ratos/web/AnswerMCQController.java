package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.entity.AnswerMCQInDto;
import ua.edu.ratos.service.dto.validator.AnswerMCQInDtoValidator;

/**
 * @link https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 */
@Slf4j
@RestController
public class AnswerMCQController {

    @Autowired
    private AnswerMCQInDtoValidator validator;

    @Autowired
    private AnswerMCQService answerService;

    @PostMapping("/answer/mcq")
    public Long save(@Validated(AnswerMCQInDto.New.class) @RequestBody AnswerMCQInDto dto, BindingResult result) {
        validator.validate(dto, result);
        if (result.hasErrors()) {
            log.error("Error :: "+result.getAllErrors()+" dto :: "+dto);
        } else {
            log.info("Answer :: {}", dto);
        }
        return 1L;
    }

    @PutMapping("/answer/mcq")
    public void update(@Validated(AnswerMCQInDto.Update.class) @RequestBody AnswerMCQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @DeleteMapping("/answer/mcq/{answerId}")
    public void delete(@PathVariable Long answerId) {
        log.info("Answer to delete ID :: {}", answerId);
    }
}
