package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.edu.ratos.service.lti.LTILaunchService;

@Slf4j
@Controller
@RequestMapping("/instructor")
public class LTIController {

    @Autowired
    private LTILaunchService ltiLaunchService;

    @GetMapping("/launch-url")
    @ResponseBody
    public String getLaunchURL(@RequestParam(required = false) Long schemeId) {
        return ltiLaunchService.getLaunchURL(schemeId);
    }

}
