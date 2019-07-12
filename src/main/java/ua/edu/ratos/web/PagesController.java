package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class PagesController {

    @GetMapping(value = "/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        @RequestParam(name= "logout", required = false) String logout) {
        log.debug("Error = {} Logout = {}", error, logout);
        return "login";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/student/start")
    public String getStartPage(@RequestParam Long schemeId) {
        return "index";
    }


    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

}
