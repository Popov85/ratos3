package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Slf4j
@Controller
public class SecurityController {
   // 1. Custom sign-in form
   // 2. Custom sign-up form
   // 3. Custom sign-up processing
   // 4. Custom access denied form
    @GetMapping("/access-denied")
    @ResponseBody
    public String denied(Principal principal) {
        log.debug("Principal ::" + principal);
        return "Access denied: not enough authority!";
    }
}
