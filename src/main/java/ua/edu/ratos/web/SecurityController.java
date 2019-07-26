package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SecurityController {
   // 1. Custom sign-in form
   // 2. Custom sign-up form
   // 3. Custom sign-up processing
   // 4. Custom access denied form
    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied";
    }
}
