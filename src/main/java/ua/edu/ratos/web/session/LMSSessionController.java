package ua.edu.ratos.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.config.ControlTime;
import ua.edu.ratos.security.RatosUser;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.lti.LTIOutcomeService;
import ua.edu.ratos.service.session.GenericSessionService;
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
    public ResponseEntity<BatchOutDto> start(@RequestParam Long schemeId, HttpSession session, Authentication auth) {
        if (session.getAttribute("sessionData")!=null)
            throw new IllegalStateException("Learning session is not finished! Cancel previous session?");
        RatosUser principal = (RatosUser) auth.getPrincipal();
        String key = session.getId();
        Long lmsId = principal.getLmsId();
        Long userId = principal.getUserId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId, lmsId));
        session.setAttribute("sessionData", sessionData);
        log.debug("Started LMS sessionId = {} for userId = {} from within lmsId = {}, taking schemeId = {}", key, userId, lmsId, schemeId);
        return ResponseEntity.ok(sessionData.getCurrentBatch().get());
    }

    @ControlTime
    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in LMS session = {}", batchOut);
        return ResponseEntity.ok(batchOut);
    }

    @ControlTime
    @GetMapping(value = "/session/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchOutDto> current(@SessionAttribute("sessionData") SessionData sessionData) {
        log.debug("Current batch in LMS session is requested");
        return ResponseEntity.ok(sessionService.current(sessionData));
    }

    @ControlTime
    @PostMapping(value = "/finish-batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto, Authentication authentication, HttpSession session, HttpServletRequest request) {
        final ResultOutDto resultOut = sessionService.finish(batchInDto, sessionData);
        session.removeAttribute("sessionData");
        String protocol = request.getScheme();
        Long schemeId = sessionData.getSchemeDomain().getSchemeId();
        ltiOutcomeService.sendOutcome(authentication, protocol, schemeId, resultOut.getPercent());
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> finish(@SessionAttribute("sessionData") SessionData sessionData, Authentication authentication, HttpSession session, HttpServletRequest request) {
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        session.removeAttribute("sessionData");
        String protocol = request.getScheme();
        Long schemeId = sessionData.getSchemeDomain().getSchemeId();
        ltiOutcomeService.sendOutcome(authentication, protocol, schemeId, resultOut.getPercent());
        return ResponseEntity.ok(resultOut);
    }

    @ControlTime
    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutDto> cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled LMS session");
        return ResponseEntity.ok(resultOut);
    }
}
