package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.domain.entity.Help;
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

    @PostMapping("/answer/save/mcq")
    public void save(@Validated(AnswerMCQInDto.New.class) @RequestBody AnswerMCQInDto dto, BindingResult result) {
        validator.validate(dto, result);
        if (result.hasErrors()) {
            log.error("Error :: "+result.getAllErrors()+" dto :: "+dto);
        } else {
            log.info("Answer :: {}", dto);
        }
    }

    @PutMapping("/answer/update/mcq")
    public void update(@Validated(AnswerMCQInDto.Update.class) @RequestBody AnswerMCQInDto dto) {
        log.info("Answer :: {}", dto);
    }


/*    @PostMapping("/answer/save/help")
    public void save1(@RequestParam("helpId") Help help) {
        log.info("Help :: {}", help);
    }*/
}
