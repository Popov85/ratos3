package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.generator.load.StartPerformance;

@Slf4j
@RestController
@Profile({"dev", "demo"})
public class PerformanceController {

    @Autowired
    private StartPerformance startPerformance;

    @GetMapping("/start-performance")
    public ResponseEntity<String> start(@RequestParam Long schemeId, @RequestParam int threads, @RequestParam long delay) {
        log.debug("About to start test for schemeId = {} with users quantity = {} with delay = {}", schemeId, threads, delay);
        startPerformance.doPerformanceTry(schemeId, threads, delay);
        return ResponseEntity.ok("Launched test for schemeId ="+schemeId+" with threads = "+threads+" with delay = "+delay);
    }

}
