package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartProcessingFactory {

    private List<StartProcessingService> startProcessingServices;

    @Autowired
    public void setStart(List<StartProcessingService> startProcessingServiceServices) {
        this.startProcessingServices = startProcessingServiceServices;
    }

    private final Map<String, StartProcessingService> startMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(StartProcessingService startProcessingService : startProcessingServices) {
            startMap.put(startProcessingService.type(), startProcessingService);
        }
    }

    public StartProcessingService getStartProcessingService(String type) {
        StartProcessingService startProcessingService = startMap.get(type);
        if(startProcessingService == null) throw new RuntimeException("Unknown startProcessingService type: " + type);
        return startProcessingService;
    }
}
