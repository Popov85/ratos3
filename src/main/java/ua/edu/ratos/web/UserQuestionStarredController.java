package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.session.UserQuestionStarredService;

@Slf4j
@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class UserQuestionStarredController {

    private final UserQuestionStarredService userQuestionStarredService;

    @DeleteMapping("/questions-starred/{questionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId) {
        userQuestionStarredService.deleteById(questionId);
        log.debug("Deleted Starred question, questionId = {}", questionId);
    }

    //-------------------------------------------------Question details-------------------------------------------------
    // no correct answer(s)(!), only for educational purpose
    @GetMapping(value = "/questions-starred/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionSessionOutDto findOneStarredQuestionById(@PathVariable Long questionId) {
        return userQuestionStarredService.findOneByQuestionId(questionId);
    }

    //----------------------------------------------------User table----------------------------------------------------
    // default limit is 100(!)
    @GetMapping(value = "/questions-starred", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<QuestionSessionMinOutDto> findAllStarredQuestionsByStudentId(@PageableDefault(sort = {"whenStarred"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return userQuestionStarredService.findAllByUserId(pageable);
    }
}
