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
@RequestMapping("/instructor/question/answer-fbmq")
public class AnswerFBMQController {

    @Autowired
    private AnswerFBMQService answerService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated(AnswerFBMQInDto.NewAndUpdate.class) @RequestBody AnswerFBMQInDto dto) {
        final Long answerId = answerService.save(dto);
        log.debug("Saved answer fbmq :: {} ", answerId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(answerId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long answerId, @Validated(AnswerFBMQInDto.NewAndUpdate.class) @RequestBody AnswerFBMQInDto dto) {
        answerService.update(answerId, dto);
        log.debug("Updated answer fbmq ID :: {} ", answerId);
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long answerId) {
        answerService.deleteById(answerId);
        log.debug("Deleted answer fbmq ID :: {}", answerId);
    }
}
