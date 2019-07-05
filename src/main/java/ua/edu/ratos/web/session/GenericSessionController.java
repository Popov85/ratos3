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
import ua.edu.ratos.service.session.SessionDataMap;

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
        Object sessionDataAttribute = session.getAttribute("sessionDataMap");
        SessionDataMap sessionDataMap = ((sessionDataAttribute == null)
                ? new SessionDataMap() : (SessionDataMap) sessionDataAttribute);
        sessionDataMap.controlAndThrow(schemeId);
        String key = session.getId();
        Long userId = securityUtils.getAuthStudId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId));
        sessionDataMap.add(schemeId, sessionData);
        session.setAttribute("sessionDataMap", sessionDataMap);
        log.debug("Started non-LMS session key = {} for userId = {} taking schemeId = {}", key, userId, schemeId);
        return ResponseEntity.ok(sessionService.current(sessionData));
    }

    @ControlTime
    @PostMapping(value = "/next", params = "schemeId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> next(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap,
                                            @RequestBody BatchInDto batchInDto){
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in learning session = {}", batchOut);
        return ResponseEntity.ok(batchOut);
    }

    @ControlTime
    @GetMapping(value = "/current", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> current(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        return ResponseEntity.ok(sessionService.current(sessionData));
    }

    @ControlTime
    @PostMapping(value = "/finish-batch", params = "schemeId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap,
                                               @RequestBody BatchInDto batchInDto, HttpSession session) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.finish(batchInDto, sessionData);
        sessionDataMap.remove(schemeId);
        log.debug("Finished learning session with last batch evaluating, result = {}", resultOut);
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/finish", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        sessionDataMap.remove(schemeId);
        log.debug("Finished learning session without last batch evaluating");
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/cancel", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> cancel(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        sessionDataMap.remove(schemeId);
        log.debug("Cancelled learning session, {}", resultOut);
        return ResponseEntity.ok(resultOut);
    }

}
