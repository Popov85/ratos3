package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.AnswerFBMQService;
import ua.edu.ratos.service.dto.in.AnswerFBMQInDto;

import java.net.URI;

/**
 * @link https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 */
@Slf4j
@RestController
@RequestMapping("/instructor")
public class AnswerFBMQController {

    private AnswerFBMQService answerService;

    @Autowired
    public void setAnswerService(AnswerFBMQService answerService) {
        this.answerService = answerService;
    }

    @PostMapping(value = "/questions/{questionId}/answers-fbmq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable Long questionId, @Validated @RequestBody AnswerFBMQInDto dto) {
        final Long answerId = answerService.save(questionId, dto);
        log.debug("Saved Answer FBMQ for questionId = {}, answerId = {}", questionId, answerId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    // Make sure to include answerId to DTO object to be able to update
    @PutMapping(value = "/questions/{questionId}/answers-fbmq/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId, @PathVariable Long answerId, @Validated @RequestBody AnswerFBMQInDto dto) {
        answerService.update(questionId, dto);
        log.debug("Updated Answer FBMQ for questionId = {}, answerId = {}", questionId, answerId);
    }

    @DeleteMapping("/questions/{questionId}/answers-fbmq/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.debug("Deleted Answer FBMQ from questionId = {}, answerId = {}", questionId, answerId);
    }
}
