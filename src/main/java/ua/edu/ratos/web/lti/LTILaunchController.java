package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@Profile({"prod", "stage"})
public class LTILaunchController {

    @GetMapping("/lms/start")
    public String getStartPage(@RequestParam Long schemeId) {
        return "session?schemeId="+schemeId;
    }

    @PostMapping("/lti/1p0/launch")
    public String launch(@RequestParam Long schemeId) {
        log.debug("POST via LTI v1.1 is performed, requested schemeId = {}", schemeId);
        return "redirect:/lms/start?schemeId="+schemeId;
    }
}
