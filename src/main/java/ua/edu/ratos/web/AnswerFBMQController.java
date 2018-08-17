package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AnswerFBMQService;
import ua.edu.ratos.service.dto.entity.AnswerFBMQInDto;

/**
 * @link https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 */
@Slf4j
@RestController
public class AnswerFBMQController {

    @Autowired
    private AnswerFBMQService answerService;

    @PostMapping("/answer/fbmq")
    public void save(@Validated(AnswerFBMQInDto.New.class) @RequestBody AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @PutMapping("/answer/fbmq")
    public void update(@Validated(AnswerFBMQInDto.Update.class) @RequestBody AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @DeleteMapping("/answer/fbmq/{answerId}")
    public void delete(@PathVariable Long answerId) {
        log.info("Answer to delete ID :: {}", answerId);
    }
}
