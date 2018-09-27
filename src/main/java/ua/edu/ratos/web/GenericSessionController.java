package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.domain.model.Result;
import ua.edu.ratos.domain.model.SessionData;
import ua.edu.ratos.service.GenericSessionService;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.BatchOut;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;

/**
 * 1) Long inactivity? Browser is opened.
 * 2) Browser is suddenly closed, authentication session lost... no data kept
 * 3) etc.
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class GenericSessionController {

    @Autowired
    private GenericSessionService sessionService;

    @GetMapping(value = "/test")
    public String test() {
        return "success";
    }

    /**
     * 1. Check is there a session opened? If so continue (send redirect to /next), if not- create
     * 2. Check if the scheme is available now;
     * 3. Generate the individual sequence
     * 4. Return first BatchOut
     * @param schemeId requested schemeId
     * @param session injected HttpSession obj
     * @param principal current authorized user
     * @return
     */
    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOut start(@RequestParam Long schemeId, HttpSession session, Principal principal) {
        if (session.getAttribute("sessionData")!=null)
            throw new RuntimeException("Learning session is already opened");
        final String start = sessionService.start("","");
        SessionData s = new SessionData("123","Andrey","#1", new ArrayList<>());
        session.setAttribute("sessionData", s);
        log.debug("SID :: {}", session.getId());
        return new BatchOut();
    }

    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOut next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchIn batchIn, Principal principal) {
        log.debug("SessionData :: {}", sessionData);
        return new BatchOut();
    }

    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result finish(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchIn batchIn, HttpSession session) {
        // Process final result
        session.removeAttribute("sessionData");
        return new Result();
    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        // Process current result
        session.removeAttribute("sessionData");
        log.debug("Cancelled");
        return new Result();
    }

}
