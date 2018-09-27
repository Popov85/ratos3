package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.AnswerMQService;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.entity.AnswerMQInDto;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor/question/answer-mq")
public class AnswerMQController {

    @Autowired
    private AnswerMQService answerService;

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated(AnswerMQInDto.NewAndUpdate.class) @RequestBody AnswerMQInDto dto) {
        final Long answerId = answerService.save(dto);
        log.debug("Saved answer mq :: {} ", answerId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long answerId, @Validated(AnswerMQInDto.NewAndUpdate.class) @RequestBody AnswerMQInDto dto) {
        answerService.update(answerId, dto);
        log.debug("Updated answer mq ID :: {}", answerId);
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.info("Deleted answer mq ID :: {}", answerId);
    }

}
