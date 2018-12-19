package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/lti")
public class LTIController {

    @PostMapping("/1p0/launch")
    public String launch(@RequestParam(required = false) Long schemeId, Principal principal) {
        log.debug("Principal ::" + principal);
        return "redirect:/student/start?schemeId="+schemeId;
    }
}
