package ua.edu.ratos.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.GenericSessionService;
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
        if (session.getAttribute("sessionData")!=null)
            throw new IllegalStateException("Learning session is not finished! Cancel previous session?");
        String key = session.getId();
        Long userId = securityUtils.getAuthStudId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId));
        session.setAttribute("sessionData", sessionData);
        log.debug("Started non-LMS session key = {} for userId = {} taking schemeId = {}", key, userId, schemeId);
        return ResponseEntity.ok(sessionData.getCurrentBatch().get());
    }

    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in learning session = {}", batchOut);
        return ResponseEntity.ok(batchOut);
    }

    @PostMapping(value = "/finish-batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto, HttpSession session) {
        final ResultOutDto resultOut = sessionService.finish(batchInDto, sessionData);
        session.removeAttribute("sessionData");
        log.debug("Finished learning session with last batch evaluating");
        return ResponseEntity.ok(resultOut);
    }

    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Finished learning session without last batch evaluating");
        return ResponseEntity.ok(resultOut);
    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled learning session");
        return ResponseEntity.ok(resultOut);
    }

}
