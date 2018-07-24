package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.GenericSession;
import ua.edu.ratos.service.dto.session.BatchOut;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
@RestController
public class GenericSessionController {

    private final GenericSession sessionService;

    @Autowired
    public GenericSessionController(GenericSession sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping("/start")
    public String start(@RequestParam String user, @RequestParam String scheme) {
        return "Session key: "+sessionService.start(user, scheme);
    }

    @RequestMapping("/proceed")
    public BatchOut proceed(@RequestParam String key) {
        return sessionService.proceed(key);
    }


    // https://github.com/denverdino/docker-spring-boot-sample-session-redis/blob/master/src/main/java/sample/session/redis/HelloRestController.java
    @GetMapping("/uuid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return uid.toString();
    }

}
