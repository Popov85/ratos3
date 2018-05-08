package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.SchedulerService;

@RestController
public class SchedulerController {
    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping("/allScheduledJobs")
    public String allScheduledJobs() {
        return "All scheduled jobs: "+ schedulerService.allScheduledJobs();
    }
}
