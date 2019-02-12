package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.lti.LTILaunchService;

/**
 * Used by instructors to obtain the actual launch URL for a given schemeId
 * to be inserted into LMS's LTI settings input field when adjusting LTI service
 */
@Slf4j
@Controller
@RequestMapping("/instructor")
public class LTIURLController {

    private LTILaunchService ltiLaunchService;

    @Autowired
    public void setLtiLaunchService(LTILaunchService ltiLaunchService) {
        this.ltiLaunchService = ltiLaunchService;
    }

    @GetMapping("/schemes/{schemeId}/launch-url")
    @ResponseBody
    public String getLaunchURL(@PathVariable Long schemeId) {
        return ltiLaunchService.getLaunchURL(schemeId);
    }

}
