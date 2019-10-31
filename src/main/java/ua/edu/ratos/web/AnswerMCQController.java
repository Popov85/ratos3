package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.AnswerMCQService;
import ua.edu.ratos.service.dto.in.AnswerMCQInDto;
import ua.edu.ratos.service.validator.AnswerMCQInDtoValidator;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class AnswerMCQController {

    private final AnswerMCQInDtoValidator validator;

    private final AnswerMCQService answerService;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping(value = "/questions/{questionId}/answer-mcq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable Long questionId, @Validated @RequestBody AnswerMCQInDto dto) {
        final Long answerId = answerService.save(questionId, dto);
        log.debug("Saved Answer MCQ, answerId = {}", answerId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/questions/{questionId}/answers-mcq/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId, @PathVariable Long answerId, @Validated @RequestBody AnswerMCQInDto dto) {
        answerService.update(questionId, dto);
        log.debug("Updated Answer MCQ, answerId = {}", answerId);
    }

    @DeleteMapping("/questions/{questionId}/answers-mcq/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.debug("Deleted Answer MCQ from questionId = {}, answerId = {}", questionId, answerId);
    }
}
