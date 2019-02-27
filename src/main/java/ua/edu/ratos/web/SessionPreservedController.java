package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.SessionPreservedService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.out.SessionPreservedOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/student")
public class SessionPreservedController {

    private SessionPreservedService sessionPreservedService;

    @Autowired
    public void setSessionPreservedService(SessionPreservedService sessionPreservedService) {
        this.sessionPreservedService = sessionPreservedService;
    }

    //----------------------------------------------Retrieve and continue-----------------------------------------------

    @GetMapping(value = "/sessions-preserved/{key}")
    public BatchOutDto retrieve(@PathVariable String key, HttpSession session) {
        if (session.getAttribute("sessionData")!=null) throw new RuntimeException("Learning session is already opened");
        SessionData sessionData = sessionPreservedService.retrieve(key);
        session.setAttribute("sessionData", sessionData);
        log.debug("Retrieved SessionData with key = {} and returned current BatchOut", key);
        return sessionData.getCurrentBatch().get();
    }

    //----------------------------------------------------Remove--------------------------------------------------------

    @DeleteMapping("/sessions-preserved/{key}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String key) {
        sessionPreservedService.deleteById(key);
        log.debug("Deleted preserved Session, key = {}", key);
    }

    //--------------------------------------------------User table------------------------------------------------------

    @GetMapping(value = "/sessions-preserved", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SessionPreservedOutDto> findAllByUserId(@PageableDefault(sort = {"whenPreserved"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        return sessionPreservedService.findAllByUserId(pageable);
    }
}
