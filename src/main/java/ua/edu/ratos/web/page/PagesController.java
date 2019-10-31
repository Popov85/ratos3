package ua.edu.ratos.web.page;

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
    public String getSessionPage(@RequestParam Long schemeId) {
        return "session";
    }


    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("/staff")
    public String getStaffPage() {
        return "staff";
    }

    @GetMapping("/student")
    public String getStudentPage() {
        return "student";
    }

}
