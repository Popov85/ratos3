package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Also see for /access-denied mapping
 * @see SecurityController
 */
@Slf4j
@Controller
public class PagesController {

    @GetMapping("/student/start")
    public String getStartPage(@RequestParam Long schemeId) {
        return "index";
    }


    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

}
