package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
import ua.edu.ratos.service.session.dto.ResultOutDto;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.security.AuthenticatedUser;
import ua.edu.ratos.service.session.GenericSessionService;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * 1) Long inactivity? Browser is opened. (Default authentication session timeout 2 hours), no data to keep
 * 2) TODO Case: browser is suddenly closed (PC trouble or electricity), authentication dto lost... save data by tracking session lost event
 * 3) etc.
 */
@Slf4j
@RestController
@RequestMapping("/student/session")
public class GenericSessionController {

    @Autowired
    private GenericSessionService sessionService;

    @GetMapping(value = "/test")
    public String test() {
        return "Some data...";
    }


    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto start(@RequestParam Long schemeId, HttpSession session, Principal principal) {
        if (session.getAttribute("sessionData")!=null)
            throw new RuntimeException("Learning session is already opened");
        final SessionData sessionData = sessionService.start(session.getId(), ((AuthenticatedUser) principal).getUserId(), 1L);
        session.setAttribute("sessionData", sessionData);
        log.debug("Started :: {}", session.getId());
        return sessionData.getCurrentBatch();
    }

    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch :: {}", batchOut);
        return batchOut;

    }

    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto finish(@SessionAttribute(value = "sessionData") SessionData sessionData, HttpSession session) {
        // Process final result
        final ResultOutDto resultOut = sessionService.finish(sessionData, false);
        session.removeAttribute("sessionData");
        log.debug("Finished");
        return resultOut;
    }
    /**
     * Client script invokes this method as a result of exception handling in case of business time out
     * (not auth. session timeout)
     * @param sessionData
     * @param session
     * @return
     */
    @PostMapping(value = "/finish-timeout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto finishTimeout(@SessionAttribute(value = "sessionData") SessionData sessionData, HttpSession session) {
        // Process final result
        final ResultOutDto resultOut = sessionService.finish(sessionData, true);
        session.removeAttribute("sessionData");
        log.debug("Time-outed");
        return resultOut;
    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        // Process current result
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled");
        return resultOut;
    }
}
