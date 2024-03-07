package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.AnswerSQService;
import ua.edu.ratos.service.dto.in.AnswerSQInDto;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class AnswerSQController {

    private final AnswerSQService answerService;

    @PostMapping(value = "/questions-sq/{questionId}/answers-sq",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable Long questionId, @Validated @RequestBody AnswerSQInDto dto) {
        final Long answerId = answerService.save(questionId, dto);
        log.debug("Saved Answer SQ for questionId = {}, answerId = {}", questionId, answerId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    // Make sure to include answerId to DTO object to be able to update
    @PutMapping("/questions-sq/{questionId}/answers-sq/{answerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId, @PathVariable Long answerId, @Validated @RequestBody AnswerSQInDto dto) {
        answerService.update(questionId, dto);
        log.debug("Updated Answer SQ for questionId = {}, answerId = {}", questionId, answerId);
    }

    @DeleteMapping("/questions-sq/{questionId}/answers-sq/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.debug("Deleted Answer SQ from questionId = {}, answerId = {}", questionId, answerId);
    }
}
