package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.AnswerMQService;
import ua.edu.ratos.service.dto.in.AnswerMQInDto;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class AnswerMQController {

    private AnswerMQService answerService;

    @Autowired
    public void setAnswerService(AnswerMQService answerService) {
        this.answerService = answerService;
    }

    @PostMapping(value = "/questions-mq/{questionId}/answers-mq",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable Long questionId, @Validated @RequestBody AnswerMQInDto dto) {
        final Long answerId = answerService.save(questionId, dto);
        log.debug("Saved Answer MQ for questionId = {}, answerId = {}", questionId, answerId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    // Make sure to include answerId to DTO object to be able to update
    @PutMapping(value = "/questions-mq/{questionId}/answers-mq/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId, @PathVariable Long answerId, @Validated @RequestBody AnswerMQInDto dto) {
        answerService.update(questionId, dto);
        log.debug("Updated Answer MQ for questionId = {}, answerId = {}", questionId, answerId);
    }

    @DeleteMapping("/questions-mq/{questionId}/answers-mq/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.info("Deleted Answer MQ from questionId = {}, answerId = {}", questionId, answerId);
    }

}
