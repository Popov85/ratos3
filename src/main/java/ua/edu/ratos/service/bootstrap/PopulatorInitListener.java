package ua.edu.ratos.service.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.generator.suite.SessionSuite;

@Slf4j
@Service
@Profile({"dev", "demo"})
public class PopulatorInitListener {

    @Value("${ratos.init.populate}")
    private boolean populate;

    @Autowired
    private SessionSuite sessionSuite;

    @Order(3)
    @EventListener(ContextRefreshedEvent.class)
    public void populate() {
        // Work here to change populating options
        if (populate) {
            log.debug("Starting to populate DB with test data...");
            sessionSuite.generateMin();
            log.debug("Finished populating DB with test data...");
            return;
        }
        log.debug("No test data populating on start-up..");
    }
}
