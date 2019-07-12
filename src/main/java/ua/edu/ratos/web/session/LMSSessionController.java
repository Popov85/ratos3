package ua.edu.ratos.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.config.ControlTime;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.lti.LTIOutcomeService;
import ua.edu.ratos.service.session.GenericSessionService;
import ua.edu.ratos.service.session.SessionDataMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/lms/session")
public class LMSSessionController {

    private GenericSessionService sessionService;

    private LTIOutcomeService ltiOutcomeService;

    @Autowired
    public void setSessionService(GenericSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setLtiOutcomeService(LTIOutcomeService ltiOutcomeService) {
        this.ltiOutcomeService = ltiOutcomeService;
    }

    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> start(@RequestParam Long schemeId, HttpSession session) {
        Object sessionDataAttribute = session.getAttribute("sessionDataMap");
        SessionDataMap sessionDataMap = ((sessionDataAttribute == null)
                ? new SessionDataMap() : (SessionDataMap) sessionDataAttribute);
        sessionDataMap.controlAndThrow(schemeId);
        String key = session.getId();
        final SessionData sessionData = sessionService.start(schemeId, key);
        sessionDataMap.add(schemeId, sessionData);
        session.setAttribute("sessionDataMap", sessionDataMap);
        log.debug("Started LMS sessionId = {} for a user taking schemeId = {}", key, schemeId);
        return ResponseEntity.ok(sessionData.getCurrentBatch().orElseThrow(()->new IllegalStateException("Current batch was not found!")));
    }


    @ControlTime
    @PostMapping(value = "/next", params = "schemeId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> next(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap,
                                            @RequestBody BatchInDto batchInDto) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in LMS session = {}", batchOut);
        return ResponseEntity.ok(batchOut);
    }

    @ControlTime
    @GetMapping(value = "/session/current", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> current(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        log.debug("Current batch in LMS session is requested");
        return ResponseEntity.ok(sessionService.current(sessionData));
    }

    @ControlTime
    @PostMapping(value = "/finish-batch", params = "schemeId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap,
                                               @RequestBody BatchInDto batchInDto, HttpServletRequest request) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.finish(batchInDto, sessionData);
        sessionDataMap.remove(schemeId);
        String protocol = request.getScheme();
        ltiOutcomeService.sendOutcome(schemeId, resultOut.getPercent(), protocol);
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @PostMapping(value = "/finish", params = "schemeId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap,
                                               HttpServletRequest request) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        sessionDataMap.remove(schemeId);
        String protocol = request.getScheme();
        ltiOutcomeService.sendOutcome(schemeId, resultOut.getPercent(), protocol);
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/cancel", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> cancel(@RequestParam Long schemeId, @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        sessionDataMap.remove(schemeId);
        log.debug("Cancelled LMS session");
        return ResponseEntity.ok(resultOut);
    }
}
