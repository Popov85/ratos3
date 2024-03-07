package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.PagedQuestionService;
import ua.edu.ratos.service.dto.out.question.*;

@Slf4j
@RestController
@AllArgsConstructor
public class PagedQuestionController {

    private final PagedQuestionService questionService;

    //--------------------------------------------------One for edit----------------------------------------------------
    // elsewhere out of editor
    @GetMapping(value = "/instructor/questions-mcq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionMCQOutDto findOneMCQForEditById(@PathVariable Long questionId) {
        return questionService.findOneMCQForEditById(questionId);
    }

    @GetMapping(value = "/instructor/questions-fbsq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionFBSQOutDto findOneFBSQForEditById(@PathVariable Long questionId) {
        return questionService.findOneFBSQForEditById(questionId);
    }

    @GetMapping(value = "/instructor/questions-fbmq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionFBMQOutDto findOneFBMQForEditById(@PathVariable Long questionId) {
        return questionService.findOneFBMQForEditById(questionId);
    }

    @GetMapping(value = "/instructor/questions-mq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionMQOutDto findOneMQForEditById(@PathVariable Long questionId) {
        return questionService.findOneMQForEditById(questionId);
    }

    @GetMapping(value = "/instructor/questions-sq/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionSQOutDto findOneSQForEditById(@PathVariable Long questionId) {
        return questionService.findOneSQForEditById(questionId);
    }

    //--------------------------------------------------Staff editor----------------------------------------------------

    @GetMapping(value = "/department/questions-mcq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMCQOutDto> findAllMCQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllMCQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/department/questions-fbsq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBSQOutDto> findAllFBSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllFBSQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/department/questions-fbmq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionFBMQOutDto> findAllFBMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllFBMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/department/questions-mq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionMQOutDto> findAllFMQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllMQForEditByThemeId(themeId, pageable);
    }

    @GetMapping(value = "/department/questions-sq", params = "themeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionSQOutDto> findAllSQForEditByThemeId(@RequestParam Long themeId, @PageableDefault(sort = {"question"}, value = 100) Pageable pageable) {
        return questionService.findAllSQForEditByThemeId(themeId, pageable);
    }

    
    //--------------------------------------Staff cross-department search by questionType-------------------------------
    // 30 by default
    @GetMapping(value = "/department/questions-mcq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionMCQOutDto> findAllMCQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllMCQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/department/questions-fbsq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionFBSQOutDto> findAllFBSQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllFBSQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/department/questions-fbmq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionFBMQOutDto> findAllFBMQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllFBMQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/department/questions-mq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionMQOutDto> findAllMQForSearchByDepartmentIdAndTitleLetters(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllMQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

    @GetMapping(value = "/department/questions-sq", params = "letters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<QuestionSQOutDto> findAllSQForSearchByDepartmentIdAndTitleContains(@RequestParam String letters, @PageableDefault(sort = {"question"}, value = 30) Pageable pageable) {
        return questionService.findAllSQForSearchByDepartmentIdAndTitleContains(letters, pageable);
    }

}
