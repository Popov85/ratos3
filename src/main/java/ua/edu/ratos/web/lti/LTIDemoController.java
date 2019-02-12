package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.edu.ratos.service.lti.LTIOutcomeService;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
@Profile("demo")
public class LTIDemoController {

    private LTIOutcomeService ltiOutcomeService;

    @Autowired
    public void setLtiOutcomeService(LTIOutcomeService ltiOutcomeService) {
        this.ltiOutcomeService = ltiOutcomeService;
    }

    @PostMapping("/lti/1p0/launch")
    public String launch(@RequestParam(required = false, defaultValue = "1") Long schemeId, Principal principal) {
        log.debug("Principal = {}", principal);
        return "redirect:/lms/session/test?schemeId="+schemeId;
    }

    @GetMapping("/lms/session/test")
    public String test(@RequestParam(required = false) Long schemeId, Authentication authentication, Model model) {
        log.debug("Started test, Authentication = {}", authentication);
        model.addAttribute("username", ((Principal) authentication.getPrincipal()).getName());
        model.addAttribute("schemeId", schemeId);
        return "start";
    }

    @PostMapping("/lms/session/post-score")
    @ResponseBody
    public String postScoreSpring(@RequestParam double score, @RequestParam Long schemeId, Authentication authentication, HttpServletRequest request) throws Exception {
        log.debug("Try to post score (%, like 60) = {}, for schemeId = {}", score, schemeId);
        ltiOutcomeService.sendOutcome(authentication, request.getScheme(), schemeId, score);
        return "OK";
    }
}
