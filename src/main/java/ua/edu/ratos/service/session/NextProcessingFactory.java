package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NextProcessingFactory {

    private List<NextProcessingService> nextProcessingServices;

    @Autowired
    public void setNext(List<NextProcessingService> nextProcessingServiceServices) {
        this.nextProcessingServices = nextProcessingServiceServices;
    }

    private final Map<String, NextProcessingService> nextMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(NextProcessingService nextProcessingService : nextProcessingServices) {
            nextMap.put(nextProcessingService.type(), nextProcessingService);
        }
    }

    public NextProcessingService getNextProcessingService(String type) {
        NextProcessingService nextProcessingService = nextMap.get(type);
        if(nextProcessingService == null) throw new RuntimeException("Unknown nextProcessingService type: " + type);
        return nextProcessingService;
    }
}
