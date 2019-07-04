package ua.edu.ratos.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.config.ControlTime;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.GenericSessionService;
import ua.edu.ratos.web.exception.SessionAlreadyOpenedException;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/student/session")
public class GenericSessionController {

    private GenericSessionService sessionService;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSessionService(GenericSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> start(@RequestParam Long schemeId, HttpSession session) {
        if (session.getAttribute("sessionData")!=null) {
            Object sessionData = session.getAttribute("sessionData");
            throw new SessionAlreadyOpenedException(((SessionData) sessionData).getSchemeDomain().getSchemeId());
        }
        String key = session.getId();
        Long userId = securityUtils.getAuthStudId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId));
        session.setAttribute("sessionData", sessionData);
        log.debug("Started non-LMS session key = {} for userId = {} taking schemeId = {}", key, userId, schemeId);
        return ResponseEntity.ok(sessionData.getCurrentBatch().get());
    }

    @ControlTime
    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in learning session = {}", batchOut);
        return ResponseEntity.ok(batchOut);
    }

    @ControlTime
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> current(@SessionAttribute("sessionData") SessionData sessionData) {
        return ResponseEntity.ok(sessionService.current(sessionData));
    }

    @ControlTime
    @PostMapping(value = "/finish-batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto, HttpSession session) {
        final ResultOutDto resultOut = sessionService.finish(batchInDto, sessionData);
        session.removeAttribute("sessionData");
        log.debug("Finished learning session with last batch evaluating, result = {}", resultOut);
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Finished learning session without last batch evaluating");
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled learning session, {}", resultOut);
        return ResponseEntity.ok(resultOut);
    }

}
