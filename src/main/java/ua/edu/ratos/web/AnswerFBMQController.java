package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @RequestMapping("/save/answer/fbmq")
    public void save(@Validated(AnswerFBMQInDto.New.class) AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }

    @RequestMapping("/update/answer/fbmq")
    public void update(@Validated(AnswerFBMQInDto.Update.class) AnswerFBMQInDto dto) {
        log.info("Answer :: {}", dto);
    }
}
