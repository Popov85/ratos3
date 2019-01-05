package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.security.AuthenticatedUser;
import ua.edu.ratos.service.session.EducationalSessionService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/student/session")
public class EducationalSessionController {

    @Autowired
    private EducationalSessionService educationalSessionService;

    @PostMapping(value = "/preserve")
    public String preserve(@SessionAttribute("sessionData") SessionData sessionData) {
        String key = educationalSessionService.preserve(sessionData);
        log.debug("Preserved session :: {}", key);
        return key;
    }

    @PostMapping(value = "/retrieve/{key}")
    public BatchOutDto retrieve(@PathVariable String key, HttpSession session, Principal principal) {
        if (session.getAttribute("sessionData")!=null)
            throw new RuntimeException("Learning session is already opened");
        SessionData sessionData = educationalSessionService.retrieve(key);
        Long requestingUserId = ((AuthenticatedUser) principal).getUserId();
        Long actualUserId = sessionData.getUserId();
        if (!requestingUserId.equals(actualUserId))
            throw new AccessDeniedException("Requested session was created by a different user");
        session.setAttribute("sessionData", sessionData);
        log.debug("Retrieved and set SessionData");
        return sessionData.getCurrentBatch();
    }
}
