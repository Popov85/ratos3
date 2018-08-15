package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.entity.AnswerFBMQInDto;

/**
 * @link https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 */
@Slf4j
@RestController
public class AnswerFBMQController {
    @Autowired
    private AnswerMCQService answerService;


    @PostMapping("/answer/save/fbmq")
    public void save(@Validated(AnswerFBMQInDto.New.class) @RequestBody AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @PutMapping("/answer/update/fbmq")
    public void update(@Validated(AnswerFBMQInDto.Update.class) @RequestBody AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }
}
