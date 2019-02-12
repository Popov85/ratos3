package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Slf4j
@Controller
@Profile("prod")
public class LTILaunchController {

    @PostMapping("/lti/1p0/launch")
    public String launch(@RequestParam Long schemeId, Principal principal) {
        log.debug("Principal = {}", principal);
        return "redirect:/lms/session/start?schemeId="+schemeId;
    }
}
