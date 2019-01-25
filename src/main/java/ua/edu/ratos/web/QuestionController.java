package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.service.QuestionService;
import ua.edu.ratos.service.QuestionsFileParserService;
import ua.edu.ratos.service.dto.in.*;
import ua.edu.ratos.service.dto.out.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.dto.out.question.*;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(path = "/instructor")
public class QuestionController {

    private QuestionService questionService;

    private QuestionsFileParserService questionsFileParserService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setQuestionsFileParserService(QuestionsFileParserService questionsFileParserService) {
        this.questionsFileParserService = questionsFileParserService;
    }

    @PostMapping(value = "/questions-mcq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody QuestionMCQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved Question MCQ, questionId = {}", questionId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/questions-fbsq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody QuestionFBSQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved Question FBSQ, questionId = {}", questionId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/questions-fbmq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody QuestionFBMQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved Question FBMQ, questionId = {}", questionId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/questions-mq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody QuestionMQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved Question MQ, questionId = {}", questionId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/questions-sq", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Validated @RequestBody QuestionSQInDto dto) {
        final Long questionId = questionService.save(dto);
        log.debug("Saved Question SQ, questionId = {}", questionId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    // For bulk save via file (as for now only applicable to MCQ)
    @PostMapping(value = "/questions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionsParsingResultOutDto saveAll(@RequestParam("file") MultipartFile multipartFile,
                                                @RequestParam Long themeId, @RequestParam Long langId,
                                                @RequestParam boolean confirmed) throws IOException {
        return questionsFileParserService.parseAndSave(multipartFile, new FileInDto(themeId, langId, 1L, confirmed));
    }

    @PutMapping(value = "/questions/{questionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long questionId, @Valid @RequestBody QuestionInDto dto) {
        questionService.update(questionId, dto);
        log.debug("Updated Question, questionId = {}", questionId);
    }

    @DeleteMapping("/questions/{questionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId) {
        questionService.deleteById(questionId);
        log.debug("Deleted Question, questionId = {}", questionId);
    }


    //----------------------------------------GET for instructor edit--------------------------------------

    @GetMapping(value = "/questions-mcq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllMCQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-fbsq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllFBSQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-fbmq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllFBMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-mq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMQOutDto> findAllFMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-sq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllSQForEditByThemeId(themeId, pageable);
    }

    //----------------------------------------GET for instructor search--------------------------------------

    @GetMapping(value = "/questions-mcq", params = {"themeId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeIdAndQuestionLettersContains(@RequestParam Long themeId, @RequestParam String letters, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllMCQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable);
    }

    @GetMapping(value = "/questions-fbsq", params = {"themeId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeIdAndQuestionLettersContains(@RequestParam Long themeId, @RequestParam String letters, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllFBSQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable);
    }

    @GetMapping(value = "/questions-fbmq", params = {"themeId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeIdAndQuestionLettersContains(@RequestParam Long themeId, @RequestParam String letters, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllFBMQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable);
    }

    @GetMapping(value = "/questions-mq", params = {"themeId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMQOutDto> findAllFMQForEditByThemeIdAndQuestionLettersContains(@RequestParam Long themeId, @RequestParam String letters, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllMQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable);
    }

    @GetMapping(value = "/questions-sq", params = {"themeId", "letters"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeIdAndQuestionLettersContains(@RequestParam Long themeId, @RequestParam String letters, @PageableDefault(sort = {"question"}, value = 50) Pageable pageable) {
        return questionService.findAllSQForEditByThemeIdAndQuestionLettersContains(themeId, letters, pageable);
    }

}
