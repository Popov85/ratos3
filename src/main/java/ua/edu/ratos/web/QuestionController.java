package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.QuestionsFileParserService;
import ua.edu.ratos.service.dto.entity.*;
import ua.edu.ratos.service.dto.view.QuestionsParsingResultOutDto;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping(path = "/instructor/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionsFileParserService questionsFileParserService;


    @PostMapping(value = "/mcq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated({AnswerMCQInDto.Include.class}) @RequestBody QuestionMCQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved question mcq :: {} ", questionId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/fbsq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody QuestionFBSQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved question fbsq :: {} ", questionId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/fbmq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated({AnswerFBMQInDto.Include.class}) @RequestBody QuestionFBMQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved question fbmq :: {} ", questionId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/mq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated({AnswerMQInDto.Include.class}) @RequestBody QuestionMQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved question mq :: {} ", questionId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping(value = "/sq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated({AnswerSQInDto.Include.class}) @RequestBody QuestionSQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved question sq :: {} ", questionId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionsParsingResultOutDto saveAll(@RequestParam("file") MultipartFile multipartFile,
                                                @RequestParam Long themeId, @RequestParam Long langId,
                                                @RequestParam boolean confirmed) throws IOException {
        return questionsFileParserService.parseAndSave(multipartFile, new FileInDto(themeId, langId, 1L, confirmed));
    }


    @PutMapping(value = "/{questionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId,  @Valid @RequestBody QuestionInDto dto) {
        questionService.update(questionId, dto);
        log.debug("Updated Question ID :: {} ", questionId);
    }

    @DeleteMapping("/{questionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId) {
        questionService.deleteById(questionId);
        log.debug("Deleted question ID :: {}", questionId);
    }

    /*--------------------GET-------------------------*/

    @GetMapping(value = "/", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Question> findAllByThemeId(@RequestParam Long themeId) {
        return questionService.findAllByThemeId(themeId);
    }

    @GetMapping(value = "/", params = {"themeId", "typeId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<? extends Question> findAllByThemeIdAndTypeId(@RequestParam Long themeId, @RequestParam Long typeId) {
        return questionService.findAllByThemeIdAndTypeId(themeId, typeId);
    }

}
