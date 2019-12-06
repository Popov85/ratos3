package ua.edu.ratos.web.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class SecurityController {

    // Custom login form
    @GetMapping(value = "/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        @RequestParam(name= "logout", required = false) String logout) {
        return "login";
    }

   // Custom access denied form
    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied";
    }
}
