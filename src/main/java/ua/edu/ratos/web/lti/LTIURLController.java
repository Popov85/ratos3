package ua.edu.ratos.web.lti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.lti.LTILaunchService;

/**
 * Used by instructors to obtain the actual launch URL for a given schemeId
 * to be inserted into LMS's LTI settings input field when adjusting LTI service
 */
@Slf4j
@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class LTIURLController {

    private final LTILaunchService ltiLaunchService;

    @GetMapping("/schemes/{schemeId}/launch-url")
    public String getLaunchURL(@PathVariable Long schemeId) {
        return ltiLaunchService.getLaunchURL(schemeId);
    }

}
