package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FinishProcessingFactory {

    private List<FinishProcessingService> finishProcessingServices;

    @Autowired
    public void setFinish(List<FinishProcessingService> finishProcessingServiceServices) {
        this.finishProcessingServices = finishProcessingServiceServices;
    }

    private final Map<String, FinishProcessingService> finishMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for(FinishProcessingService finishProcessingService : finishProcessingServices) {
            finishMap.put(finishProcessingService.type(), finishProcessingService);
        }
    }

    public FinishProcessingService getFinishProcessingService(String type) {
        FinishProcessingService finishProcessingService = finishMap.get(type);
        if(finishProcessingService == null) throw new RuntimeException("Unknown finishProcessingService type: " + type);
        return finishProcessingService;
    }
}
