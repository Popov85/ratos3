package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.security.RatosUser;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.lti.LTIOutcomeService;
import ua.edu.ratos.service.session.GenericSessionLMSServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/lms/session")
public class GenericSessionLMSController {

    @Autowired
    private GenericSessionLMSServiceImpl sessionService;

    @Autowired
    private LTIOutcomeService ltiOutcomeService;

    @GetMapping(value = "/start", params = "schemeId", produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto start(@RequestParam Long schemeId, HttpSession session, Authentication auth) {
        if (session.getAttribute("sessionData")!=null)
            throw new IllegalStateException("Learning session is not finished! Cancel previous session?");
        RatosUser principal = (RatosUser) auth.getPrincipal();
        String key = session.getId();
        Long lmsId = principal.getLmsId();
        Long userId = principal.getUserId();
        final SessionData sessionData = sessionService.start(new StartData(key, schemeId, userId, lmsId));
        session.setAttribute("sessionData", sessionData);
        log.debug("Started LMS session :: {} for user Id = {} from within LMS ID = {}, taking scheme ID = {}", key, userId, lmsId, schemeId);
        return sessionData.getCurrentBatch();
    }

    @PostMapping(value = "/next", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BatchOutDto next(@SessionAttribute("sessionData") SessionData sessionData, @RequestBody BatchInDto batchInDto) {
        BatchOutDto batchOut = sessionService.next(batchInDto, sessionData);
        log.debug("Next batch in LMS session :: {}", batchOut);
        return batchOut;

    }

    @GetMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto cancel(@SessionAttribute("sessionData") SessionData sessionData, HttpSession session) {
        // Process current result
        final ResultOutDto resultOut = sessionService.cancel(sessionData);
        session.removeAttribute("sessionData");
        log.debug("Cancelled LMS session");
        return resultOut;
    }

    // Process final result
    @PostMapping(value = "/finish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultOutDto finish(@SessionAttribute("sessionData") SessionData sessionData, Authentication authentication, HttpSession session, HttpServletRequest request) {
        final ResultOutDto resultOut = sessionService.finish(sessionData);
        session.removeAttribute("sessionData");
        String protocol = request.getScheme();
        Long schemeId = sessionData.getSchemeDomain().getSchemeId();
        ltiOutcomeService.sendOutcome(authentication, protocol, schemeId, resultOut.getPercent());
        log.debug("Finished LMS session, outcome is sent to LMS server");
        return resultOut;
    }
}
