package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionsFileParserService questionsFileParserService;

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

    // For bulk doGameProcessing via file (as for now only applicable to MCQ)
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

    //--------------------------------------------------One for edit----------------------------------------------------
    // elsewhere out of editor
    @GetMapping(value = "/questions-mcq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionMCQOutDto findOneMCQForEditById(@PathVariable Long questionId) {
        return questionService.findOneMCQForEditById(questionId);
    }

    @GetMapping(value = "/questions-fbsq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionFBSQOutDto findOneFBSQForEditById(@PathVariable Long questionId) {
        return questionService.findOneFBSQForEditById(questionId);
    }

    @GetMapping(value = "/questions-fbmq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionFBMQOutDto findOneFBMQForEditById(@PathVariable Long questionId) {
        return questionService.findOneFBMQForEditById(questionId);
    }

    @GetMapping(value = "/questions-mq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionMQOutDto findOneMQForEditById(@PathVariable Long questionId) {
        return questionService.findOneMQForEditById(questionId);
    }

    @GetMapping(value = "/questions-sq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionSQOutDto findOneSQForEditById(@PathVariable Long questionId) {
        return questionService.findOneSQForEditById(questionId);
    }

    //--------------------------------------------------Staff editor----------------------------------------------------
    // 100 questions loaded by default, options are {100, 200, 500, all}
    @GetMapping(value = "/questions-mcq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllMCQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-fbsq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllFBSQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-fbmq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllFBMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-mq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMQOutDto> findAllFMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/questions-sq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllSQForEditByThemeId(themeId, pageable);
    }

    
    //--------------------------------------Staff cross-department search by questionType-------------------------------
    // 30 by default
    @GetMapping(value = "/questions-mcq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionMCQOutDto> findAllMCQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllMCQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/questions-fbsq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionFBSQOutDto> findAllFBSQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllFBSQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/questions-fbmq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionFBMQOutDto> findAllFBMQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllFBMQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/questions-mq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionMQOutDto> findAllMQForSearchByDepartmentIdAndTitleLetters(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllMQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/questions-sq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionSQOutDto> findAllSQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllSQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

}
