package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @link https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 */
@Slf4j
@RestController
@RequestMapping("/instructor/question/answer-mcq")
public class AnswerMCQController {

    @Autowired
    private AnswerMCQInDtoValidator validator;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private AnswerMCQService answerService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated(AnswerMCQInDto.NewAndUpdate.class) @RequestBody AnswerMCQInDto dto) {
        final Long answerId = answerService.save(dto);
        log.debug("Saved answer mcq :: {} ", answerId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long answerId, @Validated(AnswerMCQInDto.NewAndUpdate.class) @RequestBody AnswerMCQInDto dto) {
        answerService.update(answerId, dto);
        log.debug("Updated answer mcq ID :: {}", answerId);
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.info("Deleted answer mcq ID :: {}", answerId);
    }
}
