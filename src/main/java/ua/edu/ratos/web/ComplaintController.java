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
import ua.edu.ratos.service.ComplaintService;
import ua.edu.ratos.service.dto.out.ComplaintOutDto;

@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @SuppressWarnings("SpellCheckingInspection")
    @DeleteMapping("/question-complaints/{questionId}/{ctypeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId, @PathVariable Long ctypeId) {
        complaintService.deleteById(questionId, ctypeId);
        log.debug("Deleted Complaint, questionId = {}, ctypeId = {}", questionId, ctypeId);
    }

    //---------------------------------------------------Staff table----------------------------------------------------
    // see full question to edit in QuestionController findOneForEditById set of end-points
    @GetMapping(value = "/question-complaints", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ComplaintOutDto> findAllByStaffId(@PageableDefault(sort = {"timesComplained"}, direction = Sort.Direction.DESC, value = 50) Pageable pageable) {
        return complaintService.findAllByDepartmentId(pageable);
    }

}
