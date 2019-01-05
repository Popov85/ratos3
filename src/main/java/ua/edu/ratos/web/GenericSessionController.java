package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.security.AuthenticatedUser;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.GenericSessionServiceImpl;
import javax.servlet.http.HttpSession;

/**
 * 1) Long inactivity? Browser is opened. (Default authentication session timeout 12 hours), no data to keep
 * 2) Case: browser is suddenly closed (PC trouble or electricity), authentication session lost... save data by tracking session lost event
 * 3) etc.
 */
@Slf4j
@RestController
@RequestMapping("/student/session")
public class GenericSessionController {

    @Autowired
    private GenericSessionServiceImpl sessionService;

    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto start(@RequestParam Long schemeId, HttpSession session, Authentication auth) {
        if (session.getAttribute("sessionData")!=null)
            throw new IllegalStateException("Learning session is not finished! Cancel previous session?");
        String key = session.getId();
        Long userId = ((AuthenticatedUser) auth.getPrincipal()).getUserId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId));
        session.setAttribute("sessionData", sessionData);
        log.debug("Started non-LMS session :: {} for user Id = {} taking scheme ID = {}", key, userId, schemeId);
        return sessionData.getCurrentBatch();
    }

    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in learning session:: {}", batchOut);
        return batchOut;

    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        // Process current result
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled learning session");
        return resultOut;
    }

    // Process final result
    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto finish(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Finished learning session");
        return resultOut;
    }

}
