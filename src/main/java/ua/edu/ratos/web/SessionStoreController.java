package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.SessionStoreService;

@RestController
public class SessionStoreController {

    @Autowired
    private SessionStoreService sessionStoreService;


    @RequestMapping("/sessions/findAll")
    public String allCurrentSessions() {
        return "All current sessions: "+ sessionStoreService.findAll();
    }

    @RequestMapping("/sessions/removeAll")
    public String removeCurrentSessions() {
        sessionStoreService.removeAll();
        return "All current sessions: "+ sessionStoreService.findAll();
    }


}
